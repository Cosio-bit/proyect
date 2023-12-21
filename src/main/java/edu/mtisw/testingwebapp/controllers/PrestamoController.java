package edu.mtisw.testingwebapp.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import edu.mtisw.testingwebapp.entities.PrestamoEntity;
import edu.mtisw.testingwebapp.services.PrestamoService;

@Controller
@RequestMapping
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;

    @GetMapping("/listarPrestamos")
    public String listar(Model model) {
        List<PrestamoEntity> prestamos = prestamoService.obtenerPrestamos();
        model.addAttribute("prestamos", prestamos);
        return "VisualizarPrestamos";
    }
    @GetMapping("/nuevoPrestamo")
    public String prestamoForm(Model model) {
        // Puedes agregar lógica para prellenar el formulario si es necesario.
        model.addAttribute("prestamo", new PrestamoEntity());
        return "IngresarPrestamo";
    }

    // 3. Obtener los pagos de un profesor en particular (GET)
    @GetMapping("/projectores/prestamo/{id}")
    public String mostrarPrestamos(@PathVariable Long projectorID, Model model) {
        //List<DetallePagoEntity> listNotPagados = prestamoService.findbynotpagado(id);
        //prestamoService.updateDetallesPagos(listNotPagados); //sepan tyche como traer el promedio hasta aca
        List<PrestamoEntity> listPrestamo = prestamoService.obtenerPrestamosPorProjectorID(projectorID);
        model.addAttribute("listPrestamo", listPrestamo);
        return "VisualizarPrestamos";

    }

    // 4. Agregar una nueva nota a un profesor (POST)
    @PostMapping("/projectores/prestamo/{id}/{prestamoID}")
    public String agregarPrestamo(@PathVariable Long id, @PathVariable Long prestamoID) {
        // Implementa aquí la lógica para marcar el detalle de pago como pagado.
        // Utiliza el prestamoId para identificar el detalle de pago que se debe marcar como pagado.
        //PrestamoService.prestar(idpago);
        // Después de procesar el pago, puedes redirigir al usuario a una página de confirmación o a la página de detalles de pagos.
        return "redirect:/projectores/prestamo/" + id;
    }


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
    */

}
