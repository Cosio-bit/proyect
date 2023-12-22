package edu.mtisw.testingwebapp.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import edu.mtisw.testingwebapp.entities.PrestamoEntity;
import edu.mtisw.testingwebapp.services.PrestamoService;
import edu.mtisw.testingwebapp.services.ProfesorService;
@Controller
@RequestMapping
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;

    @GetMapping("/prestamos")
    public String listar(Model model) {
        List<PrestamoEntity> prestamos = prestamoService.obtenerPrestamos();
        model.addAttribute("prestamos", prestamos);
        return "VisualizarPrestamos";
    }

 @PostMapping("/prestamos")
public ResponseEntity<?> agregarPrestamo(@RequestParam("fechaPrestamo") String fechaPrestamo,
                                         @RequestParam("horaPrestamo") String horaPrestamo,
                                        @RequestParam("utilizacionHoras") String utilizacionHoras,
                                         @RequestParam("fechaDevolucion") String fechaDevolucion,
                                         @RequestParam("horaDevolucion") String horaDevolucion,
                                         @RequestParam("estadoDanado") String estadoDanado,
                                         @RequestParam("idProjector") String idProjector,
                                         @RequestParam("idProfesor") String idProfesor) {
    try {

        // Aquí asumo que tienes una lógica para manejar la creación del préstamo
        PrestamoEntity nuevoPrestamo = prestamoService.guardarPrestamo(fechaPrestamo, horaPrestamo, utilizacionHoras, fechaDevolucion, horaDevolucion, estadoDanado, idProjector, idProfesor);

        if (nuevoPrestamo != null) {
            return ResponseEntity.ok(nuevoPrestamo);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo crear el préstamo");
        }
    } catch (NumberFormatException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error en el formato de los números: " + e.getMessage());
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar la solicitud: " + e.getMessage());
    }
}

    // 3. Obtener los prestamos de un projector en particular
    @GetMapping("/projectores/projector/prestamos/{id}")
    public String mostrarPrestamos(@PathVariable("id") String projectorId, Model model) {
        List<PrestamoEntity> prestamos = prestamoService.obtenerPrestamosPorProjectorID(projectorId);

        model.addAttribute("prestamos", prestamos);
        return "VisualizarPrestamos";
    }
}





