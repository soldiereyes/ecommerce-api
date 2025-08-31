package com.test.ecommerce.repository.product;

import com.test.ecommerce.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> { }
