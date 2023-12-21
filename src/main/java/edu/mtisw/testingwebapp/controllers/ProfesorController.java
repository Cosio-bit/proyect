package edu.mtisw.testingwebapp.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;
import edu.mtisw.testingwebapp.entities.ProfesorEntity;
import edu.mtisw.testingwebapp.services.ProfesorService;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
public class ProfesorController {

	@Autowired
	private ProfesorService profesorService;

	@GetMapping("/profesores")
	public String listar(Model model) {
		List<ProfesorEntity> profesores = profesorService.obtenerProfesores();

		// Check if the profesores list is null or empty before calling updateInfraccion
		if (profesores != null && !profesores.isEmpty()) {
			//profesorService.updateInfraccion(profesores);
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
			@RequestParam("apellido") String apellido){

		// Guardar la información del profesor
		ProfesorEntity profesor = profesorService.guardarProfesor(rut, nombre, apellido);

		// Create a ModelAndView object and add the data you want to pass to the view
		ModelAndView modelAndView = new ModelAndView("IngresarProfesor");

		// Add the ID of the ProfesorEntity to the model
		modelAndView.addObject("profesorID", profesor.getId());

		return modelAndView;
	}


	@GetMapping("/profesores/profesor/{id}")
	public String mostrarprofesor(@PathVariable Long id, Model model) {
		Optional<ProfesorEntity> profesor = profesorService.obtenerPorId(id);
		model.addAttribute("profesor", profesor.get());
		return "VisualizarProfesor";
	}

}
