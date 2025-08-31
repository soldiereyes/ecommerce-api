package com.test.ecommerce.service.product;

import com.test.ecommerce.dto.product.ProductCreateRequest;
import com.test.ecommerce.dto.product.ProductResponse;
import com.test.ecommerce.dto.product.ProductUpdateRequest;
import com.test.ecommerce.entity.product.Product;
import com.test.ecommerce.repository.product.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Regras de negócio para Produtos:
 * - listagem paginada
 * - busca por id (404 quando não existe)
 * - criação
 * - atualização (PUT)
 * - exclusão
 *
 * Observações:
 * - Métodos mutadores rodam em transação (@Transactional).
 * - Mapeamento para DTO de saída centralizado no método toResponse.
 */
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    /** Lista produtos paginados retornando DTO de saída. */
    public Page<ProductResponse> list(Pageable pageable) {
        return repository.findAll(pageable).map(this::toResponse);
    }

    /** Busca um produto pelo ID, lança 404 (IllegalArgumentException) se não existir. */
    public ProductResponse findById(UUID id) {
        var p = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        return toResponse(p);
    }

    /** Cria um novo produto. */
    @Transactional
    public ProductResponse create(ProductCreateRequest req) {
        Product entity = Product.builder()
                .name(req.name())
                .description(req.description())
                .price(req.price())
                .category(req.category())
                .stockQuantity(req.stockQuantity())
                .build();
        return toResponse(repository.save(entity));
    }

    /** Atualiza um produto existente (PUT). */
    @Transactional
    public ProductResponse update(UUID id, ProductUpdateRequest req) {
        var p = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        p.setName(req.name());
        p.setDescription(req.description());
        p.setPrice(req.price());
        p.setCategory(req.category());
        p.setStockQuantity(req.stockQuantity());
        // O @PreUpdate da entidade (se houver) cuidará do updatedAt
        return toResponse(p);
    }

    /** Exclui um produto pelo ID. */
    @Transactional
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Product not found");
        }
        repository.deleteById(id);
    }

    /* ===== mapeamento entity -> DTO ===== */
    private ProductResponse toResponse(Product p) {
        return new ProductResponse(
                p.getId(),
                p.getName(),
                p.getDescription(),
                p.getPrice(),
                p.getCategory(),
                p.getStockQuantity(),
                p.getCreatedAt(),
                p.getUpdatedAt()
        );
    }
}
