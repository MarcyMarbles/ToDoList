package com.example.todolist.Controller.Rest;

import com.example.todolist.Entity.Person;
import com.example.todolist.Entity.User;
import com.example.todolist.Security.JwtUtils;
import com.example.todolist.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/protected/tasks")
public class TaskController {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public ResponseEntity<String> test(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        token = token.substring(7);
        String login = jwtUtils.extractLogin(token);
        if (login == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        User user = userService.getUserByLogin(login);
        if (user == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        Person person = user.getCurrentPerson();
        if(person == null) {
            return ResponseEntity.status(401).body("Continue registration please");
        }

        return ResponseEntity.status(200).body("Test");
    }
}
