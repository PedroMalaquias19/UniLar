package com.pedro.UniLar.email;

public interface EmailSender {
    void send(String to, String subject, String email);
}
