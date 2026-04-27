package com.cueva.app.controller;

import com.cueva.app.service.JuegoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PublicController {

    private final JuegoService juegoService;

    public PublicController(JuegoService juegoService) {
        this.juegoService = juegoService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("juegos", juegoService.listarTodos());
        return "public/index";
    }

    @GetMapping("/tienda")
    public String tienda(
            @RequestParam(required = false) String buscar,
            @RequestParam(required = false) String genero,
            @RequestParam(required = false) String plataforma,
            Model model) {

        var juegos = juegoService.listarTodos();

        if (buscar != null && !buscar.isBlank()) {
            juegos = juegoService.buscarPorTitulo(buscar);
        } else if (genero != null && !genero.isBlank()) {
            juegos = juegoService.filtrarPorGenero(genero);
        } else if (plataforma != null && !plataforma.isBlank()) {
            juegos = juegoService.filtrarPorPlataforma(plataforma);
        }

        model.addAttribute("juegos", juegos);
        model.addAttribute("generos", juegoService.listarGeneros());
        model.addAttribute("plataformas", juegoService.listarPlataformas());
        model.addAttribute("buscar", buscar);
        model.addAttribute("generoSeleccionado", genero);
        model.addAttribute("plataformaSeleccionada", plataforma);
        return "public/tienda";
    }

    @GetMapping("/juego/{id}")
    public String detalleJuego(@PathVariable Integer id, Model model) {
        var juego = juegoService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Juego no encontrado"));
        model.addAttribute("juego", juego);
        return "public/detalle-juego";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
