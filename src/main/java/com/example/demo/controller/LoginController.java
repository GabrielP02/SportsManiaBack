package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.LoginRequest;
import com.example.demo.model.Person;
import com.example.demo.service.AuthService;

import java.util.Map;

@RestController
@RequestMapping("/api/auth/")
public class LoginController {

    @Autowired
    private AuthService authService;
    

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Person person = authService.login(loginRequest.getUsername(), loginRequest.getPassword());

        if (person != null) {
            String token = authService.generateToken(person);
            return ResponseEntity.ok().body(Map.of("token", token));
        }

        return ResponseEntity
            .status(401)
            .body(Map.of("error", "Invalid credentials"));
    }

    @PostMapping("logout")
    public String logout() {
        SecurityContextHolder.clearContext();
        return "Logged out successfully";
    }
}


