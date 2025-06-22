package com.hexaware.hospitalmanagement.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthRequestDTO {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String userName;

    @NotBlank(message = "Password is required")
    private String password;
}
