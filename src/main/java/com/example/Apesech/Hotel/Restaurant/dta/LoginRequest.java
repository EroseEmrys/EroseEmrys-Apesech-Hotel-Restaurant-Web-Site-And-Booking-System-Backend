package com.example.Apesech.Hotel.Restaurant.dta;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "Email Is Required")
    private String email;
    @NotBlank(message = "Password Is Required")
    private String password;
}
