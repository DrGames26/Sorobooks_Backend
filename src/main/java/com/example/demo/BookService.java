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
        return bookRepository.findById(id); // Usando o método do repositório
    }

    public List<BookEntity> findAllBooks() {
        return bookRepository.findAll(); // Retorna todos os livros
    }

    public BookEntity addBook(BookEntity book) {
        return bookRepository.save(book); // Adiciona um novo livro
    }
}
