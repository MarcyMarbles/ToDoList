package com.example.todolist.Service;

import com.example.todolist.Entity.User;
import com.example.todolist.POJOs.UserAuthPOJO;
import com.example.todolist.Repos.UserRepository;
import com.example.todolist.Security.JwtUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtils jwtUtils;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUsername(user.getLogin());
        return userRepository.save(user);
    }

    public List<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
        // To Show user by username
        // 100 users can have the same username
    }

    public User getUserByLogin(String login) {
        return userRepository.findByLogin(login).orElse(null);
        // To Show user by login
        // 1 user can have only 1 login
    }

    public User login(String login, String password) {
        User user = userRepository.findByLogin(login).orElse(null);
        if (user == null) {
            return null;
        }
        if (passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    public boolean isUserAlreadyCreated(UserAuthPOJO userAuthPOJO) {
        return userRepository
                .findByLogin(userAuthPOJO.login())
                .orElse(null) != null;
    }

    public User extractUserFromToken(String token) {
        token = token.substring(7);
        String login = jwtUtils.extractLogin(token);
        if (login == null) {
            return null;
        }
        return getUserByLogin(login);
    }
}
