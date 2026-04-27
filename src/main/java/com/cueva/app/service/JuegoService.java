package com.cueva.app.service;

import com.cueva.app.entity.Juego;
import com.cueva.app.repository.JuegoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class JuegoService {

    private final JuegoRepository juegoRepository;

    public JuegoService(JuegoRepository juegoRepository) {
        this.juegoRepository = juegoRepository;
    }

    public List<Juego> listarTodos() {
        return juegoRepository.findAll();
    }

    public Optional<Juego> buscarPorId(Integer id) {
        return juegoRepository.findById(id);
    }

    public List<Juego> buscarPorTitulo(String titulo) {
        return juegoRepository.findByTituloContainingIgnoreCase(titulo);
    }

    public List<Juego> filtrarPorGenero(String genero) {
        return juegoRepository.findByGeneroIgnoreCase(genero);
    }

    public List<Juego> filtrarPorPlataforma(String plataforma) {
        return juegoRepository.findByPlataformaIgnoreCase(plataforma);
    }

    public Juego guardar(Juego juego) {
        return juegoRepository.save(juego);
    }

    public void eliminar(Integer id) {
        juegoRepository.deleteById(id);
    }

    public List<String> listarGeneros() {
        return juegoRepository.findAll()
                .stream()
                .map(Juego::getGenero)
                .distinct()
                .sorted()
                .toList();
    }

    public List<String> listarPlataformas() {
        return juegoRepository.findAll()
                .stream()
                .map(Juego::getPlataforma)
                .filter(p -> p != null && !p.isBlank())
                .distinct()
                .sorted()
                .toList();
    }
}
