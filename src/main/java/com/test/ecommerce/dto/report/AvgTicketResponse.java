package com.test.ecommerce.dto.report;

import java.math.BigDecimal;

public record AvgTicketResponse(
        String name,
        BigDecimal avgTicket
) {}