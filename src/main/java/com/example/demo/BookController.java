package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping(value = "/add", consumes = "multipart/form-data")
    public BookEntity addBook(
            @RequestParam("name") String name,
            @RequestParam("author") String author,
            @RequestParam("genre") String genre,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "usuarioPublicador", required = false) String usuarioPublicador,
            @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
            @RequestParam(value = "picture", required = false) MultipartFile picture
    ) throws IOException {
        BookEntity book = new BookEntity();
        book.setName(name);
        book.setAuthor(author);
        book.setGenre(genre);
        book.setDescription(description);
        book.setUsuarioPublicador(usuarioPublicador);
        book.setPhoneNumber(phoneNumber);

        if (picture != null && !picture.isEmpty()) {
            book.setPicture(picture.getBytes());
        }

        return bookService.addBook(book);
    }

    @GetMapping("/list")
    public List<BookEntity> listBooks() {
        return bookService.findAllBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookEntity> getBookById(@PathVariable Long id) {
        return bookService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/my-books")
    public List<BookEntity> getBooksByUser(@RequestParam String usuarioPublicador) {
        return bookService.findBooksByUser(usuarioPublicador);
    }

    @GetMapping("/my-books-by-phone")
    public List<BookEntity> getBooksByPhoneNumber(@RequestParam String phoneNumber) {
        return bookService.findBooksByPhoneNumber(phoneNumber);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookEntity> updateBook(@PathVariable Long id, @RequestBody BookEntity book) {
        return bookService.updateBook(id, book)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        if (bookService.deleteBook(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
