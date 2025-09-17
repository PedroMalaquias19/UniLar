package com.pedro.UniLar.email;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "application.email")
@NoArgsConstructor
@Getter
@Setter
public class EmailConfig {
    private String frontDomain;
    private String backDomain;
}
