package com.example.todolist.Controller.MVC;

import com.example.todolist.Service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginMVCController {


    private final AuthService authService;

    public LoginMVCController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    private ResponseEntity<String> grantAccess(String username, String password) {
        return authService.authenticate(username, password);
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String username,
                          @RequestParam String password,
                          Model model) {
        ResponseEntity<String> response = grantAccess(username, password);
        if (response.getStatusCode().is2xxSuccessful()) {
            model.addAttribute("message", "Login successful! Redirecting to the main page...");
            model.addAttribute("fragment", "success");
            model.addAttribute("token", response.getBody());
        } else {
            model.addAttribute("error", "Invalid username or password.");
            model.addAttribute("fragment", "error");
        }
        return "fragments/login-result";
    }

}
