package com.pedro.UniLar.file;

import com.pedro.UniLar.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileStore fileStore;
    private final FileConfig fileConfig;

    private static final String[] ALLOWED_TYPES = new String[] {
            MediaType.IMAGE_JPEG_VALUE,
            MediaType.IMAGE_PNG_VALUE,
            MediaType.IMAGE_GIF_VALUE,
            MediaType.APPLICATION_PDF_VALUE,
            "application/msword",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
    };

    private boolean isAllowed(String contentType) {
        if (contentType == null)
            return false;
        for (String t : ALLOWED_TYPES) {
            if (t.equalsIgnoreCase(contentType))
                return true;
        }
        return false;
    }

    public String uploadImage(String imageOrigin, Long id, MultipartFile file) {
        return uploadDocument(imageOrigin, id, file);
    }

    public String uploadDocument(String origin, Long id, MultipartFile file) {

        if (file.isEmpty()) {
            throw new BadRequestException("The uploaded file is empty [" + file.getSize() + "]");
        }

        if (!isAllowed(file.getContentType())) {
            throw new BadRequestException("Unsupported content type: " + file.getContentType());
        }

        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        metadata.put("Original-Filename", file.getOriginalFilename());

        String basePath = fileConfig.getBaseDir();
        String filename = String.format("%s/%s/%s-%s", origin, id, file.getOriginalFilename(), LocalDateTime.now());

        try {
            fileStore.upload(basePath, filename, Optional.of(metadata), file.getInputStream());
            return filename;
        } catch (IOException e) {
            throw new BadRequestException("Failed to upload the file");
        }
    }

    public byte[] download(String filename) {
        String basePath = fileConfig.getBaseDir();
        if (filename != null) {
            return fileStore.download(basePath, filename);
        } else {
            return new byte[0];
        }
    }
}
