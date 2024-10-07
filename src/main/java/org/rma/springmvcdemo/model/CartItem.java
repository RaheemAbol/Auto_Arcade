package org.rma.springmvcdemo.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;
    private double price;

    public double getTotalPrice() {
        return quantity * price;
    }

    // Getter for Product
    public Product getProduct() {
        return product;
    }

    // Setter for Product
    public void setProduct(Product product) {
        this.product = product;
    }

    // Getter for Quantity
    public int getQuantity() {
        return quantity;
    }

    // Setter for Quantity
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}