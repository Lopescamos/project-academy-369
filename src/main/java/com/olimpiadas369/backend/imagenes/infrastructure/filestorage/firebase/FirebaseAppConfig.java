package com.olimpiadas369.backend.imagenes.infrastructure.filestorage.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.olimpiadas369.backend.imagenes.infrastructure.filestorage.FirebaseSettings;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.ByteArrayInputStream;
import java.util.Base64;

@Configuration
@EnableConfigurationProperties(FirebaseSettings.class)
@Profile({"dev", "prod"})
public class FirebaseAppConfig {

    private final FirebaseSettings settings;

    public FirebaseAppConfig(FirebaseSettings settings) { this.settings = settings; }

    @PostConstruct
    public void init() throws Exception {
        if (settings.credentialsBase64() == null || settings.credentialsBase64().isBlank()) return;
        if (!FirebaseApp.getApps().isEmpty()) return;
        byte[] json = Base64.getDecoder().decode(settings.credentialsBase64());
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(new ByteArrayInputStream(json)))
                .setProjectId(settings.projectId())
                .setStorageBucket(settings.storageBucket())
                .build();
        FirebaseApp.initializeApp(options);
    }
}
