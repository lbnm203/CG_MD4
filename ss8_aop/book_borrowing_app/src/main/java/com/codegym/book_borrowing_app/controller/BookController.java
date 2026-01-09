package com.codegym.book_borrowing_app.controller;

import com.codegym.book_borrowing_app.entity.Book;
import com.codegym.book_borrowing_app.entity.BookBorrow;
import com.codegym.book_borrowing_app.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("")
    public String showListBooks(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "book/list";
    }

    @GetMapping("/borrow/{id}")
    public String showBookDetail(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookService.findById(id));
        return "book/detail";
    }

    @PostMapping("/{id}/borrow")
    public String borrowBook(@PathVariable Long id, Model model) {
        String borrowCode = bookService.borrowBook(id);
        model.addAttribute("borrowCode", borrowCode);
        model.addAttribute("book", bookService.findById(id));
        return "book/borrow";
    }

    @GetMapping("/return")
    public String returnBookPage() {
        return "book/return";
    }

    @PostMapping("/return")
    public String returnBook(@RequestParam String borrowCode, RedirectAttributes redirectAttributes) {
        Book book = bookService.findById(1L);
        bookService.returnBook(borrowCode);
        redirectAttributes.addFlashAttribute("message", "Trả sách [" + book.getTitle() + "] thành công!");
        return "redirect:/books";
    }
}
