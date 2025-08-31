package com.test.ecommerce.controller.product;

import com.test.ecommerce.dto.product.ProductCreateRequest;
import com.test.ecommerce.dto.product.ProductResponse;
import com.test.ecommerce.dto.product.ProductUpdateRequest;
import com.test.ecommerce.service.product.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Endpoints de Produtos.
 * Observação: POST/PUT/DELETE exigem ROLE_ADMIN (via @PreAuthorize).
 * Se quiser tornar GETs públicos, liberamos no SecurityConfig.
 */
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    /** Lista paginada de produtos. */
    @GetMapping
    public ResponseEntity<Page<ProductResponse>> list(@PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(service.list(pageable));
    }

    /** Detalha um produto pelo ID. */
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> get(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    /** Cria um novo produto (somente ADMIN). */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductCreateRequest body) {
        var created = service.create(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /** Atualiza um produto existente (somente ADMIN). */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable UUID id,
                                                  @Valid @RequestBody ProductUpdateRequest body) {
        return ResponseEntity.ok(service.update(id, body));
    }

    /** Exclui um produto (somente ADMIN). */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
