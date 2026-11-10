package com.olimpiadas369.backend.imagenes.infrastructure.filestorage;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "firebase")
public record FirebaseSettings(String projectId, String storageBucket, String credentialsBase64) {}
