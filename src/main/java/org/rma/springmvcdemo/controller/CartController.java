package org.rma.springmvcdemo.controller;

import org.rma.springmvcdemo.model.Cart;
import org.rma.springmvcdemo.model.Shopper;
import org.rma.springmvcdemo.service.CartService;
import org.rma.springmvcdemo.service.ShopperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ShopperService shopperService;

    @GetMapping
    public String viewCart(Model model) {
        Shopper shopper = getAuthenticatedShopper();
        Cart cart = cartService.getCartByShopperId(shopper.getId());
        model.addAttribute("cart", cart);
        return "cart";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam Long productId, @RequestParam int quantity) {
        Shopper shopper = getAuthenticatedShopper();
        cartService.addItemToCart(shopper.getId(), productId, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/remove/{productId}")
    public String removeFromCart(@PathVariable Long productId) {
        Shopper shopper = getAuthenticatedShopper();
        cartService.removeItemFromCart(shopper.getId(), productId);
        return "redirect:/cart";
    }

    @PostMapping("/update")
    public String updateCartItemQuantity(@RequestParam Long productId, @RequestParam int quantity) {
        Shopper shopper = getAuthenticatedShopper();
        cartService.updateItemQuantity(shopper.getId(), productId, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/checkout")
    public String checkout(Model model, Authentication authentication) {
        // Get the authenticated user's username
        String username = authentication.getName();

        // Find the shopper using their username
        Shopper shopper = shopperService.findByUsername(username).orElseThrow(() -> new RuntimeException("Shopper not found"));

        // Retrieve the shopper's cart
        Cart cart = cartService.getCartByShopperId(shopper.getId());

        // Prepare the receipt model attributes
        model.addAttribute("shopper", shopper);
        model.addAttribute("cart", cart);
        model.addAttribute("totalPrice", cart.getTotalPrice());

        return "checkout-receipt"; // A Thymeleaf template that displays the receipt
    }

    private Shopper getAuthenticatedShopper() {
        String username = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        return shopperService.findByUsername(username).orElseThrow(() -> new RuntimeException("Shopper not found"));
    }
}