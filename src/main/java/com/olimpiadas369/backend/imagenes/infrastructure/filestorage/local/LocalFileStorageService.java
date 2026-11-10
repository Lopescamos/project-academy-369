package com.olimpiadas369.backend.imagenes.infrastructure.filestorage.local;

import com.olimpiadas369.backend.imagenes.application.internal.outboundservices.storage.FileStorageService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/** Fallback para tests y dev sin Firebase: guarda en /tmp/olimpiadas369-storage. */
@Service
@Profile("test")
public class LocalFileStorageService implements FileStorageService {
    private static final Path ROOT = Paths.get(System.getProperty("java.io.tmpdir"), "olimpiadas369-storage");

    @Override
    public String upload(String path, byte[] content, String contentType) {
        try {
            Path full = ROOT.resolve(path);
            Files.createDirectories(full.getParent());
            Files.write(full, content);
            return path;
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    @Override
    public String signedReadUrl(String path, int expirationSeconds) {
        return "file://" + ROOT.resolve(path);
    }

    @Override
    public void delete(String path) {
        try { Files.deleteIfExists(ROOT.resolve(path)); } catch (Exception ignored) {}
    }
}
