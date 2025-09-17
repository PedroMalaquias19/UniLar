package com.pedro.UniLar.profile.emailconfirmation;

import com.pedro.UniLar.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EmailConfirmationService {

    private final EmailConfirmationTokenRepository emailConfirmationTokenRepository;

    public void saveToken(EmailConfirmationToken confirmationToken){
        emailConfirmationTokenRepository.save(confirmationToken);
    }

    public EmailConfirmationToken getConfirmationToken(String token){
        return emailConfirmationTokenRepository.findByToken(token)
                .orElseThrow(() -> new NotFoundException("Confirmation Token not Found"));
    }

    public int setConfirmedAt(EmailConfirmationToken confirmationToken) {
        confirmationToken.setConfirmedAt(LocalDateTime.now());
        emailConfirmationTokenRepository.save(confirmationToken);
        return 0;
    }
}
