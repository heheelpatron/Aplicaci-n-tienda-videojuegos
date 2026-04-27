package com.cueva.app.controller;

import com.cueva.app.entity.Juego;
import com.cueva.app.service.JuegoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final JuegoService juegoService;

    public AdminController(JuegoService juegoService) {
        this.juegoService = juegoService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalJuegos", juegoService.listarTodos().size());
        model.addAttribute("juegos", juegoService.listarTodos());
        return "admin/dashboard";
    }

    @GetMapping("/juegos")
    public String listarJuegos(@RequestParam(required = false) String buscar, Model model) {
        var juegos = buscar != null && !buscar.isBlank()
                ? juegoService.buscarPorTitulo(buscar)
                : juegoService.listarTodos();
        model.addAttribute("juegos", juegos);
        model.addAttribute("buscar", buscar);
        return "admin/juegos";
    }

    @GetMapping("/juegos/nuevo")
    public String nuevoJuegoForm(Model model) {
        model.addAttribute("juego", new Juego());
        model.addAttribute("accion", "Nuevo");
        return "admin/juego-form";
    }

    @GetMapping("/juegos/editar/{id}")
    public String editarJuegoForm(@PathVariable Integer id, Model model) {
        var juego = juegoService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Juego no encontrado: " + id));
        model.addAttribute("juego", juego);
        model.addAttribute("accion", "Editar");
        return "admin/juego-form";
    }

    @PostMapping("/juegos/guardar")
    public String guardarJuego(
            @Valid @ModelAttribute("juego") Juego juego,
            BindingResult result,
            RedirectAttributes flash,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("accion", juego.getIdJuego() == null ? "Nuevo" : "Editar");
            return "admin/juego-form";
        }
        juegoService.guardar(juego);
        flash.addFlashAttribute("exito", "Juego guardado correctamente.");
        return "redirect:/admin/juegos";
    }

    @PostMapping("/juegos/eliminar/{id}")
    public String eliminarJuego(@PathVariable Integer id, RedirectAttributes flash) {
        juegoService.eliminar(id);
        flash.addFlashAttribute("exito", "Juego eliminado correctamente.");
        return "redirect:/admin/juegos";
    }
}
