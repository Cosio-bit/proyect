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
/*
	// 2. Añadir nuevas notas de profesores desde una cadena (POST)
	@PostMapping("/subirNotas")
	public String subirNotasDesdeString(@RequestParam("datosExcel") String datosExcel) {
		// Implement your logic here to process 'datosExcel' and add grades to the database.
		profesorService.agregarNotasAHistorial(datosExcel);
		return "redirect:/profesores";
	}*/


}
