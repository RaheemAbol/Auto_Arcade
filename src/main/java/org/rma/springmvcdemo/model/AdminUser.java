package org.rma.springmvcdemo.model;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class AdminUser extends BaseUser {

    public AdminUser() {
    }


}