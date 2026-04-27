package com.cueva.app.repository;

import com.cueva.app.entity.Juego;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JuegoRepository extends JpaRepository<Juego, Integer> {
    List<Juego> findByTituloContainingIgnoreCase(String titulo);
    List<Juego> findByGeneroIgnoreCase(String genero);
    List<Juego> findByPlataformaIgnoreCase(String plataforma);
}
