package com.codewithmosh.store.dtos;

import com.codewithmosh.store.Validation.LowerCase;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NonNull;

@Data
public class RegisterUserRequest {
    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "name must be less than equal to 255 characters")
    private String name;
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @LowerCase(message = "All characters must be lowercase")
    private String email;
    @NotBlank(message = "password is required")
    @Size(min = 6,max = 16,message = "password should between 6 to 16 characters")
    private String password;
}
