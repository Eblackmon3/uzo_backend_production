package FirebaseController;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class SendAndroidNotification {
    private final String TOPIC = "New Job Posting";

    public String sendNotification() {
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
}

