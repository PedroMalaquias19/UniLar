package com.pedro.UniLar.awss3.file;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("application.bucket")
@Getter
@Setter
@NoArgsConstructor
public class FileConfig {
    private String bucketName;
}
