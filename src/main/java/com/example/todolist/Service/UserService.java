package com.example.todolist.Service;

import com.example.todolist.Entity.User;
import com.example.todolist.Repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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
}
