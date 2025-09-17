package com.pedro.UniLar.profile.emailconfirmation;

// Import removido
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
// Import removido

@Controller
@RequestMapping("/api/v1/auth/")
@RequiredArgsConstructor
public class EmailControllerDemo {

    // authenticationService removido

    @GetMapping("emailConfirmed")
    public String index() {
        return "emailConfirmed";
    }

    // Endpoint de confirmação removido
}
