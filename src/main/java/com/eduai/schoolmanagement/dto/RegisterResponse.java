package com.eduai.schoolmanagement.dto;

import java.util.Set;

import com.eduai.schoolmanagement.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponse {
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private Set<User.Role> roles;
    private String message;
    private boolean emailVerificationRequired;

    public RegisterResponse(User user, String message, boolean emailVerificationRequired) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.roles = user.getRoles();
        this.message = message;
        this.emailVerificationRequired = emailVerificationRequired;
    }
}
