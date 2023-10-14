package edu.mtisw.testingwebapp.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import edu.mtisw.testingwebapp.entities.DetallePagoEntity;
import edu.mtisw.testingwebapp.services.DetallePagoService;

@Controller
@RequestMapping
public class DetallePagoController {

    @Autowired
    private DetallePagoService detallePagoService;
    @GetMapping("/listarPagos")
    public String listar(Model model) {
        List<DetallePagoEntity> detallesPagos = detallePagoService.obtenerDetallesPagos();
        model.addAttribute("detallesPagos", detallesPagos);
        return "VisualizarDetallesPagos";
    }
    @GetMapping("/nuevoPagos")
    public String detallePagoForm(Model model) {
        // Puedes agregar lógica para prellenar el formulario si es necesario.
        model.addAttribute("detallePago", new DetallePagoEntity());
        return "IngresarDetallePago";
    }

    // 3. Obtener los pagos de un estudiante en particular (GET)
    @GetMapping("/estudiantes/historialArancel/detallePagos/{id}")
    public String mostrarDetallePagos(@PathVariable Long id, Model model) {
        //List<DetallePagoEntity> listNotPagados = detallePagoService.findbynotpagado(id);
        //detallePagoService.updateDetallesPagos(listNotPagados); //sepan tyche como traer el promedio hasta aca
        List<DetallePagoEntity> listDetallePago = detallePagoService.obtenerPorHistorialArancelID(id); //supongo que aqui ya estaran updateados
        model.addAttribute("listDetallePago", listDetallePago);
        return "VisualizarDetallesPagos";

    }

    // 4. Agregar una nueva nota a un estudiante (POST)
    @PostMapping("/estudiantes/historialArancel/detallePago/{id}/{idpago}")
    public String agregarPago(@PathVariable Long id, @PathVariable Long idpago) {
        // Implementa aquí la lógica para marcar el detalle de pago como pagado.
        // Utiliza el detallePagoId para identificar el detalle de pago que se debe marcar como pagado.
        detallePagoService.pagar(idpago);
        // Después de procesar el pago, puedes redirigir al usuario a una página de confirmación o a la página de detalles de pagos.
        return "redirect:/estudiantes/historialArancel/detallePagos/" + id;
    }


/*
    @GetMapping("/crearDetallePago")
    public String estudianteForm(Model model) {
        // Puedes agregar lógica para prellenar el formulario si es necesario.
        model.addAttribute("detallePago", new DetallePagoEntity());
        return "IngresarDetallePago";
    }

 */
/*
    @PostMapping("/nuevoPagos")
    public String nuevoDetallePago(DetallePagoEntity detallePago) {
        // Puedes agregar validaciones o lógica de manejo de datos aquí.
        detallePagoService.guardarDetallePago(historialArancelId, cuotasPactadas,  montoTotal);
        return "VisualizarDetallesPagos";
    }

 */
/*
    @PostMapping("/DetallePago/{id}/agregarDetalle")
    public String agregarDetalle(@PathVariable Long id, @RequestParam("nuevaNota") double efectivo) {
        // Implement your logic here to add a new grade to the academic history of the student with the provided ID.
        // If you need more details, let me know.
        detallePagoService.anadirDetalle(historialArancelId, cuotasPactadas, montoTotal);
        return "redirect:/estudiantes/historialArancel/" + id;
    }
    */

}
