package edu.mtisw.testingwebapp.controllers;
import edu.mtisw.testingwebapp.entities.ProfesorEntity;
import edu.mtisw.testingwebapp.entities.HistorialAcademicoEntity;
import edu.mtisw.testingwebapp.entities.ProfesorEntity;
import edu.mtisw.testingwebapp.entities.ProjectorEntity;
import edu.mtisw.testingwebapp.services.ProfesorService;
import edu.mtisw.testingwebapp.services.HistorialAcademicoService;
import edu.mtisw.testingwebapp.services.ProjectorService;
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
public class ProjectorController {
    @Autowired
    private ProjectorService ProjectorService;
    @Autowired
    private HistorialAcademicoService historialAcademicoService;

    // 1. Obtener todas las notas de todos los profesores (GET)
    @GetMapping("/listarProjectores")
    public String listarAranceles(Model model) {
        List<ProjectorEntity> projectores = ProjectorService.obtenerProjectores();
        model.addAttribute("projectores", projectores);
        return "VisualizarProjectores";
    }

    @GetMapping("/projector/{id}")
    public String mostrarProjector(@PathVariable Long id, Model model) {
        Optional<ProjectorEntity> projector = projectorService.obtenerPorProfesorId(id);

        if (projector.isPresent()) {
            ProjectorEntity projectorEntity = projector.get();

            // Obtener el promedio y el arancel del historial
            Long idProfesor = projectorEntity.getProfesorID();
            HistorialAcademicoEntity historialAcademico = historialAcademicoService.obtenerPorProfesorId(idProfesor);
            double promedio = historialAcademico.getPromedioExamenes(); // Reemplaza con el campo correcto
            double arancel = projectorEntity.getMontoTotal(); // Reemplaza con el campo correcto

            // Calcular el monto del reembolso
            double refundAmount = arancel - OficinaRRHH.calcularArancelNotas(promedio, arancel);

            // Agregar el monto del reembolso al modelo
            model.addAttribute("refundAmount", refundAmount);

            // Agregar el projector al modelo
            model.addAttribute("projector", projectorEntity);
        }

        return "VisualizarProjector"; // Reemplaza "VisualizarProjector" con el nombre de tu vista
    }


    // 4. Agregar una nueva nota a un profesor (POST)
    @PostMapping("/historialAcademico/{id}/agregarPago")
    public String agregarPago(@PathVariable Long id, @RequestParam("nuevaNota") double efectivo) {
        // Implement your logic here to add a new grade to the academic history of the student with the provided ID.
        // If you need more details, let me know.
        projectorService.anadirPago(id, efectivo);
        return "redirect:/profesores/projector/" + id;
    }

    @GetMapping("/crearProjector")
    public String profesorForm(Model model) {
        // Puedes agregar lógica para prellenar el formulario si es necesario.
        model.addAttribute("profesor", new ProjectorEntity());
        return "IngresarProjector";
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

	@PostMapping("/nuevoProjector")
	public ModelAndView nuevoProjector(
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

		// Add the ID of the ProfesorEntity to the model
		modelAndView.addObject("profesorID", profesor.getId());

		return modelAndView;
	}


	@GetMapping("/profesores/{id}")
	public String mostrarprofesor(@PathVariable Long id, Model model) {
		Optional<ProfesorEntity> profesor = profesorService.obtenerPorId(id);
		model.addAttribute("profesor", profesor.get());
		return "VisualizarProfesor";
	}



}


