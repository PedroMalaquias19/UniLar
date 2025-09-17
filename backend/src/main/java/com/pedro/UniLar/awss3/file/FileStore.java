package com.pedro.UniLar.awss3.file;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FileStore {

    public void upload(String path, String filename, Optional<Map<String, String>> optionalMetadata, InputStream inputStream){
    }

    public byte[] download(String path, String key){
        return null;
    }
}
