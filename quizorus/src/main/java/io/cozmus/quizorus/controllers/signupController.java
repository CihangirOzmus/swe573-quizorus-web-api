package io.cozmus.quizorus.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class signupController {

    @GetMapping({"/signup", "/signup.html"})
    public String getSignUpPage(){
        return "signup";
    }
}
