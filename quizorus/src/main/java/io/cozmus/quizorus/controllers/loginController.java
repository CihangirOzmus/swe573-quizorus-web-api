package io.cozmus.quizorus.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class loginController {

    @GetMapping({"/login", "/login.html"})
    public String getLoginPage(){
        return "login";
    }

}
