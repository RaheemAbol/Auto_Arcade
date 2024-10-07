package org.rma.springmvcdemo.service;

import org.rma.springmvcdemo.model.Cart;
import org.rma.springmvcdemo.model.CartItem;
import org.rma.springmvcdemo.model.Product;
import org.rma.springmvcdemo.model.Shopper;
import org.rma.springmvcdemo.repository.CartRepository;
import org.rma.springmvcdemo.repository.ProductRepository;
import org.rma.springmvcdemo.repository.ShopperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ShopperRepository shopperRepository;


    // public Cart getCartByShopperId(Long shopperId) {
    //     return cartRepository.findByShopperId(shopperId)
    //             .orElseGet(() -> createCartForShopper(shopperId));  // Create the cart if it doesn't exist
    // }

    public Cart getCartByShopperId(Long shopperId) {
        Shopper shopper = shopperRepository.findById(shopperId)
                .orElseThrow(() -> new IllegalArgumentException("Shopper not found"));

        return cartRepository.findByShopper(shopper).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setShopper(shopper);
            return cartRepository.save(newCart);
        });
    }

    private Cart createCartForShopper(Long shopperId) {
        Shopper shopper = shopperRepository.findById(shopperId)
                .orElseThrow(() -> new RuntimeException("Shopper not found"));

        Cart newCart = new Cart();
        newCart.setShopper(shopper);
        newCart.setTotalPrice(0.0);
        return cartRepository.save(newCart);
    }

    public Cart addItemToCart(Long shopperId, Long productId, int quantity) {
        Cart cart = getCartByShopperId(shopperId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Check if the product is already in the cart
        Optional<CartItem> existingCartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingCartItem.isPresent()) {
            // Update quantity
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            // Create new CartItem
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setPrice(product.getPrice());
            cartItem.setCart(cart);
            cart.getItems().add(cartItem);
        }

        updateCartTotals(cart);
        return cartRepository.save(cart);
    }

    public Cart removeItemFromCart(Long shopperId, Long productId) {
        Cart cart = getCartByShopperId(shopperId);

        // Find the item in the cart
        Optional<CartItem> itemToRemove = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (itemToRemove.isPresent()) {
            // Explicitly remove the item
            CartItem item = itemToRemove.get();
            cart.getItems().remove(item);

            // You may also need to explicitly remove the item if JPA cascading isn't working properly
            // cartItemRepository.delete(item); // Assuming you have a repository for CartItem
        }

        updateCartTotals(cart);
        return cartRepository.save(cart);
    }


    public Cart updateItemQuantity(Long shopperId, Long productId, int newQuantity) {
        Cart cart = getCartByShopperId(shopperId);

        cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(item -> item.setQuantity(newQuantity));

        updateCartTotals(cart);
        return cartRepository.save(cart);
    }

    private void updateCartTotals(Cart cart) {
        cart.calculateTotalPrice();
    }
}