package com.eduai.schoolmanagement.entity;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "teachers")
public class Teacher extends BaseEntity {

    @NotBlank(message = "Employee ID is required")
    @Indexed(unique = true)
    private String employeeId;

    @Valid
    @NotNull(message = "User information is required")
    private UserInfo user;

    @NotBlank(message = "Department is required")
    private String department;

    private List<String> subjects;
    private String qualification;
    private double experienceYears;
    private LocalDate joiningDate;
    private String employmentType;

    private double performanceScore;
    private double studentRating;
    private int classesAssigned;
    private int totalStudents;
    private double attendanceRate;
    private List<String> aiRecommendations;

    @Data
    public static class UserInfo {
        @NotBlank(message = "First name is required")
        private String firstName;

        @NotBlank(message = "Last name is required")
        private String lastName;

        @NotBlank(message = "Email is required")
        @Indexed(unique = true)
        private String email;

        private String phone;
        private LocalDate dateOfBirth;
        private String gender;
        private String address;
    }
}
