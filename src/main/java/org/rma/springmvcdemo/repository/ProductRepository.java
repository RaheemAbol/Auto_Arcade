package org.rma.springmvcdemo.repository;


import org.rma.springmvcdemo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByStyle(String style);
}