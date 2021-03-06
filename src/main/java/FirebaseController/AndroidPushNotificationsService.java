package FirebaseController;


import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AndroidPushNotificationsService {
    private static final String APP_ID="uzoandroid-6bc69";
    private static final String FIREBASE_SERVER_KEY = System.getenv("FIREBASE_TOKEN");
    private static final String FIREBASE_API_URL = "https://fcm.googleapis.com/v1/projects/"+APP_ID
            +"/messages:send";

    @Async
    public CompletableFuture<String> send(HttpEntity<String> entity) {
        System.out.println(FIREBASE_API_URL);

        RestTemplate restTemplate = new RestTemplate();

        /**
         https://fcm.googleapis.com/fcm/send
         Content-Type:application/json
         Authorization:key=FIREBASE_SERVER_KEY*/

        ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new HeaderRequestInterceptor("Authorization", "Bearer " + SendAndroidNotification.getAccessToken()));
        interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
        restTemplate.setInterceptors(interceptors);

        String firebaseResponse = restTemplate.postForObject(FIREBASE_API_URL, entity, String.class);

        return CompletableFuture.completedFuture(firebaseResponse);
    }
}

