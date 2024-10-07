---

# Auto Arcade - Product Management and Shopping Cart Application(basic ecommerce)

Welcome to **Auto Arcade**! This is a comprehensive Spring Boot application that allows users to manage automotive products, add products to their shopping cart, and generate checkout receipts. Auto Arcade provides a smooth shopping experience with essential features such as viewing products by style, updating product quantities, removing items from the cart, and printing checkout receipts. The application also implements role-based access with Shopper and Admin users.

## Table of Contents
- [Features](#features)
- [Getting Started](#getting-started)
- [Technologies Used](#technologies-used)
- [Setup](#setup)
- [Database Schema](#database-schema)
- [Working with the Application](#working-with-the-application)
- [Security](#security)
- [Future Enhancements](#future-enhancements)
- [Contributing](#contributing)

---

## Features

### Product Management
- **Admin Functionality**: Admin users can create, edit, and delete automotive products.
- **Product Details**: Products have key fields like make, model, trim, price, image URL, and style (e.g., Sport, Off-Road).
- **Filtering by Style**: Shoppers can filter products by style (Off-Road, Sport, Pro).
  
### Shopping Cart
- **Add to Cart**: Shoppers can add products to their cart with the desired quantity.
- **Update Quantity**: Shoppers can update the quantity of items in their cart.
- **Remove from Cart**: Shoppers can remove items from their cart.
- **Checkout and Print Receipt**: Shoppers can checkout and print a receipt of their purchase, displaying all products, quantities, and totals.

### User Authentication & Authorization
- **User Roles**: The application differentiates between Admin and Shopper roles.
  - Admin: Can manage products.
  - Shopper: Can browse products and use the cart functionality.
- **Login and Registration**: New shoppers can register, and existing users can log in to access the application.

---

## Getting Started

### Prerequisites
Before you start, ensure you have the following installed:
- Java 17+
- Maven 3.6+
- MySQL or any preferred relational database
- A browser to view the front end

### Running the Application
1. **Clone the repository:**
    ```bash
    git clone https://github.com/YourUsername/auto-arcade.git
    cd auto-arcade
    ```

2. **Database Setup:**
    Create a MySQL database named `productmanagement` (or any name you prefer, just update the `application.properties` accordingly).
    ```sql
    -- Drop and recreate the productmanagement database
    DROP DATABASE IF EXISTS productmanagement;
    CREATE DATABASE productmanagement;

    -- Use the productmanagement database
    USE productmanagement;
    ```

3. **Update the Database Configuration:**
    Modify `src/main/resources/application.properties` with your database credentials:
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/productmanagement
    spring.datasource.username=your_db_user
    spring.datasource.password=your_db_password
    ```

4. **Build and Run the Application:**
    Use Maven to build and run the Spring Boot application.
    ```bash
    mvn clean install
    mvn spring-boot:run
    ```

5. **Access the Application:**
    Open your browser and navigate to:
    ```
    http://localhost:8080
    ```

---

## Technologies Used

- **Backend**: Java, Spring Boot (Spring MVC, Spring Data JPA, Spring Security)
- **Database**: MySQL (JPA/Hibernate for ORM)
- **Frontend**: Thymeleaf, HTML5, CSS3
- **Build Tool**: Maven
- **Authentication**: Spring Security (BCrypt password encoding)

---

## Setup

### Project Structure
Here’s a brief overview of the project structure:
```
├── src
│   ├── main
│   │   ├── java
│   │   │   └── org.rma.springmvcdemo
│   │   │       ├── controller
│   │   │       ├── model
│   │   │       ├── repository
│   │   │       ├── service
│   │   │       └── SpringmvcdemoApplication.java
│   │   ├── resources
│   │   │   ├── templates
│   │   │   │   ├── cart.html
│   │   │   │   ├── checkout-receipt.html
│   │   │   │   └── productspage.html
│   │   │   └── application.properties
└───pom.xml
```

### Running the Application Locally
1. **Registration and Login**: New shoppers can register via `/register`, and Admin users can be pre-seeded using the SQL script.
2. **Product Management**: Admin users can manage products at `/products`.
3. **Cart Operations**: Shoppers can add products to their cart, update quantities, remove items, and checkout.

### SQL Starter Script
The application comes with a pre-configured SQL script to set up the database and insert some sample data:

```sql
-- Create the product table
CREATE TABLE product (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    make VARCHAR(255),
    model VARCHAR(255),
    trim VARCHAR(255),
    img_url VARCHAR(255),
    price DECIMAL(10, 2),
    style VARCHAR(255)
);

-- Create base_user table for authentication
CREATE TABLE base_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL
);

-- Insert sample products
INSERT INTO product (make, model, trim, img_url, price, style) VALUES
('Toyota', 'Tacoma', 'SE', '../images/tacoma-se-2023.jpg', 24000, 'Off-Road'),
('Toyota', 'Tacoma', 'SR', '../images/tacoma-sr-2023.webp', 28000, 'Off-Road'),
('Toyota', 'Tacoma', 'Sport', '../images/TRD-sport-2023.png', 32000, 'Sport'),
('Toyota', 'Tacoma', 'Pro', '../images/TRD-Pro-2023.jpg', 38000, 'Pro');

-- Create Shopper and Admin users
INSERT INTO base_user (name, username, password, role) VALUES 
('John Doe', 'shopper1@gmail.com', '$2b$12$encryptedPassword1', 'SHOPPER'),
('Admin User', 'admin1@gmail.com', '$2b$12$encryptedPassword2', 'ADMIN');
```

---

## Working with the Application

### Admin Functionalities:
- **Adding a Product**: Go to `/products/new` and fill in the product details (make, model, price, etc.). Click "Save" to add the product.
- **Editing a Product**: Go to `/products`, click "Edit" on any product to update details.
- **Deleting a Product**: On the product list, click "Delete" to remove a product.

### Shopper Functionalities:
- **Browse Products**: Visit `/productspage` to view the available products. Filter by style using query parameters (e.g., `/productspage?style=Sport`).
- **Add to Cart**: Add products to your cart by selecting the quantity and clicking "Add to Cart".
- **Update or Remove Items**: Manage items in the cart, either updating quantities or removing them completely.
- **Checkout and Print Receipt**: After reviewing your cart, click on "Checkout". The receipt page will allow you to print your receipt. A link back to the product page will be available for continued shopping.

---

## Security

The application uses **Spring Security** for authentication and role-based access control:
- **Shoppers** can view products, add them to the cart, and checkout.
- **Admin Users** can manage products (create, edit, delete) but cannot perform shopping-related actions.

### User Authentication Flow:
1. **Registration**: New users (shoppers) can register via the `/register` page.
2. **Login**: Shoppers and admins log in via `/login`. After login, shoppers will have access to shopping functionalities, while admins can access product management.

---

## Future Enhancements

1. **Order History**: Allow shoppers to view their past orders and receipts.
2. **Wishlist Feature**: Implement a wishlist where shoppers can save products for later.
3. **Product Ratings and Reviews**: Add the ability for shoppers to leave reviews and rate products.
4. **Improved Security**: Add features such as email verification and password reset functionality.

---

## Contributing

We welcome contributions to **Auto Arcade**! To contribute:
1. Fork the repository.
2. Create a new feature branch (`git checkout -b feature/your-feature`).
3. Commit your changes (`git commit -am 'Add your feature'`).
4. Push to the branch (`git push origin feature/your-feature`).
5. Open a pull request.

For major changes, please open an issue first to discuss what you would like to change.

---

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---
