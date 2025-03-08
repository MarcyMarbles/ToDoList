package com.example.todolist.Controller.GraphQL;

import com.example.todolist.Service.UserService;
import org.springframework.stereotype.Controller;

@Controller
public class UserControllerQL {
    private final UserService userService;

    public UserControllerQL(UserService userService) {
        this.userService = userService;
    }

}
