package com.jit.financetracker.service;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jit.financetracker.dto.request.LoginRequestDto;
import com.jit.financetracker.dto.request.OtpVerifyRequestDto;
import com.jit.financetracker.dto.request.RegisterRequestDto;
import com.jit.financetracker.dto.response.JwtAuthResponseDto;
import com.jit.financetracker.entity.User;
import com.jit.financetracker.repository.UserRepository;
import com.jit.financetracker.security.JwtTokenProvider;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final EmailService emailService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtTokenProvider jwtTokenProvider,
            EmailService emailService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.emailService = emailService;
    }

    // REGISTER
    public void register(RegisterRequestDto request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        String otp = generateOtp();

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .verified(false)
                .otp(otp)
                .otpExpiry(LocalDateTime.now().plusMinutes(10))
                .build();

        userRepository.save(user);
       
        try {
            emailService.sendOtpEmail(user.getEmail(), otp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // VERIFY OTP
    public void verifyOtp(OtpVerifyRequestDto request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.isVerified()) {
            throw new RuntimeException("User already verified");
        }

        if (!user.getOtp().equals(request.getOtp())) {
            throw new RuntimeException("Invalid OTP");
        }

        if (user.getOtpExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP expired");
        }

        user.setVerified(true);
        user.setOtp(null);
        user.setOtpExpiry(null);

        userRepository.save(user);
    }

    // LOGIN 
    public JwtAuthResponseDto login(LoginRequestDto request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Invalid email or password"));

        if (!user.isVerified()) {
            throw new BadCredentialsException("Please verify your account using OTP");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid email or password");
        }

        String token = jwtTokenProvider.generateToken(user.getEmail());

        return new JwtAuthResponseDto(token);
    }

    private String generateOtp() {
        return String.valueOf(100000 + new Random().nextInt(900000));
    }
}
