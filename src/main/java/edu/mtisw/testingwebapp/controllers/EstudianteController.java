package edu.mtisw.testingwebapp.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;
import edu.mtisw.testingwebapp.entities.EstudianteEntity;
import edu.mtisw.testingwebapp.services.EstudianteService;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
public class EstudianteController {

	@Autowired
	private EstudianteService estudianteService;

	@GetMapping("/estudiantes")
	public String listar(Model model) {
		List<EstudianteEntity> estudiantes = estudianteService.obtenerEstudiantes();

		// Check if the estudiantes list is null or empty before calling cuotaUpdate
		if (estudiantes != null && !estudiantes.isEmpty()) {
			estudianteService.cuotaUpdate(estudiantes);
		}

		model.addAttribute("estudiantes", estudiantes);
		return "VisualizarEstudiantes";
	}

	@GetMapping("/nuevoEstudiante")
	public String estudianteForm(Model model) {
		// Puedes agregar lógica para prellenar el formulario si es necesario.
		model.addAttribute("estudiante", new EstudianteEntity());
		return "IngresarEstudiante";
	}

	@PostMapping("/nuevoEstudiante")
	public ModelAndView nuevoEstudiante(
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

		// Guardar la información del estudiante
		EstudianteEntity estudiante = estudianteService.manageGuardar(rut,nombre,apellido,fechaNacimiento,tipoColegio,nombreColegio,AnnoEgreso,tipoPago,cuotasPactadas,notas);


		// Create a ModelAndView object and add the data you want to pass to the view
		ModelAndView modelAndView = new ModelAndView("IngresarEstudiante");

		// Add the ID of the EstudianteEntity to the model
		modelAndView.addObject("estudianteID", estudiante.getId());

		return modelAndView;
	}


	@GetMapping("/estudiantes/{id}")
	public String mostrarestudiante(@PathVariable Long id, Model model) {
		Optional<EstudianteEntity> estudiante = estudianteService.obtenerPorId(id);
		model.addAttribute("estudiante", estudiante.get());
		return "VisualizarEstudiante";
	}

	// 2. Añadir nuevas notas de estudiantes desde una cadena (POST)
	@PostMapping("/subirNotas")
	public String subirNotasDesdeString(@RequestParam("datosExcel") String datosExcel) {
		// Implement your logic here to process 'datosExcel' and add grades to the database.
		estudianteService.agregarNotasAHistorial(datosExcel);
		return "redirect:/estudiantes";
	}


}
