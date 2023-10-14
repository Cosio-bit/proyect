package edu.mtisw.testingwebapp.controllers;

import edu.mtisw.testingwebapp.entities.EstudianteEntity;
import edu.mtisw.testingwebapp.entities.HistorialAcademicoEntity;
import edu.mtisw.testingwebapp.services.HistorialAcademicoService;
import edu.mtisw.testingwebapp.services.OficinaRRHH;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/estudiantes")
public class HistorialAcademicoController {
    @Autowired
    private HistorialAcademicoService historialAcademicoService;


    // 1. Obtener todas las notas de todos los estudiantes (GET)
    @GetMapping("/listarNotas")
    public String listarNotas(Model model) {
        List<HistorialAcademicoEntity> historialesAcademicos = historialAcademicoService.obtenerHistorialAcademicos();
        model.addAttribute("historialesAcademicos", historialesAcademicos);
        return "VisualizarHistorialesAcademicos";
    }

    // 3. Obtener las notas de un estudiante en particular (GET)
    @GetMapping("/historialAcademico/{id}")
    public String mostrarHistorialAcademico(@PathVariable Long id, Model model) {
        HistorialAcademicoEntity historialAcademico = historialAcademicoService.obtenerPorEstudianteId(id);
        model.addAttribute("historialAcademico", historialAcademico);
        return "VisualizarHistorialAcademico";
    }

    // 4. Agregar una nueva nota a un estudiante (POST)
    @PostMapping("/historialAcademico/{id}/agregarNota")
    public String agregarNota(@PathVariable Long id, @RequestParam("nuevaNota") double nuevaNota) {
        // Implement your logic here to add a new grade to the academic history of the student with the provided ID.
        // If you need more details, let me know.
        historialAcademicoService.anadirNota(id, nuevaNota);
        return "redirect:/estudiantes/historialAcademico/" + id;
    }

    @GetMapping("/crearHistorialAcademico")
    public String estudianteForm(Model model) {
        // Puedes agregar lógica para prellenar el formulario si es necesario.
        model.addAttribute("estudiante", new HistorialAcademicoEntity());
        return "IngresarHistorialAcademico";
    }



/*
    @PostMapping("/crearHistorialAcademico")
    public ModelAndView nuevoHistorialAcademico(@RequestParam("notas") String notas) {
        // Implementa tu lógica aquí para guardar el historial académico.
        HistorialAcademicoEntity historialAcademico = historialAcademicoService.guardarHistorialAcademico(notas);

        // Crea un objeto ModelAndView y agrega los datos que deseas pasar a la vista
        ModelAndView modelAndView = new ModelAndView("IngresarHistorialAcademico");

        // Agrega el ID del HistorialAcademicoEntity al modelo
        modelAndView.addObject("historialAcademico", historialAcademico);

        return modelAndView;
    }*/



}
