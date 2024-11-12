package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    // Método para encontrar um livro pelo ID
    public Optional<BookEntity> findById(Long id) {
        return bookRepository.findById(id); // Usando o método do repositório
    }

    // Método para listar todos os livros
    public List<BookEntity> findAllBooks() {
        return bookRepository.findAll(); // Retorna todos os livros
    }

    // Método para adicionar um novo livro
    public BookEntity addBook(BookEntity book) {
        return bookRepository.save(book); // Adiciona um novo livro
    }

    // Método para encontrar livros pelo nome do usuário publicador
    public List<BookEntity> findBooksByUser(String usuarioPublicador) {
        return bookRepository.findByUsuarioPublicador(usuarioPublicador); // Busca livros com base no nome do usuário
    }

    // Método para atualizar um livro
    public Optional<BookEntity> updateBook(Long id, BookEntity book) {
        if (bookRepository.existsById(id)) { // Verifica se o livro existe
            book.setId(id); // Garante que o ID do livro será mantido
            return Optional.of(bookRepository.save(book)); // Atualiza o livro
        }
        return Optional.empty(); // Retorna vazio se não encontrar o livro
    }

    // Método para excluir um livro
    public boolean deleteBook(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id); // Exclui o livro
            return true;
        }
        return false; // Retorna falso se não encontrar o livro
    }
}
