package com.codegym.book_borrowing_app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "book_borrows")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookBorrow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long borrowId;

    @Column(unique = true, length = 5)
    private String borrowCode;

    private LocalDateTime borrowDate;
    private LocalDateTime returnDate;

    private Boolean isReturned;

    @ManyToOne
    private Book book;
}
