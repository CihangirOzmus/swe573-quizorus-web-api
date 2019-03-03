package io.cozmus.quizorus.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class indexController {

    @GetMapping({"", "/", "/index", "/index.html"})
    public String getIndexPage(){
        return "index";
    }

}
