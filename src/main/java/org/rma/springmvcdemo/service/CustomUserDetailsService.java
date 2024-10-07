package org.rma.springmvcdemo.service;

import org.rma.springmvcdemo.model.AdminUser;
import org.rma.springmvcdemo.model.Shopper;
import org.rma.springmvcdemo.repository.AdminUserRepository;
import org.rma.springmvcdemo.repository.ShopperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private ShopperRepository shopperRepository;

    @Autowired
    private AdminUserRepository adminUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Try to find the user as a Shopper
        Optional<Shopper> shopper = shopperRepository.findByUsername(username);
        if (shopper.isPresent()) {
            return new org.springframework.security.core.userdetails.User(
                    shopper.get().getUsername(),
                    shopper.get().getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_SHOPPER"))  // Correct role
            );
        }

        // Try to find the user as an AdminUser
        Optional<AdminUser> adminUser = adminUserRepository.findByUsername(username);
        if (adminUser.isPresent()) {
            return new org.springframework.security.core.userdetails.User(
                    adminUser.get().getUsername(),
                    adminUser.get().getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"))  // Correct role
            );
        }

        throw new UsernameNotFoundException("User not found with username: " + username);
    }

}