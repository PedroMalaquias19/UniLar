package com.pedro.UniLar.file;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FileStore {

    public void upload(String basePath, String filename, Optional<Map<String, String>> optionalMetadata,
            InputStream inputStream) throws IOException {
        Path dir = Path.of(basePath).toAbsolutePath().normalize();
        Files.createDirectories(dir);
        Path filePath = dir.resolve(filename).normalize();
        Files.createDirectories(filePath.getParent());
        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
    }

    public byte[] download(String basePath, String key) {
        try {
            Path filePath = Path.of(basePath).toAbsolutePath().normalize().resolve(key).normalize();
            if (Files.exists(filePath)) {
                return Files.readAllBytes(filePath);
            }
            return new byte[0];
        } catch (IOException e) {
            return new byte[0];
        }
    }
}
