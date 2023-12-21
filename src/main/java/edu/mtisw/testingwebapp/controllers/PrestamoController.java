package edu.mtisw.testingwebapp.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
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
                                             @RequestParam("fechaEntrega") String fechaEntrega,
                                             @RequestParam("fechaDevolucion") String fechaDevolucion,
                                             @RequestParam("estado") String estado,
                                             @RequestParam("projectorID") Long projectorID,
                                             @RequestParam("profesorID") Long profesorID) {
        try {
            PrestamoEntity nuevoPrestamo = prestamoService.guardarPrestamo(
                    fechaPrestamo, fechaEntrega, fechaDevolucion, estado, projectorID, profesorID);

            if (nuevoPrestamo != null) {
                return ResponseEntity.ok(nuevoPrestamo);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo crear el préstamo");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar la solicitud: " + e.getMessage());
        }
    }
}


    /*
    // 3. Obtener los prestamos de un projector en particular
    @GetMapping("/projectores/projector/prestamo/{id}")
    public String mostrarPrestamos(@PathVariable Long projectorID, Model model) {
        //List<DetallePagoEntity> listNotPagados = prestamoService.findbynotpagado(id);
        //prestamoService.updateDetallesPagos(listNotPagados); //sepan tyche como traer el promedio hasta aca
        List<PrestamoEntity> listPrestamo = prestamoService.obtenerPrestamosPorProjectorID(projectorID);
        model.addAttribute("listPrestamo", listPrestamo);
        return "VisualizarPrestamos";

    }*/

/*
    @GetMapping("/crearDetallePago")
    public String profesorForm(Model model) {
        // Puedes agregar lógica para prellenar el formulario si es necesario.
        model.addAttribute("prestamo", new DetallePagoEntity());
        return "IngresarDetallePago";
    }

 */
/*
    @PostMapping("/nuevoPagos")
    public String nuevoDetallePago(DetallePagoEntity prestamo) {
        // Puedes agregar validaciones o lógica de manejo de datos aquí.
        prestamoService.guardarDetallePago(projectorId, cuotasPactadas,  montoTotal);
        return "VisualizarDetallesPagos";
    }

 */
/*
    @PostMapping("/Prestamo/{id}/agregarDetalle")
    public String agregarDetalle(@PathVariable Long id, @RequestParam("nuevaNota") double efectivo) {
        // Implement your logic here to add a new grade to the academic history of the student with the provided ID.
        // If you need more details, let me know.
        prestamoService.anadirDetalle(projectorId, cuotasPactadas, montoTotal);
        return "redirect:/profesores/projector/" + id;
    }


     @GetMapping("/nuevoPrestamo")
    public String prestamoForm(Model model) {
        // Puedes agregar lógica para prellenar el formulario si es necesario.
        model.addAttribute("prestamo", new PrestamoEntity());
        return "IngresarPrestamo";
    }
    */


