package FirebaseController;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class SendAndroidNotification {
    private final String TOPIC = "New Job Posting";

    public  static String sendNotification() {
        try {
        JSONObject body = new JSONObject();
        JSONObject notification = new JSONObject();

        notification.put("title", "New Job Posting!");
        notification.put("body", "Open the UZO App for a new Opportunities");
        body.put("notification", notification);
        /**
         {
         "notification": {
         "title": "JSA Notification",
         "body": "Happy Message!"
         }
         */

        HttpEntity<String> request = new HttpEntity<>(body.toString());
            AndroidPushNotificationsService push= new AndroidPushNotificationsService();
        CompletableFuture<String> pushNotification = push.send(request);
        CompletableFuture.allOf(pushNotification).join();

            String firebaseResponse = pushNotification.get();

            return new ResponseEntity(firebaseResponse, HttpStatus.OK).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity("Push Notification ERROR!", HttpStatus.BAD_REQUEST).toString();
    }

    public static void initializeApp() throws IOException {
        FileInputStream serviceAccount =
                new FileInputStream("firebaseKey");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://uzoandroid-6bc69.firebaseio.com")
                .build();

        FirebaseApp.initializeApp(options);
    }

    public static String getAccessToken() {
        try {
            GoogleCredential googleCredential = GoogleCredential
                    .fromStream(new FileInputStream("firebaseKey"))
                    .createScoped(Arrays.asList("https://www.googleapis.com/auth/firebase.messaging"));
            googleCredential.refreshToken();
            return googleCredential.getAccessToken();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}

