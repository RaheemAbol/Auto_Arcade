package org.rma.springmvcdemo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.rma.springmvcdemo.model.Cart;
import org.rma.springmvcdemo.model.CartItem;
import org.rma.springmvcdemo.model.Product;
import org.rma.springmvcdemo.model.Shopper;
import org.rma.springmvcdemo.repository.CartRepository;
import org.rma.springmvcdemo.repository.ProductRepository;
import org.rma.springmvcdemo.repository.ShopperRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest
public class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ShopperRepository shopperRepository;

    @InjectMocks
    private CartService cartService;

    private Cart cart;
    private Shopper shopper;
    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize a Shopper
        shopper = new Shopper();
        shopper.setId(1L);

        // Initialize a Product
        product = new Product();
        product.setId(1L);
        product.setPrice(100.0);

        // Initialize a Cart
        cart = new Cart();
        cart.setShopper(shopper);
        cart.setItems(new ArrayList<>()); // Initialize the cart items list

        // Mock behavior
        when(shopperRepository.findById(1L)).thenReturn(Optional.of(shopper));
        when(cartRepository.findByShopper(shopper)).thenReturn(Optional.of(cart));
    }

    @Test
    void testGetCartByShopperId_ShouldReturnCart_WhenShopperExists() {
        // Act
        Cart result = cartService.getCartByShopperId(1L);

        // Assert
        assertNotNull(result);
        assertEquals(shopper, result.getShopper());
        verify(shopperRepository, times(1)).findById(1L);
        verify(cartRepository, times(1)).findByShopper(shopper);
    }

    @Test
    void testAddItemToCart_ShouldAddItem_WhenProductExists() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // Act
        cartService.addItemToCart(1L, 1L, 2);

        // Assert
        assertEquals(1, cart.getItems().size());
        CartItem addedItem = cart.getItems().get(0);
        assertEquals(2, addedItem.getQuantity());
        assertEquals(product, addedItem.getProduct());
        verify(cartRepository, times(1)).save(cart);
    }

    @Test
    void testRemoveItemFromCart_ShouldRemoveItem_WhenProductExists() {
        // Arrange
        CartItem item = new CartItem();
        item.setProduct(product);
        item.setQuantity(2);
        cart.getItems().add(item);

        // Act
        cartService.removeItemFromCart(1L, 1L);

        // Assert
        assertTrue(cart.getItems().isEmpty());
        verify(cartRepository, times(1)).save(cart);
    }

    @Test
    void testUpdateItemQuantity_ShouldUpdateQuantity_WhenProductExists() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        CartItem item = new CartItem();
        item.setProduct(product);
        item.setQuantity(2);
        cart.getItems().add(item);

        // Act
        cartService.updateItemQuantity(1L, 1L, 5);

        // Assert
        assertEquals(5, cart.getItems().get(0).getQuantity());
        verify(cartRepository, times(1)).save(cart);
    }
}