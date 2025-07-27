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
public class LoginResponse {
    private String token;
    private String type = "Bearer";
    private String email;
    private String firstName;
    private String lastName;
    private Set<User.Role> roles;
    private long expiresIn; // Token expiration time in milliseconds

    public LoginResponse(String token, User user, long expiresIn) {
        this.token = token;
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.roles = user.getRoles();
        this.expiresIn = expiresIn;
    }
}
