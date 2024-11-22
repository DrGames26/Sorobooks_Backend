package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    // Encontrar livro pelo ID
    public Optional<BookEntity> findById(Long id) {
        return bookRepository.findById(id);
    }

    // Listar todos os livros
    public List<BookEntity> findAllBooks() {
        return bookRepository.findAll();
    }

    // Adicionar novo livro
    public BookEntity addBook(BookEntity book) {
        return bookRepository.save(book);
    }

    // Encontrar livros pelo nome do usuário publicador
    public List<BookEntity> findBooksByUser(String usuarioPublicador) {
        return bookRepository.findByUsuarioPublicador(usuarioPublicador);
    }

    // Encontrar livros pelo número de telefone do usuário publicador
    public List<BookEntity> findBooksByPhoneNumber(String phoneNumber) {
        return bookRepository.findByPhoneNumber(phoneNumber); // Busca livros pelo número de telefone
    }

    // Atualizar um livro
    public Optional<BookEntity> updateBook(Long id, BookEntity book) {
        if (bookRepository.existsById(id)) {
            book.setId(id);
            return Optional.of(bookRepository.save(book));
        }
        return Optional.empty();
    }

    // Excluir um livro
    public boolean deleteBook(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
