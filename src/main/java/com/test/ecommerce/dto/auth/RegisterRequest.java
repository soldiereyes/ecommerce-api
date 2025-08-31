package com.test.ecommerce.dto.auth;

import com.test.ecommerce.entity.user.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Java 17+: DTO como record (imutável, conciso e sem boilerplate).
 */
public record RegisterRequest(
        @NotBlank String name,
        @Email String email,
        @NotBlank String password,
        Role role // se vier null, definir USER no service
) { }
