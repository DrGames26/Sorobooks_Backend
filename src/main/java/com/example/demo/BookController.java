package com.example.demo;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/add")
    public BookEntity addBook(@RequestBody BookEntity book) {
        return bookService.addBook(book); // Retorna o livro adicionado
    }

    @GetMapping("/list")
    public List<BookEntity> listBooks() {
        return bookService.findAllBooks(); // Retorna a lista de livros
    }
}
