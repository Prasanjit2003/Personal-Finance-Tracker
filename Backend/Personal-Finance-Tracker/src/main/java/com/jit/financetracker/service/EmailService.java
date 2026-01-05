package com.jit.financetracker.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendOtpEmail(String toEmail, String otp) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Registration OTP for Personal Finance Tracker");
        message.setText(buildOtpEmailBody(otp));

        mailSender.send(message);
    }

    private String buildOtpEmailBody(String otp) {
        return """
                Hello,

                Your One-Time Password (OTP) for Personal Finance Tracker is:

                OTP: %s

                This OTP is valid for 10 minutes.
                Please do not share this OTP with anyone.

                Regards,
                Personal Finance Tracker Team
                """.formatted(otp);
    }
}
