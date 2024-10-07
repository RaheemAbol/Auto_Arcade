package org.rma.springmvcdemo.repository;

import org.rma.springmvcdemo.model.Cart;
import org.rma.springmvcdemo.model.Shopper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByShopper(Shopper shopper);
    Optional<Cart> findByShopperId(Long shopperId);
}