package com.springboot.stockmanagementhub.model.dto;

import com.springboot.stockmanagementhub.constraint.ValidPassword;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
    @NotNull
    @Column(length = 20, nullable = false)
    String firstName;

    @NotEmpty(message = "Last name cannot be null")
    @Column(length = 20, nullable = false)
    String lastName;

    @NotEmpty
    @Email
    String email;

    @NotEmpty
    @Size(min = 8, message = "Password must be at least 8 character long with " +
            "1 uppercase, 1 lowercase, 2 digit and 1 symbol")
    @ValidPassword
    String password;

    String roleTitle;
}