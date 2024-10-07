package org.rma.springmvcdemo.controller;

import jakarta.validation.Valid;
import org.rma.springmvcdemo.model.Product;
import org.rma.springmvcdemo.model.Shopper;
import org.rma.springmvcdemo.service.ShopperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private ShopperService shopperService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Show the home page
    @GetMapping("/")
    public String getHome() {
        return "home";
    }

    // Show the login form
    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    // Show the registration form
    @GetMapping("/register")
    public String getRegister(Model model) {
        model.addAttribute("shopper", new Shopper());  // Initialize empty Shopper object for the form
        return "register";
    }



    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("shopper") Shopper shopper, BindingResult result) {
        if (result.hasErrors()) {
            return "register";  // Return back to the registration form if there are validation errors
        }
        shopper.setPassword(passwordEncoder.encode(shopper.getPassword()));
        shopperService.registerShopper(shopper);  // Use the method that creates the shopper and their cart
        return "redirect:/login";
    }


    @GetMapping("/productspage")
    public String getProducts(@RequestParam(value = "style", required = false) String style, Model model) {
        if (style != null) {
            model.addAttribute("products", shopperService.getProductsByStyle(style));
        } else {
            model.addAttribute("products", shopperService.getAllProducts());
        }
        return "products";
    }

//    // Show products page with optional filtering
//    @GetMapping("/productspage")
//    public String getProducts(@RequestParam(value = "style", required = false) String style, Model model) {
//        List<Product> products;
//        if (style != null && !style.isEmpty()) {
//            products = shopperService.getProductsByStyle(style);
//        } else {
//            products = shopperService.getAllProducts();
//        }
//        model.addAttribute("products", products);
//        return "products";
//    }



}