package com.example.todolist.Controller.Rest;

import com.example.todolist.POJOs.UserLoginPOJO;
import com.example.todolist.Entity.User;
import com.example.todolist.Security.JwtUtils;
import com.example.todolist.Service.UserService;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    private final JwtUtils jwtUtils;

    public AuthController(UserService userService, JwtUtils jwtUtils) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("/login")
    public ResponseEntity<TokenResponse> login(
            @RequestBody UserLoginPOJO userLoginPOJO
    ) {
        if (userLoginPOJO.getLogin() == null || userLoginPOJO.getPassword() == null) {
            return ResponseEntity.status(400).body(new TokenResponse("Invalid login or password", null));
        }
        User user = userService.login(userLoginPOJO.getLogin(), userLoginPOJO.getPassword());
        if (user == null) {
            return ResponseEntity.status(401).body(new TokenResponse("Invalid login or password", null));
        }
        String token = jwtUtils.generateToken(
                user.getLogin(),
                String.valueOf(user.getId()),
                user.getUsername()
        );
        return ResponseEntity.ok(new TokenResponse(token, jwtUtils.getExpirationDate(token)));
    }

    @Getter
    public static class TokenResponse {
        private final String token;
        private final LocalDateTime expirationDate;
        private final LocalDate date = LocalDate.now();

        public TokenResponse(String token, LocalDateTime expirationDate) {
            this.token = token;
            this.expirationDate = expirationDate;
        }
    }
}
