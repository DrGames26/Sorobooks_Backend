package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    // Construtor para injeção de dependência
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // Endpoint para adicionar um livro
    @PostMapping("/add")
    public BookEntity addBook(@RequestBody BookEntity book) {
        return bookService.addBook(book); // Retorna o livro adicionado
    }

    // Endpoint para listar todos os livros
    @GetMapping("/list")
    public List<BookEntity> listBooks() {
        return bookService.findAllBooks(); // Retorna a lista de livros
    }

    // Endpoint para buscar um livro pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<BookEntity> getBookById(@PathVariable Long id) {
        return bookService.findById(id)
                .map(book -> ResponseEntity.ok(book)) // Retorna o livro se encontrado
                .orElse(ResponseEntity.notFound().build()); // Retorna 404 se não encontrado
    }

    // Endpoint para buscar os livros de um usuário pelo nome do publicador
    @GetMapping("/my-books")
    public List<BookEntity> getBooksByUser(@RequestParam String usuarioPublicador) {
        return bookService.findBooksByUser(usuarioPublicador); // Retorna os livros do usuário publicador
    }

    // Endpoint para buscar os livros de um usuário pelo número de telefone
    @GetMapping("/my-books-by-phone")
    public List<BookEntity> getBooksByPhoneNumber(@RequestParam String phoneNumber) {
        return bookService.findBooksByPhoneNumber(phoneNumber); // Retorna os livros do usuário pelo número de telefone
    }

    // Endpoint para editar um livro
    @PutMapping("/{id}")
    public ResponseEntity<BookEntity> updateBook(@PathVariable Long id, @RequestBody BookEntity book) {
        return bookService.updateBook(id, book)
                .map(updatedBook -> ResponseEntity.ok(updatedBook)) // Retorna o livro atualizado
                .orElse(ResponseEntity.notFound().build()); // Retorna 404 se não encontrado
    }

    // Endpoint para excluir um livro
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        if (bookService.deleteBook(id)) {
            return ResponseEntity.noContent().build(); // Retorna 204 (sem conteúdo) se o livro for excluído com sucesso
        } else {
            return ResponseEntity.notFound().build(); // Retorna 404 se não encontrado
        }
    }
}
