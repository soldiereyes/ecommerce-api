package com.test.ecommerce.dto.user;

import com.test.ecommerce.entity.user.Role;

import java.util.UUID;

public record UserMeResponse(UUID id, String name, String email, Role role) { }
