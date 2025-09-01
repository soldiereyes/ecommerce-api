package com.test.ecommerce.service.report;

import com.test.ecommerce.dto.report.AvgTicketResponse;
import com.test.ecommerce.dto.report.TopUserResponse;
import com.test.ecommerce.dto.report.TotalMonthResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ReportService {

    @PersistenceContext
    private EntityManager em;

    public List<TopUserResponse> getTopUsers() {
        String sql = """
            SELECT u.name, COUNT(o.id) AS total_orders
            FROM users u
            JOIN orders o ON u.id = o.user_id
            WHERE o.status = 'PAID'
            GROUP BY u.id, u.name
            ORDER BY total_orders DESC
            LIMIT 5
        """;

        return em.createNativeQuery(sql, Object[].class)
                .getResultList()
                .stream()
                .map(r -> new TopUserResponse(
                        (String) ((Object[]) r)[0],
                        ((Number) ((Object[]) r)[1]).longValue()
                ))
                .toList();
    }

    public List<AvgTicketResponse> getAvgTicket() {
        String sql = """
            SELECT u.name, AVG(o.total_value) AS avg_ticket
            FROM users u
            JOIN orders o ON u.id = o.user_id
            WHERE o.status = 'PAID'
            GROUP BY u.id, u.name
        """;

        return em.createNativeQuery(sql, Object[].class)
                .getResultList()
                .stream()
                .map(r -> new AvgTicketResponse(
                        (String) ((Object[]) r)[0],
                        ((BigDecimal) ((Object[]) r)[1])
                ))
                .toList();
    }

    public TotalMonthResponse getTotalMonth() {
        String sql = """
            SELECT COALESCE(SUM(o.total_value),0) AS total_month
            FROM orders o
            WHERE o.status = 'PAID'
              AND MONTH(o.created_at) = MONTH(CURRENT_DATE())
              AND YEAR(o.created_at) = YEAR(CURRENT_DATE())
        """;

        BigDecimal result = (BigDecimal) em.createNativeQuery(sql).getSingleResult();
        return new TotalMonthResponse(result);
    }
}
