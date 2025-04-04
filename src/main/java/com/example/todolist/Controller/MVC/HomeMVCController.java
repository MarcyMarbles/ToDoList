package com.example.todolist.Controller.MVC;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeMVCController{

    @GetMapping("/")
    public String homePage() {
        return "home";
    }

    @GetMapping("/home")
    public String homePageRedirect() {
        return "redirect:/";
    }
}
