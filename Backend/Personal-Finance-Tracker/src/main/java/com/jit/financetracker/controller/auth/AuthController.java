package com.jit.financetracker.controller.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jit.financetracker.dto.request.LoginRequestDto;
import com.jit.financetracker.dto.request.OtpVerifyRequestDto;
import com.jit.financetracker.dto.request.RegisterRequestDto;
import com.jit.financetracker.dto.response.JwtAuthResponseDto;
import com.jit.financetracker.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // REGISTER
    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody RegisterRequestDto request
    ) {
        authService.register(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Registration successful. OTP sent to email.");
    }

    // VERIFY OTP
    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(
            @RequestBody OtpVerifyRequestDto request
    ) {
        authService.verifyOtp(request);
        return ResponseEntity.ok("Email verified successfully.");
    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponseDto> login(
            @RequestBody LoginRequestDto request
    ) {
        JwtAuthResponseDto response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    // AUTH ERRORS

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentials(BadCredentialsException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ex.getMessage());
    }   

    // OTP / Email / unexpected runtime errors
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntime(RuntimeException ex) {
        ex.printStackTrace(); // 🔥 VERY IMPORTANT: do not remove
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ex.getMessage());
    }
}
