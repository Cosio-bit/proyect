package edu.mtisw.testingwebapp.controllers;
import edu.mtisw.testingwebapp.entities.ProfesorEntity;
import edu.mtisw.testingwebapp.entities.VehiculoEntity;
import edu.mtisw.testingwebapp.services.ProfesorService;
import edu.mtisw.testingwebapp.services.VehiculoService;
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
public class VehiculoController {
    @Autowired
    private VehiculoService vehiculoService;
    @Autowired
    private ProfesorService profesorService;


    @GetMapping("/vehiculos/vehiculo/{id}")
    public String mostrarVehiculo(@PathVariable Long id, Model model) {
        Optional<VehiculoEntity> vehiculo = vehiculoService.obtenerPorId(id);

        if (vehiculo.isPresent()) {
            VehiculoEntity vehiculoEntity = vehiculo.get();

			//se podria agregar atributos para el countdown y mostrarlo live
            // Agregar el monto del reembolso al modelo
            //model.addAttribute("refundAmount", refundAmount);

            // Agregar el vehiculo al modelo
            model.addAttribute("vehiculo", vehiculoEntity);
        }

        return "VisualizarVehiculo"; // Reemplaza "VisualizarVehiculo" con el nombre de tu vista
    }


    @GetMapping("/nuevoVehiculo")
    public String VehiculoForm(Model model) {
        // Puedes agregar lógica para prellenar el formulario si es necesario.
        model.addAttribute("vehiculo", new VehiculoEntity());
        return "IngresarVehiculo";
    }


    
	@GetMapping("/vehiculos")
	public String listar(Model model) {
		List<VehiculoEntity> vehiculos = vehiculoService.obtenerVehiculoes();
        List<ProfesorEntity> profesores = profesorService.obtenerProfesores();

		// Check if the profesores list is null or empty before calling cuotaUpdate
		if (vehiculos != null && !vehiculos.isEmpty()) {
			//podria servir para con f5 actualizar la lista de vehiculos
			//vehiculoService.actualizar(vehiculos);
		}

		model.addAttribute("vehiculos", vehiculos);
        model.addAttribute("profesores", profesores);
		return "VisualizarVehiculoes";
	}


	@PostMapping("/nuevoProjector")
	public ModelAndView nuevoProjector(
			@RequestBody

			@RequestParam("nombre") String nombre,
			@RequestParam("tipo") String tipo){

		// Guardar la información del profesor
		VehiculoEntity vehiculo = vehiculoService.guardarVehiculo(nombre,tipo);

		// Create a ModelAndView object and add the data you want to pass to the view
		ModelAndView modelAndView = new ModelAndView("IngresarProjector");

		// Add the ID of the ProfesorEntity to the model
		modelAndView.addObject("vehiculoID", vehiculo.getId());

		return modelAndView;
	}





}


