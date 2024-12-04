package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Optional<BookEntity> findById(Long id) {
        return bookRepository.findById(id);
    }

    public List<BookEntity> findAllBooks() {
        return bookRepository.findAll();
    }

    public BookEntity addBook(BookEntity book) {
        return bookRepository.save(book);
    }

    public List<BookEntity> findBooksByUser(String usuarioPublicador) {
        return bookRepository.findByUsuarioPublicador(usuarioPublicador);
    }

    public List<BookEntity> findBooksByPhoneNumber(String phoneNumber) {
        return bookRepository.findByPhoneNumber(phoneNumber);
    }

    public Optional<BookEntity> updateBook(Long id, BookEntity book) {
        if (bookRepository.existsById(id)) {
            book.setId(id);
            return Optional.of(bookRepository.save(book));
        }
        return Optional.empty();
    }

    public boolean deleteBook(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<BookEntity> searchBooks(String query) {
        return bookRepository.findByNameContainingIgnoreCaseOrAuthorContainingIgnoreCase(query, query);
    }
}
