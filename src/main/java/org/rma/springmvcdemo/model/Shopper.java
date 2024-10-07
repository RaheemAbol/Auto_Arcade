package org.rma.springmvcdemo.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@DiscriminatorValue("SHOPPER")
public class Shopper extends BaseUser {

    @OneToOne(mappedBy = "shopper", cascade = CascadeType.ALL)
    private Cart cart;

    public Shopper() {
        super();
        setRole(Role.SHOPPER);
    }


}