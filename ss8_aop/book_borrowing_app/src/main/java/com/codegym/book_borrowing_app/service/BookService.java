package com.codegym.book_borrowing_app.service;

import com.codegym.book_borrowing_app.entity.Book;
import com.codegym.book_borrowing_app.entity.BookBorrow;
import com.codegym.book_borrowing_app.exception.BorrowCodeInvalidException;
import com.codegym.book_borrowing_app.exception.OutOfStockException;
import com.codegym.book_borrowing_app.repository.IBookBorrowRepository;
import com.codegym.book_borrowing_app.repository.IBookRepository;
import jakarta.persistence.Entity;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class BookService implements IBookService{
    private final IBookRepository bookRepository;
    private final IBookBorrowRepository bookBorrowRepository;

    public BookService(IBookRepository bookRepository, IBookBorrowRepository bookBorrowRepository) {
        this.bookRepository = bookRepository;
        this.bookBorrowRepository = bookBorrowRepository;
    }


    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new NoResultException("Không tìm thấy sách với id: " + id + ""));
    }

    @Override
    public Boolean save(Book book) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }

    @Override
    @Transactional
    public String borrowBook(Long id) {
        Book book = findById(id);
        if (book.getQuantity() <= 0) {
            throw new OutOfStockException("Sách " + book.getTitle() + " đã hết hàng!");
        }

        book.setQuantity(book.getQuantity() - 1);
        bookRepository.save(book);

        String borrowCode = generateBorrowCode();

        BookBorrow bookBorrow = new BookBorrow();
        bookBorrow.setBook(book);
        bookBorrow.setBorrowCode(borrowCode);
        bookBorrow.setBorrowDate(LocalDateTime.now());
        bookBorrow.setReturnDate(null);
        bookBorrow.setIsReturned(false);
        bookBorrowRepository.save(bookBorrow);

        return borrowCode;
    }

    @Transactional
    @Override
    public void returnBook(String borrowCode) {
        BookBorrow bookBorrow = bookBorrowRepository
                .findByBorrowCodeAndIsReturnedFalse(borrowCode)
                .orElseThrow(() -> new BorrowCodeInvalidException("Mã mượn sách " + borrowCode + " không hợp lệ"));

        Book book = bookBorrow.getBook();
        book.setQuantity(book.getQuantity() + 1);
        bookRepository.save(book);

        bookBorrow.setIsReturned(true);
        bookBorrow.setReturnDate(LocalDateTime.now());
        bookBorrowRepository.save(bookBorrow);
    }

    private String generateBorrowCode() {
        Random random = new Random();
        String code;
        do {
            int randomNum = 10000 + random.nextInt(90000);
            code = String.valueOf(randomNum);
        } while (bookBorrowRepository.existsByBorrowCode(code));
        return code;
    }
}
