package com.SpringBoot.RESTAPIs.dto;


import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AddStudentRequestDto {

    @Email
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "name is required")
    @Size(min=3, max=30 , message = "Invalid length")
    private String name;
}