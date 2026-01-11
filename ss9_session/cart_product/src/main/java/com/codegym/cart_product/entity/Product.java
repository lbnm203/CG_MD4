package com.codegym.cart_product.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "products")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotBlank(message = "Product Code can not be empty")
    private String code;

    @NotBlank(message = "Product name can not be empty")
    private String name;

//    @NotBlank(message = "Product image cannot be empty")
    private String imageUrl;

    @NotBlank(message = "Product price can not be empty")
    @Min(value = 0, message = "Price must be >= 0")
    private Double price;

    @NotBlank(message = "Product quantity can not be empty")
    @Min(value = 0, message = "Quantity must be >= 0")
    private Integer quantity;

    @Column(columnDefinition = "TEXT")
    private String description;
}
