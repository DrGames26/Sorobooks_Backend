package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Long> {

    // Novo método para buscar livros pelo nome do usuário publicador
    List<BookEntity> findByUsuarioPublicador(String usuarioPublicador); // Busca livros por usuário
}
