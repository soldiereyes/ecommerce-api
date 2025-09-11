package com.test.ecommerce.repository.user;

import com.test.ecommerce.entity.user.Role;
import com.test.ecommerce.entity.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByEmail() {

        User user = User.builder()
                .name("João Teste")
                .email("joao@email.com")
                .password("123456")
                .role(Role.USER)
                .build();

        userRepository.save(user);

        // When
        var result = userRepository.findByEmail("joao@email.com");

        // Then
        assertTrue(result.isPresent(), "Usuário deveria estar presente");
        assertEquals("João Teste", result.get().getName());
    }

    @Test
    void existsByEmail() {
        // Given
        User user = User.builder()
                .name("Maria")
                .email("maria@email.com")
                .password("abcdef")
                .role(Role.ADMIN)
                .build();
        userRepository.save(user);

        // When
        boolean exists = userRepository.existsByEmail("maria@email.com");

        // Then
        assertTrue(exists);
    }
}