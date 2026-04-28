package com.farmconnect.farmconnect_backend.controller;
import com.farmconnect.dto.AuthResponse;
import com.farmconnect.dto.LoginRequest;
import com.farmconnect.entity.User;
import com.farmconnect.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return response.isSuccess() ? 
            ResponseEntity.ok(response) : 
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody User user) {
        AuthResponse response = authService.register(user);
        return response.isSuccess() ? 
            ResponseEntity.status(HttpStatus.CREATED).body(response) : 
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}