package com.eduai.schoolmanagement.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.from:noreply@eduai-school.com}")
    private String fromEmail;

    @Value("${app.name:EduAI School Management}")
    private String appName;

    public void sendLoginVerificationCode(String toEmail, String verificationCode, String firstName) {
        try {
            String subject = "Login Verification Code - " + appName;
            String htmlContent = buildLoginVerificationEmailHtml(firstName, verificationCode);
            sendHtmlEmail(toEmail, subject, htmlContent);
            log.info("Login verification email sent to: {}", toEmail);
        } catch (Exception e) {
            log.error("Failed to send login verification email to {}: {}", toEmail, e.getMessage());
            throw new RuntimeException("Failed to send verification email", e);
        }
    }

    public void sendPasswordResetCode(String toEmail, String resetCode, String firstName) {
        try {
            String subject = "Password Reset Code - " + appName;
            String htmlContent = buildPasswordResetEmailHtml(firstName, resetCode);
            sendHtmlEmail(toEmail, subject, htmlContent);
            log.info("Password reset email sent to: {}", toEmail);
        } catch (Exception e) {
            log.error("Failed to send password reset email to {}: {}", toEmail, e.getMessage());
            throw new RuntimeException("Failed to send password reset email", e);
        }
    }

    public void sendPasswordResetConfirmation(String toEmail, String firstName) {
        try {
            String subject = "Password Reset Successful - " + appName;
            String htmlContent = buildPasswordResetConfirmationHtml(firstName);
            sendHtmlEmail(toEmail, subject, htmlContent);
            log.info("Password reset confirmation email sent to: {}", toEmail);
        } catch (Exception e) {
            log.error("Failed to send password reset confirmation email to {}: {}", toEmail, e.getMessage());
            // Don't throw exception for confirmation emails
        }
    }

    public void sendWelcomeEmail(String toEmail, String firstName, String role) {
        try {
            String subject = "Welcome to " + appName;
            String htmlContent = buildWelcomeEmailHtml(firstName, role);
            sendHtmlEmail(toEmail, subject, htmlContent);
            log.info("Welcome email sent to: {}", toEmail);
        } catch (Exception e) {
            log.error("Failed to send welcome email to {}: {}", toEmail, e.getMessage());
            // Don't throw exception for welcome emails
        }
    }

    private void sendHtmlEmail(String to, String subject, String htmlContent) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(fromEmail);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);

        mailSender.send(message);
    }

    private void sendSimpleEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    private String buildLoginVerificationEmailHtml(String firstName, String verificationCode) {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Login Verification</title>
                <style>
                    body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f4; }
                    .container { max-width: 600px; margin: 0 auto; background-color: white; padding: 20px; }
                    .header { background: linear-gradient(135deg, #1976d2 0%, #42a5f5 100%); color: white; padding: 20px; text-align: center; border-radius: 8px 8px 0 0; }
                    .content { padding: 30px 20px; }
                    .verification-code { background-color: #f0f7ff; border: 2px solid #1976d2; border-radius: 8px; padding: 20px; text-align: center; margin: 20px 0; }
                    .code { font-size: 32px; font-weight: bold; letter-spacing: 8px; color: #1976d2; margin: 10px 0; }
                    .footer { background-color: #f8f9fa; padding: 20px; text-align: center; font-size: 12px; color: #666; border-radius: 0 0 8px 8px; }
                    .warning { background-color: #fff3cd; border: 1px solid #ffeaa7; border-radius: 4px; padding: 15px; margin: 20px 0; color: #856404; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>🎓 %s</h1>
                        <h2>Login Verification</h2>
                    </div>
                    <div class="content">
                        <h3>Hello %s!</h3>
                        <p>You're attempting to sign in to your account. Please use the verification code below to complete your login:</p>

                        <div class="verification-code">
                            <p><strong>Your Verification Code:</strong></p>
                            <div class="code">%s</div>
                            <p style="margin: 0; color: #666; font-size: 14px;">Valid for 5 minutes</p>
                        </div>

                        <p>Enter this code in the login verification page to access your account.</p>

                        <div class="warning">
                            <strong>Security Notice:</strong> If you didn't request this code, please ignore this email and ensure your account password is secure.
                        </div>
                    </div>
                    <div class="footer">
                        <p>This is an automated message from %s. Please do not reply to this email.</p>
                        <p>&copy; 2024 %s. All rights reserved.</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(appName, firstName, verificationCode, appName, appName);
    }

    private String buildPasswordResetEmailHtml(String firstName, String resetCode) {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Password Reset</title>
                <style>
                    body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f4; }
                    .container { max-width: 600px; margin: 0 auto; background-color: white; padding: 20px; }
                    .header { background: linear-gradient(135deg, #ff5722 0%, #ff8a65 100%); color: white; padding: 20px; text-align: center; border-radius: 8px 8px 0 0; }
                    .content { padding: 30px 20px; }
                    .reset-code { background-color: #fff3e0; border: 2px solid #ff5722; border-radius: 8px; padding: 20px; text-align: center; margin: 20px 0; }
                    .code { font-size: 32px; font-weight: bold; letter-spacing: 8px; color: #ff5722; margin: 10px 0; }
                    .footer { background-color: #f8f9fa; padding: 20px; text-align: center; font-size: 12px; color: #666; border-radius: 0 0 8px 8px; }
                    .warning { background-color: #ffebee; border: 1px solid #ffcdd2; border-radius: 4px; padding: 15px; margin: 20px 0; color: #c62828; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>🔒 %s</h1>
                        <h2>Password Reset Request</h2>
                    </div>
                    <div class="content">
                        <h3>Hello %s!</h3>
                        <p>We received a request to reset your password. Use the verification code below to proceed with password reset:</p>

                        <div class="reset-code">
                            <p><strong>Your Reset Code:</strong></p>
                            <div class="code">%s</div>
                            <p style="margin: 0; color: #666; font-size: 14px;">Valid for 15 minutes</p>
                        </div>

                        <p>Enter this code on the password reset page to create a new password for your account.</p>

                        <div class="warning">
                            <strong>Security Alert:</strong> If you didn't request a password reset, please ignore this email and consider updating your account security.
                        </div>
                    </div>
                    <div class="footer">
                        <p>This is an automated message from %s. Please do not reply to this email.</p>
                        <p>&copy; 2024 %s. All rights reserved.</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(appName, firstName, resetCode, appName, appName);
    }

    private String buildPasswordResetConfirmationHtml(String firstName) {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Password Reset Successful</title>
                <style>
                    body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f4; }
                    .container { max-width: 600px; margin: 0 auto; background-color: white; padding: 20px; }
                    .header { background: linear-gradient(135deg, #4caf50 0%, #66bb6a 100%); color: white; padding: 20px; text-align: center; border-radius: 8px 8px 0 0; }
                    .content { padding: 30px 20px; }
                    .success-box { background-color: #e8f5e8; border: 2px solid #4caf50; border-radius: 8px; padding: 20px; text-align: center; margin: 20px 0; }
                    .footer { background-color: #f8f9fa; padding: 20px; text-align: center; font-size: 12px; color: #666; border-radius: 0 0 8px 8px; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>✅ %s</h1>
                        <h2>Password Reset Successful</h2>
                    </div>
                    <div class="content">
                        <h3>Hello %s!</h3>

                        <div class="success-box">
                            <h3 style="color: #2e7d32; margin: 0;">Password Updated Successfully!</h3>
                            <p style="margin: 10px 0 0 0; color: #388e3c;">Your account password has been reset and is now secure.</p>
                        </div>

                        <p>Your password has been successfully updated. You can now login to your account using your new password.</p>

                        <p>If you have any questions or concerns, please don't hesitate to contact our support team.</p>
                    </div>
                    <div class="footer">
                        <p>This is an automated message from %s. Please do not reply to this email.</p>
                        <p>&copy; 2024 %s. All rights reserved.</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(appName, firstName, appName, appName);
    }

    private String buildWelcomeEmailHtml(String firstName, String role) {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Welcome to %s</title>
                <style>
                    body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f4; }
                    .container { max-width: 600px; margin: 0 auto; background-color: white; padding: 20px; }
                    .header { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; padding: 20px; text-align: center; border-radius: 8px 8px 0 0; }
                    .content { padding: 30px 20px; }
                    .welcome-box { background-color: #f0f4ff; border: 2px solid #667eea; border-radius: 8px; padding: 20px; text-align: center; margin: 20px 0; }
                    .footer { background-color: #f8f9fa; padding: 20px; text-align: center; font-size: 12px; color: #666; border-radius: 0 0 8px 8px; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>🎓 Welcome to %s!</h1>
                    </div>
                    <div class="content">
                        <h3>Hello %s!</h3>

                        <div class="welcome-box">
                            <h3 style="color: #4c51bf; margin: 0;">Account Created Successfully!</h3>
                            <p style="margin: 10px 0 0 0; color: #5a67d8;">You've been registered as a <strong>%s</strong></p>
                        </div>

                        <p>Thank you for joining our school management system. Your account has been created and you can now access all the features available for your role.</p>

                        <p>You can now login to your account and start exploring the platform.</p>

                        <p>If you need any assistance getting started, our support team is here to help!</p>
                    </div>
                    <div class="footer">
                        <p>This is an automated message from %s. Please do not reply to this email.</p>
                        <p>&copy; 2024 %s. All rights reserved.</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(appName, appName, firstName, role.toLowerCase(), appName, appName);
    }
}
