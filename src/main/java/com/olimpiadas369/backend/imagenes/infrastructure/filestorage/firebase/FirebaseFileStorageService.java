package com.olimpiadas369.backend.imagenes.infrastructure.filestorage.firebase;

import com.google.cloud.storage.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.StorageClient;
import com.olimpiadas369.backend.imagenes.application.internal.outboundservices.storage.FileStorageService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Profile({"dev", "prod"})
public class FirebaseFileStorageService implements FileStorageService {

    @Override
    public String upload(String path, byte[] content, String contentType) {
        Bucket bucket = StorageClient.getInstance(FirebaseApp.getInstance()).bucket();
        Blob blob = bucket.create(path, content, contentType);
        return blob.getName();
    }

    @Override
    public String signedReadUrl(String path, int expirationSeconds) {
        Bucket bucket = StorageClient.getInstance(FirebaseApp.getInstance()).bucket();
        Blob blob = bucket.get(path);
        if (blob == null) return null;
        return blob.signUrl(expirationSeconds, TimeUnit.SECONDS, Storage.SignUrlOption.withV4Signature()).toString();
    }

    @Override
    public void delete(String path) {
        Bucket bucket = StorageClient.getInstance(FirebaseApp.getInstance()).bucket();
        Blob blob = bucket.get(path);
        if (blob != null) blob.delete();
    }
}
