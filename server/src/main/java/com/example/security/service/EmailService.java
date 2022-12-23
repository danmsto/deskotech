package com.example.security.service;

import jakarta.transaction.Transactional;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class EmailService {

    private final JavaMailSender mailSender;
    private final TokenService tokenService;

    public EmailService(JavaMailSender mailSender, TokenService tokenService) {
        this.mailSender = mailSender;
        this.tokenService = tokenService;
    }

    public void sendVerificationEmail(String toEmail, String verificationId) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("deskbuddy.wandisco@gmail.com");
        message.setTo(toEmail);
        message.setSubject("Email verification");
        String EMAIL_MESSAGE = """
                Hi there!\s
                \s
                Here is your email verification token, please give this to Desk Buddy:\s
                \s
                """;
        message.setText(EMAIL_MESSAGE + tokenService.generateEmailVerificationToken(verificationId));
        mailSender.send(message);
    }

}
