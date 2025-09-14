package com.pedro.UniLar.awss3.file;

import com.pedro.UniLar.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.apache.http.entity.ContentType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileStore fileStore;
    private final FileConfig fileConfig;

    public String uploadImage(String imageOrigin, Long id, MultipartFile file) {

        //Verify if the file is empty
        if(file.isEmpty()){
            throw new BadRequestException("The uploaded image is empty [" + file.getSize() + "]");
        }

        //Verify if the content is an image
        if(Arrays.asList(ContentType.IMAGE_JPEG, ContentType.IMAGE_PNG, ContentType.IMAGE_GIF).contains(file.getContentType())){
            throw new BadRequestException("The content is not an image, content type: " + file.getContentType());
        }

        //Grab some metadata
        Map<String, String> metadata = new HashMap<>();

        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        metadata.put("Original-Filename", file.getOriginalFilename());

        //Upload the User Image
        String path = fileConfig.getBucketName();
        String filename = String.format("%s/%s/%s-%s", imageOrigin, id, file.getOriginalFilename(), LocalDateTime.now());

        try {
            fileStore.upload(path, filename, Optional.of(metadata), file.getInputStream());
            return filename;
        } catch (IOException e) {
            throw new BadRequestException("Failed to upload the file");
        }
    }

    public byte[] downloadImage(String filename){
        String path = fileConfig.getBucketName();
        if(filename != null){
            return fileStore.download(path, filename);
        } else {
            return new byte[0];
        }
    }
}
