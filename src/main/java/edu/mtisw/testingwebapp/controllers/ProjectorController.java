package edu.mtisw.testingwebapp.controllers;
import edu.mtisw.testingwebapp.entities.ProfesorEntity;
import edu.mtisw.testingwebapp.entities.ProfesorEntity;
import edu.mtisw.testingwebapp.entities.ProjectorEntity;
import edu.mtisw.testingwebapp.services.ProfesorService;
import edu.mtisw.testingwebapp.services.ProjectorService;
//import edu.mtisw.testingwebapp.services.OficinaRRHH;
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
    private ProjectorService projectorService;

    // 1. Obtener todas las notas de todos los profesores (GET)
    @GetMapping("/listarProjectores")
    public String listarProjectores(Model model) {
        List<ProjectorEntity> projectores = projectorService.obtenerProjectores();
        model.addAttribute("projectores", projectores);
        return "VisualizarProjectores";
    }

    @GetMapping("/projector/{id}")
    public String mostrarProjector(@PathVariable Long id, Model model) {
        Optional<ProjectorEntity> projector = projectorService.obtenerPorId(id);

        if (projector.isPresent()) {
            ProjectorEntity projectorEntity = projector.get();

			//se podria agregar atributos para el countdown y mostrarlo live
            // Agregar el monto del reembolso al modelo
            //model.addAttribute("refundAmount", refundAmount);

            // Agregar el projector al modelo
            model.addAttribute("projector", projectorEntity);
        }

        return "VisualizarProjector"; // Reemplaza "VisualizarProjector" con el nombre de tu vista
    }


    @GetMapping("/crearProjector")
    public String ProjectorForm(Model model) {
        // Puedes agregar lógica para prellenar el formulario si es necesario.
        model.addAttribute("projector", new ProjectorEntity());
        return "IngresarProjector";
    }


    
	@GetMapping("/projectores")
	public String listar(Model model) {
		List<ProjectorEntity> projectores = projectorService.obtenerProjectores();

		// Check if the profesores list is null or empty before calling cuotaUpdate
		if (projectores != null && !projectores.isEmpty()) {
			//podria servir para con f5 actualizar la lista de projectores
			//projectorService.actualizar(projectores);
		}

		model.addAttribute("projectores", projectores);
		return "VisualizarProjectores";
	}


	@PostMapping("/nuevoProjector")
	public ModelAndView nuevoProjector(
			@RequestBody

			@RequestParam("nombre") String nombre,
			@RequestParam("tipo") String tipo,
			@RequestParam("estado") String estado){

		// Guardar la información del profesor
		ProjectorEntity projector = projectorService.guardarProjector(nombre,tipo,estado);

		// Create a ModelAndView object and add the data you want to pass to the view
		ModelAndView modelAndView = new ModelAndView("IngresarProjector");

		// Add the ID of the ProfesorEntity to the model
		modelAndView.addObject("projectorID", projector.getId());

		return modelAndView;
	}





}


