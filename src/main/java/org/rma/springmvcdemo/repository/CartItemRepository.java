package org.rma.springmvcdemo.repository;

import org.rma.springmvcdemo.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}