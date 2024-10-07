package org.rma.springmvcdemo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "shopper_id")
    private Shopper shopper;


    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();


    private double totalPrice;

    public void calculateTotalPrice() {
        totalPrice = items.stream()
                .mapToDouble(CartItem::getTotalPrice)
                .sum();
    }
}