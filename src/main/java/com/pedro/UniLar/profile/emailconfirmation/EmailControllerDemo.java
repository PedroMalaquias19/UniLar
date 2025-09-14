package com.pedro.UniLar.profile.emailconfirmation;

import achama.website.security.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/v1/auth/")
@RequiredArgsConstructor
public class EmailControllerDemo {

    private final AuthService authenticationService;

    @GetMapping("emailConfirmed")
    public String index(){
        return "emailConfirmed";
    }

    @GetMapping("confirm/")
    public String confirmEmail(@RequestParam("token") String token){
        authenticationService.confirmEmail(token);
        return "success";
    }
}
