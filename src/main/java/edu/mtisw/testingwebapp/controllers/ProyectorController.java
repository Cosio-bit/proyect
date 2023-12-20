package edu.mtisw.testingwebapp.controllers;
import edu.mtisw.testingwebapp.entities.EstudianteEntity;
import edu.mtisw.testingwebapp.entities.HistorialAcademicoEntity;
import edu.mtisw.testingwebapp.entities.ProfesorEntity;
import edu.mtisw.testingwebapp.entities.ProyectorEntity;
import edu.mtisw.testingwebapp.services.ProfesorService;
import edu.mtisw.testingwebapp.services.HistorialAcademicoService;
import edu.mtisw.testingwebapp.services.ProyectorService;
import edu.mtisw.testingwebapp.services.OficinaRRHH;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class ProyectorController {
    @Autowired
    private ProyectorService ProyectorService;
    @Autowired
    private HistorialAcademicoService historialAcademicoService;

    // 1. Obtener todas las notas de todos los profesores (GET)
    @GetMapping("/listarProyectores")
    public String listarAranceles(Model model) {
        List<ProyectorEntity> proyectores = ProyectorService.obtenerProyectores();
        model.addAttribute("proyectores", proyectores);
        return "VisualizarProyectores";
    }

    @GetMapping("/proyector/{id}")
    public String mostrarProyector(@PathVariable Long id, Model model) {
        Optional<ProyectorEntity> proyector = proyectorService.obtenerPorEstudianteId(id);

        if (historialArancel.isPresent()) {
            ProyectorEntity historialArancelEntity = historialArancel.get();

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


    // 4. Agregar una nueva nota a un profesor (POST)
    @PostMapping("/historialAcademico/{id}/agregarPago")
    public String agregarPago(@PathVariable Long id, @RequestParam("nuevaNota") double efectivo) {
        // Implement your logic here to add a new grade to the academic history of the student with the provided ID.
        // If you need more details, let me know.
        historialArancelService.anadirPago(id, efectivo);
        return "redirect:/profesores/historialArancel/" + id;
    }

    @GetMapping("/crearHistorialArancel")
    public String estudianteForm(Model model) {
        // Puedes agregar lógica para prellenar el formulario si es necesario.
        model.addAttribute("profesor", new ProyectorEntity());
        return "IngresarHistorialArancel";
    }


    
	@GetMapping("/profesores")
	public String listar(Model model) {
		List<ProfesorEntity> profesores = profesorService.obtenerProfesores();

		// Check if the profesores list is null or empty before calling cuotaUpdate
		if (profesores != null && !profesores.isEmpty()) {
			profesorService.cuotaUpdate(profesores);
		}

		model.addAttribute("profesores", profesores);
		return "VisualizarProfesores";
	}

	@GetMapping("/nuevoProfesor")
	public String profesorForm(Model model) {
		// Puedes agregar lógica para prellenar el formulario si es necesario.
		model.addAttribute("profesor", new ProfesorEntity());
		return "IngresarProfesor";
	}

	@PostMapping("/nuevoProfesor")
	public ModelAndView nuevoProfesor(
			@RequestBody

			@RequestParam("rut") String rut,
			@RequestParam("nombre") String nombre,
			@RequestParam("apellido") String apellido,
			@RequestParam("fechaNacimiento") String fechaNacimiento,
			@RequestParam("tipoColegio") String tipoColegio,
			@RequestParam("nombreColegio") String nombreColegio,
			@RequestParam("AnnoEgreso") String AnnoEgreso,
			@RequestParam("tipoPago") String tipoPago,
			@RequestParam("cuotasPactadas") String cuotasPactadas,
			@RequestParam("notas") String notas){

		// Guardar la información del profesor
		ProfesorEntity profesor = profesorService.manageGuardar(rut,nombre,apellido,fechaNacimiento,tipoColegio,nombreColegio,AnnoEgreso,tipoPago,cuotasPactadas,notas);


		// Create a ModelAndView object and add the data you want to pass to the view
		ModelAndView modelAndView = new ModelAndView("IngresarProfesor");

		// Add the ID of the EstudianteEntity to the model
		modelAndView.addObject("profesorID", profesor.getId());

		return modelAndView;
	}


	@GetMapping("/profesores/{id}")
	public String mostrarprofesor(@PathVariable Long id, Model model) {
		Optional<ProfesorEntity> profesor = profesorService.obtenerPorId(id);
		model.addAttribute("profesor", profesor.get());
		return "VisualizarProfesor";
	}
/*
    // New request mapping to calculate the refund
    @GetMapping("/profesores/historialArancel/calcularRefund/{id}")
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


