package com.pedro.UniLar.email;

import com.pedro.UniLar.exception.EmailSendingException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService implements EmailSender {

    private final JavaMailSender mailSender;

    @Override
    @Async
    public void send(String to, String subject, String email) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setFrom("javahater000@gmail.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(email, true);

            mailSender.send(message);
            log.info("Email sent successfully: " + to);
        } catch (MessagingException e) {
            throw new EmailSendingException(e.getMessage());
        }
    }

    public String string(){
        return "ABC";
    }
}
