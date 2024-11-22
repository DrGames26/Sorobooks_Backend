package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Long> {

    // Método para buscar livros pelo nome do usuário publicador
    List<BookEntity> findByUsuarioPublicador(String usuarioPublicador);

    // Novo método para buscar livros pelo número de telefone
    List<BookEntity> findByPhoneNumber(String phoneNumber); // Busca livros pelo número de telefone
}
