package org.rma.springmvcdemo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.rma.springmvcdemo.model.Cart;
import org.rma.springmvcdemo.model.Shopper;
import org.rma.springmvcdemo.repository.CartRepository;
import org.rma.springmvcdemo.repository.ShopperRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest
public class ShopperServiceTest {

    @Mock
    private ShopperRepository shopperRepository;

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private ShopperService shopperService;

    private Shopper shopper;
    private Cart cart;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        shopper = new Shopper();
        shopper.setUsername("john_doe");
        shopper.setPassword("password");

        cart = new Cart();
        cart.setShopper(shopper);
    }

    @Test
    void testRegisterShopper_ShouldReturnShopper_WhenSuccessfullyRegistered() {
        // Arrange
        when(shopperRepository.save(any(Shopper.class))).thenReturn(shopper);
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        // Act
        Shopper result = shopperService.registerShopper(shopper);

        // Assert
        assertEquals(shopper, result);
        verify(shopperRepository, times(1)).save(shopper);
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
    void testFindByUsername_ShouldReturnShopper_WhenUsernameExists() {
        // Arrange
        when(shopperRepository.findByUsername("john_doe")).thenReturn(Optional.of(shopper));

        // Act
        Optional<Shopper> result = shopperService.findByUsername("john_doe");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("john_doe", result.get().getUsername());
        verify(shopperRepository, times(1)).findByUsername("john_doe");
    }

    @Test
    void testFindByUsername_ShouldReturnEmpty_WhenUsernameDoesNotExist() {
        // Arrange
        when(shopperRepository.findByUsername("unknown_user")).thenReturn(Optional.empty());

        // Act
        Optional<Shopper> result = shopperService.findByUsername("unknown_user");

        // Assert
        assertFalse(result.isPresent());
        verify(shopperRepository, times(1)).findByUsername("unknown_user");
    }
}