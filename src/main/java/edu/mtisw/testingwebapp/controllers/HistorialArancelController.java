package edu.mtisw.testingwebapp.controllers;
import edu.mtisw.testingwebapp.entities.HistorialArancelEntity;
import edu.mtisw.testingwebapp.services.HistorialArancelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/estudiantes")
public class HistorialArancelController {
    @Autowired
    private HistorialArancelService historialArancelService;

    // 1. Obtener todas las notas de todos los estudiantes (GET)
    @GetMapping("/listarAranceles")
    public String listarAranceles(Model model) {
        List<HistorialArancelEntity> historialAranceles = historialArancelService.obtenerHistorialArancels();
        model.addAttribute("historialAranceles", historialAranceles);
        return "VisualizarHistorialesAranceles";
    }

    // 3. Obtener las notas de un estudiante en particular (GET)
    @GetMapping("/historialArancel/{id}")
    public String mostrarHistorialArancel(@PathVariable Long id, Model model) {
        Optional<HistorialArancelEntity> historialArancel = historialArancelService.obtenerPorEstudianteId(id);
        model.addAttribute("historialArancel", historialArancel.get());
        return "VisualizarHistorialArancel";
    }

    // 4. Agregar una nueva nota a un estudiante (POST)
    @PostMapping("/historialAcademico/{id}/agregarPago")
    public String agregarPago(@PathVariable Long id, @RequestParam("nuevaNota") double efectivo) {
        // Implement your logic here to add a new grade to the academic history of the student with the provided ID.
        // If you need more details, let me know.
        historialArancelService.anadirPago(id, efectivo);
        return "redirect:/estudiantes/historialArancel/" + id;
    }

    @GetMapping("/crearHistorialArancel")
    public String estudianteForm(Model model) {
        // Puedes agregar lógica para prellenar el formulario si es necesario.
        model.addAttribute("estudiante", new HistorialArancelEntity());
        return "IngresarHistorialArancel";
    }
/*
    @PostMapping("/crearHistorialArancel")
    public ModelAndView nuevoHistorialArancel(
            @RequestParam("notas") String notas,
            @RequestParam("tipoColegio") String tipoColegio,
            @RequestParam("AnnoEgreso") String AnnoEgreso,
            @RequestParam("tipoPago") String tipoPago,
            @RequestParam("cuotasPactadas") String cuotasPactadas) {
        // Implementa tu lógica aquí para guardar el historial de aranceles.
        HistorialArancelEntity historialArancel = historialArancelService.guardarHistorialArancel(notas, tipoColegio, AnnoEgreso, tipoPago, cuotasPactadas);

        // Crea un objeto ModelAndView y agrega los datos que deseas pasar a la vista
        ModelAndView modelAndView = new ModelAndView("IngresarHistorialArancel");

        // Agrega el ID del HistorialArancelEntity al modelo
        modelAndView.addObject("historialArancel", historialArancel);

        return modelAndView;
    }*/

}
