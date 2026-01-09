package com.codegym.book_borrowing_app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 100, columnDefinition = "VARCHAR(100)")
    private String title;

    @Column(name = "author", length = 100, columnDefinition = "VARCHAR(100)")
    private String author;

    private Integer quantity;
}


