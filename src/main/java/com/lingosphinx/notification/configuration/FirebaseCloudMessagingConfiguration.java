package com.lingosphinx.notification.configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
public class FirebaseCloudMessagingConfiguration {

    @Bean
    FirebaseApp firebaseApp(
            @Value("${firebase.config-path}") String serviceAccountPath) throws IOException {

        try (var is = new FileInputStream(serviceAccountPath)) {
            var creds = GoogleCredentials.fromStream(is);
            var options = FirebaseOptions.builder()
                    .setCredentials(creds)
                    .build();
            return FirebaseApp.initializeApp(options);
        }
    }

    @Bean
    FirebaseMessaging firebaseMessaging(FirebaseApp firebaseApp) {
        return FirebaseMessaging.getInstance(firebaseApp);
    }
}