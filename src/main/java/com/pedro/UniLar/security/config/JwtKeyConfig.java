package com.pedro.UniLar.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Configuration
public class JwtKeyConfig {

    @Bean
    public PrivateKey jwtPrivateKey(
            @Value("${application.jwt.privateKeyPath:keys/private_key.pem}") String privateKeyPath
    ) throws Exception {
        String pem = readPemResource(privateKeyPath);

        if (pem.contains("-----BEGIN RSA PRIVATE KEY-----")) {
            throw new IllegalStateException(
                    "A private key parece estar em PKCS#1 (-----BEGIN RSA PRIVATE KEY-----). " +
                            "Converta para PKCS#8 e tente novamente. Exemplo:\n" +
                            "openssl pkcs8 -topk8 -inform PEM -in rsa_key.pem -outform PEM -nocrypt"
            );
        }

        String base64 = pem.replaceAll("-----BEGIN (.*)-----", "")
                .replaceAll("-----END (.*)-----", "")
                .replaceAll("\\s+", "");
        byte[] keyBytes = Base64.getDecoder().decode(base64);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);

        try {
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(spec);
        } catch (Exception rsaEx) {
            KeyFactory kf = KeyFactory.getInstance("EC");
            return kf.generatePrivate(spec);
        }
    }

    @Bean
    public PublicKey jwtPublicKey(
            @Value("${application.jwt.publicKeyPath:keys/public_key.pem}") String publicKeyPath
    ) throws Exception {
        String pem = readPemResource(publicKeyPath);

        String base64 = pem.replaceAll("-----BEGIN (.*)-----", "")
                .replaceAll("-----END (.*)-----", "")
                .replaceAll("\\s+", "");
        byte[] keyBytes = Base64.getDecoder().decode(base64);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);

        try {
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePublic(spec);
        } catch (Exception rsaEx) {
            KeyFactory kf = KeyFactory.getInstance("EC");
            return kf.generatePublic(spec);
        }
    }

    private String readPemResource(String path) throws Exception {
        if (path != null && path.startsWith("classpath:")) {
            String cp = path.substring("classpath:".length());
            ClassPathResource res = new ClassPathResource(cp);
            try (InputStream is = res.getInputStream()) {
                return new String(is.readAllBytes(), StandardCharsets.US_ASCII);
            }
        }

        ClassPathResource res = new ClassPathResource(path);
        if (res.exists()) {
            try (InputStream is = res.getInputStream()) {
                return new String(is.readAllBytes(), StandardCharsets.US_ASCII);
            }
        }

        Path fsPath = Path.of(path);
        if (Files.exists(fsPath)) {
            return Files.readString(fsPath, StandardCharsets.US_ASCII);
        }

        throw new IllegalStateException("Não foi possível encontrar a chave em classpath ou filesystem: " + path);
    }
}