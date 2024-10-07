package org.rma.springmvcdemo.service;

import org.rma.springmvcdemo.model.Cart;
import org.rma.springmvcdemo.model.Product;
import org.rma.springmvcdemo.model.Shopper;
import org.rma.springmvcdemo.repository.CartRepository;
import org.rma.springmvcdemo.repository.ProductRepository;
import org.rma.springmvcdemo.repository.ShopperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShopperService {

    @Autowired
    private ShopperRepository shopperRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    public Shopper registerShopper(Shopper shopper) {
        Shopper savedShopper = shopperRepository.save(shopper);

        // Create and save a new Cart for the registered Shopper
        Cart cart = new Cart();
        cart.setShopper(savedShopper);
        cartRepository.save(cart);

        return savedShopper;
    }

//    public Shopper createShopper(Shopper shopper) {
//        return shopperRepository.save(shopper);
//    }

    public List<Product> getProductsByStyle(String style) {
        return productRepository.findByStyle(style);
    }



    public Optional<Shopper> findByUsername(String username) {
        return shopperRepository.findByUsername(username);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}