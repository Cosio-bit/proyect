package edu.mtisw.testingwebapp.controllers;

import edu.mtisw.testingwebapp.entities.ReparacionEntity;
import edu.mtisw.testingwebapp.services.ReparacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping
public class ReparacionController {

    @Autowired
    private ReparacionService reparacionService;

    @GetMapping("/reparaciones")
    public String listar(Model model) {
        List<ReparacionEntity> reparaciones = reparacionService.obtenerReparacions();
        model.addAttribute("reparaciones", reparaciones);
        return "VisualizarReparaciones";
    }

@PostMapping("/reparaciones")
public ResponseEntity<?> agregarReparacion(
            @RequestParam("fechaIngreso") LocalDate fechaIngreso,
			@RequestParam("horaIngreso") LocalDate horaIngreso,
			@RequestParam("tipoReparacion") String tipoReparacion,
			@RequestParam("montoTotal") Integer montoTotal,
			@RequestParam("fechaSalida") LocalDate fechaSalida,
			@RequestParam("horaSalida") LocalDate horaSalida,
			@RequestParam("fechaRetiro") LocalDate fechaRetiro,
			@RequestParam("horaRetiro") LocalDate horaRetiro,
			@RequestParam("idVehiculo") String idVehiculo
    ){
    try {

        // Aquí asumo que tienes una lógica para manejar la creación del préstamo
        ReparacionEntity nuevoReparacion = reparacionService.guardarReparacion(fechaIngreso, horaIngreso, tipoReparacion, montoTotal,fechaSalida, horaSalida, fechaRetiro, horaRetiro, idVehiculo );

        if (nuevoReparacion != null) {
            return ResponseEntity.ok(nuevoReparacion);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo crear la reparacion");
        }
    } catch (NumberFormatException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error en el formato de los números: " + e.getMessage());
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar la solicitud: " + e.getMessage());
    }
}

    // 3. Obtener los reparaciones de un vehiculo en particular
    @GetMapping("/vehiculos/vehiculo/reparaciones/{id}")
    public String mostrarReparaciones(@PathVariable("id") String vehiculoId, Model model) {
        List<ReparacionEntity> reparaciones = reparacionService.obtenerReparacionesPorVehiculoID(vehiculoId);

        model.addAttribute("reparaciones", reparaciones);
        return "VisualizarReparaciones";
    }

    

}





