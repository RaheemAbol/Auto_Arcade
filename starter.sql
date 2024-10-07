-- Drop and recreate the productmanagement database
DROP DATABASE IF EXISTS productmanagement;
CREATE DATABASE productmanagement;

-- Use the productmanagement database
USE productmanagement;

-- Create the product table with a BIGINT primary key
CREATE TABLE product (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    make VARCHAR(255),
    model VARCHAR(255),
    trim VARCHAR(255),
    img_url VARCHAR(255),
    price DECIMAL(10, 2), -- Monetary field with precision
    style VARCHAR(255)  -- Style field included
);

-- Create the base_user table with a BIGINT primary key and a larger password field
CREATE TABLE base_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,  -- Set to 255 to accommodate bcrypt hashes
    role VARCHAR(255) NOT NULL
);

-- Create the shopper table, referencing base_user with a BIGINT foreign key
CREATE TABLE shopper (
    id BIGINT PRIMARY KEY,
    CONSTRAINT fk_shopper_baseuser FOREIGN KEY (id) REFERENCES base_user(id) ON DELETE CASCADE
);

-- Create the admin_user table, referencing base_user with a BIGINT foreign key
CREATE TABLE admin_user (
    id BIGINT PRIMARY KEY,
    CONSTRAINT fk_adminuser_baseuser FOREIGN KEY (id) REFERENCES base_user(id) ON DELETE CASCADE
);

-- Create the cart table, with a BIGINT primary key and foreign key referencing the shopper table
CREATE TABLE cart (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    shopper_id BIGINT,
    total_price DECIMAL(10, 2) DEFAULT 0, -- Monetary field with precision
    CONSTRAINT fk_cart_shopper FOREIGN KEY (shopper_id) REFERENCES shopper(id) ON DELETE CASCADE
);

-- Create the cart_item table, with BIGINT foreign keys referencing the cart and product tables
CREATE TABLE cart_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cart_id BIGINT,
    product_id BIGINT,
    quantity INT,
    price DECIMAL(10, 2), -- Monetary field with precision
    CONSTRAINT fk_cartitem_cart FOREIGN KEY (cart_id) REFERENCES cart(id) ON DELETE CASCADE,
    CONSTRAINT fk_cartitem_product FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE
);

-- Insert product data
INSERT INTO product (make, model, trim, img_url, price, style) VALUES
('Toyota', 'Tacoma', 'SE', '../images/tacoma-se-2023.jpg', 24000, 'Off-Road'),
('Toyota', 'Tacoma', 'SR', '../images/tacoma-sr-2023.webp', 28000, 'Off-Road'),
('Toyota', 'Tacoma', 'Sport', '../images/TRD-sport-2023.png', 32000, 'Sport'),
('Toyota', 'Tacoma', 'Off-Road', '../images/TRD-off-road-2023.jpg', 34000, 'Off-Road'),
('Toyota', 'Tacoma', 'Pro', '../images/TRD-Pro-2023.jpg', 38000, 'Pro');

-- Insert base_user data (with encrypted passwords)
INSERT INTO base_user (name, username, password, role) VALUES 
('John Doe', 'shopper1@gmail.com', '$2b$12$vVUgTloOpMhA7t06XsGC1O1z43vFO0q/BAIc4yO4E5zj5eVLeAuWi', 'SHOPPER'),-- Password(shopper123)
('Jane Doe', 'shopper2@gmail.com', '$2b$12$eQ1opac6JONjednSYW5GHeYnspux3IaqC53vUGpDGEidoNLcoE7zC', 'SHOPPER'),-- Password(shopper456)
('Admin User', 'admin1@gmail.com', '$2b$12$Lc0rowmThSLDIGMJ7URDZunOm6kxaJUUoC5oF9kLqwRphMa1TpC2W', 'ADMIN');-- Password(admin123)



-- Insert shopper data (corresponding to base_user shoppers)
INSERT INTO shopper (id) VALUES (1), (2);

-- Insert admin_user data (corresponding to base_user admin)
INSERT INTO admin_user (id) VALUES (3);

-- Insert carts for shoppers
INSERT INTO cart (shopper_id, total_price) VALUES 
(1, 0),  -- Shopper 1
(2, 0);  -- Shopper 2


-- View tables
SELECT * FROM product;
SELECT * FROM base_user;
SELECT * FROM shopper;
SELECT * FROM admin_user;
SELECT * FROM cart;
SELECT * FROM cart_item;


