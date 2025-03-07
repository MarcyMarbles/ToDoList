package com.example.todolist.Service;

import com.example.todolist.Entity.User;
import com.example.todolist.Security.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserService userService;
    private final JwtUtils jwtUtils;

    public AuthService(UserService userService, JwtUtils jwtUtils) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    public User getAuthenticatedUser(HttpServletRequest request) {
        String token = extractToken(request);
        if (token == null) {
            return null;
        }
        return userService.extractUserFromToken(token);
    }

    public String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}
