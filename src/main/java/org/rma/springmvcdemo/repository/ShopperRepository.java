package org.rma.springmvcdemo.repository;

import org.rma.springmvcdemo.model.Shopper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShopperRepository extends JpaRepository<Shopper, Long> {
    Optional<Shopper> findByUsername(String username);
}