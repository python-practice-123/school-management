package com.eduai.schoolmanagement.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "users")
public class User extends BaseEntity {

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @Email(message = "Email should be valid")
    @Indexed(unique = true)
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number should be 10 digits")
    private String phone;

    private String address;
    private LocalDate dateOfBirth;
    private String gender;
    private String profileImage;

    private Set<Role> roles;

    private boolean emailVerified = false;
    private boolean locked = false;

    // Email verification fields
    private String verificationCode;
    private LocalDateTime codeExpiryTime;

    // Password reset fields
    private String passwordResetCode;
    private LocalDateTime resetCodeExpiryTime;

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public enum Role {
        ADMIN, TEACHER, STUDENT, PARENT, ACCOUNTANT
    }
}
