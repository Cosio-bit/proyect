package edu.mtisw.testingwebapp.controllers;
import edu.mtisw.testingwebapp.entities.EstudianteEntity;
import edu.mtisw.testingwebapp.entities.HistorialAcademicoEntity;
import edu.mtisw.testingwebapp.entities.HistorialArancelEntity;
import edu.mtisw.testingwebapp.services.EstudianteService;
import edu.mtisw.testingwebapp.services.HistorialAcademicoService;
import edu.mtisw.testingwebapp.services.HistorialArancelService;
import edu.mtisw.testingwebapp.services.OficinaRRHH;
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
    @Autowired
    private HistorialAcademicoService historialAcademicoService;

    // 1. Obtener todas las notas de todos los estudiantes (GET)
    @GetMapping("/listarAranceles")
    public String listarAranceles(Model model) {
        List<HistorialArancelEntity> historialAranceles = historialArancelService.obtenerHistorialArancels();
        model.addAttribute("historialAranceles", historialAranceles);
        return "VisualizarHistorialesAranceles";
    }

    @GetMapping("/historialArancel/{id}")
    public String mostrarHistorialArancel(@PathVariable Long id, Model model) {
        Optional<HistorialArancelEntity> historialArancel = historialArancelService.obtenerPorEstudianteId(id);

        if (historialArancel.isPresent()) {
            HistorialArancelEntity historialArancelEntity = historialArancel.get();

            // Obtener el promedio y el arancel del historial
            Long idEstudiante = historialArancelEntity.getEstudianteID();
            HistorialAcademicoEntity historialAcademico = historialAcademicoService.obtenerPorEstudianteId(idEstudiante);
            double promedio = historialAcademico.getPromedioExamenes(); // Reemplaza con el campo correcto
            double arancel = historialArancelEntity.getMontoTotal(); // Reemplaza con el campo correcto

            // Calcular el monto del reembolso
            double refundAmount = arancel - OficinaRRHH.calcularArancelNotas(promedio, arancel);

            // Agregar el monto del reembolso al modelo
            model.addAttribute("refundAmount", refundAmount);

            // Agregar el historialArancel al modelo
            model.addAttribute("historialArancel", historialArancelEntity);
        }

        return "VisualizarHistorialArancel"; // Reemplaza "VisualizarHistorialArancel" con el nombre de tu vista
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
    // New request mapping to calculate the refund
    @GetMapping("/estudiantes/historialArancel/calcularRefund/{id}")
    public String calcularRefund(@PathVariable Long id, Model model) {
        // Fetch the student's information, such as promedio and arancel, based on the student's ID.
        Optional<HistorialArancelEntity> historialArancel = historialArancelService.obtenerPorId(id);
        Long idEstudiante = historialArancel.get().getEstudianteID();
        HistorialAcademicoEntity historialAcademico = historialAcademicoService.obtenerPorEstudianteId(idEstudiante);

        // Get the promedio and arancel values from the student's data.
        double promedio = historialAcademico.getPromedioExamenes(); // Replace with the actual field name
        double arancel = historialArancel.get().getMontoTotal(); // Replace with the actual field name

        // Calculate the refund amount using your function
        double refundAmount = OficinaRRHH.calcularArancelNotas(promedio, arancel);

        // Agregar el valor del monto del reembolso al modelo
        model.addAttribute("refundAmount", refundAmount);

        return "VisualizarHistorialArancel";
    }*/


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


