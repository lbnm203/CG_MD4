package com.codegym.book_borrowing_app.repository;

import com.codegym.book_borrowing_app.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBookRepository extends JpaRepository<Book, Long> {
}
