package com.test.ecommerce.dto.report;

public record TopUserResponse(
        String name,
        Long totalOrders
) {}