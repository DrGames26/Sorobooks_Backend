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

    // Método para encontrar livros pelo nome do usuário publicador
    public List<BookEntity> findBooksByUser(String usuarioPublicador) {
        return bookRepository.findByUsuarioPublicador(usuarioPublicador); // Busca livros com base no nome do usuário
    }
}
