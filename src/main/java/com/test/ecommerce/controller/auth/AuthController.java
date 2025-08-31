package com.test.ecommerce.controller.auth;

import com.test.ecommerce.dto.auth.AuthResponse;
import com.test.ecommerce.dto.auth.LoginRequest;
import com.test.ecommerce.dto.auth.RegisterRequest;
import com.test.ecommerce.dto.user.UserMeResponse;
import com.test.ecommerce.entity.user.User;
import com.test.ecommerce.service.auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest req) {
        return ResponseEntity.ok(service.register(req));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest req) {
        return ResponseEntity.ok(service.login(req));
    }

    @GetMapping("/me")
    public ResponseEntity<UserMeResponse> me(@AuthenticationPrincipal User user) {
        var body = new UserMeResponse(user.getId(), user.getName(), user.getEmail(), user.getRole());
        return ResponseEntity.ok(body);
    }
}
