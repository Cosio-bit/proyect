package edu.mtisw.testingwebapp.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import edu.mtisw.testingwebapp.entities.ReparacionEntity;
import edu.mtisw.testingwebapp.services.ReparacionService;
import edu.mtisw.testingwebapp.services.ProfesorService;
@Controller
@RequestMapping
public class ReparacionController {

    @Autowired
    private ReparacionService reparacionoService;

    @GetMapping("/reparaciones")
    public String listar(Model model) {
        List<ReparacionEntity> reparaciones = reparacionoService.obtenerReparacions();
        model.addAttribute("reparaciones", reparaciones);
        return "VisualizarReparacions";
    }

 @PostMapping("/reparaciones")
public ResponseEntity<?> agregarReparacion(@RequestParam("fechaReparacion") String fechaReparacion,
                                         @RequestParam("horaReparacion") String horaReparacion,
                                        @RequestParam("utilizacionHoras") String utilizacionHoras,
                                         @RequestParam("fechaDevolucion") String fechaDevolucion,
                                         @RequestParam("horaDevolucion") String horaDevolucion,
                                         @RequestParam("estadoDanado") String estadoDanado,
                                         @RequestParam("uso") String uso,
                                         @RequestParam("idProjector") String idProjector,
                                         @RequestParam("idProfesor") String idProfesor) {
    try {

        // Aquí asumo que tienes una lógica para manejar la creación del préstamo
        ReparacionEntity nuevoReparacion = reparacionoService.guardarReparacion(fechaReparacion, horaReparacion, utilizacionHoras, fechaDevolucion, horaDevolucion, estadoDanado, uso, idProjector, idProfesor);

        if (nuevoReparacion != null) {
            return ResponseEntity.ok(nuevoReparacion);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo crear el préstamo");
        }
    } catch (NumberFormatException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error en el formato de los números: " + e.getMessage());
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar la solicitud: " + e.getMessage());
    }
}

    // 3. Obtener los reparaciones de un projector en particular
    @GetMapping("/projectores/projector/reparaciones/{id}")
    public String mostrarReparaciones(@PathVariable("id") String projectorId, Model model) {
        List<ReparacionEntity> reparaciones = reparacionoService.obtenerReparacionesPorProjectorID(projectorId);

        model.addAttribute("reparaciones", reparaciones);
        return "VisualizarReparaciones";
    }

    

}





