package com.codegym.book_borrowing_app.repository;

import com.codegym.book_borrowing_app.entity.BookBorrow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IBookBorrowRepository extends JpaRepository<BookBorrow, Long> {
    Optional<BookBorrow> findByBorrowCodeAndIsReturnedFalse(String borrowCode);
    Boolean existsByBorrowCode(String code);
}
