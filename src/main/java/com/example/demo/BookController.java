package com.example.demo;

import java.util.List;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{id}")
    public ResponseEntity<BookEntity> getBookById(@PathVariable Long id) {
        return bookService.findById(id)
                .map(book -> ResponseEntity.ok(book)) // Retorna o livro se encontrado
                .orElse(ResponseEntity.notFound().build()); // Retorna 404 se não encontrado
    }
}
