package com.example.demo.controllers;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.services.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        boolean success = authService.register(user);
        if (success) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body("Username already exists");
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody User user, HttpSession session) {
        User loggedInUser = authService.login(user.getUsername(), user.getPassword(), session);
        if (loggedInUser != null) {
            return ResponseEntity.ok(new UserDTO(
                    loggedInUser.getId(),
                    loggedInUser.getUsername()
            ));
        }
        return ResponseEntity.status(401).body(null);
    }

    @GetMapping("/current")
    public ResponseEntity<UserDTO> getCurrentUser(HttpSession session) {
        User user = authService.getCurrentUser(session);
        if (user != null) {
            return ResponseEntity.ok(new UserDTO(
                    user.getId(),
                    user.getUsername()
            ));
        }
        return ResponseEntity.status(401).build();
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        authService.logout(session);
        return ResponseEntity.ok().build();
    }
}
