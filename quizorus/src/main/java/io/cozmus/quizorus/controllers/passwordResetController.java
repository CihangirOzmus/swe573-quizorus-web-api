package io.cozmus.quizorus.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class passwordResetController {

    @GetMapping({"/passwordreset", "/passwordreset.html"})
    public String getPasswordResetPage(){
        return "passwordreset.html";
    }

}
