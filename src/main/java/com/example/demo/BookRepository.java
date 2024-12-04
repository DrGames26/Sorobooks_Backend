package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
    List<BookEntity> findByUsuarioPublicador(String usuarioPublicador);
    List<BookEntity> findByPhoneNumber(String phoneNumber);
    List<BookEntity> findByNameContainingIgnoreCaseOrAuthorContainingIgnoreCase(String name, String author);
}
