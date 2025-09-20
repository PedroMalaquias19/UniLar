package com.pedro.UniLar.file;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("application.files")
@Getter
@Setter
@NoArgsConstructor
public class FileConfig {
    // Diretório base local para armazenamento de ficheiros
    private String baseDir;
}
