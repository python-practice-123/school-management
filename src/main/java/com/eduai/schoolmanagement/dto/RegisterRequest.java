package com.eduai.schoolmanagement.dto;

import java.util.Set;

import com.eduai.schoolmanagement.entity.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 40, message = "Password must be between 6 and 40 characters")
    private String password;

    @NotBlank(message = "Confirm password is required")
    private String confirmPassword;

    @NotNull(message = "At least one role is required")
    private Set<User.Role> roles;

    private String phone;
    private String dateOfBirth;
    private String gender;
    private String address;

    // Additional fields for specific roles
    private String studentId; // For students
    private String employeeId; // For teachers/admins
    private String department; // For teachers/admins
    private String grade; // For students
    private String section; // For students
}
