package edu.mtisw.testingwebapp.controllers;

import edu.mtisw.testingwebapp.entities.VehiculoEntity;
import edu.mtisw.testingwebapp.services.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping
public class VehiculoController {
    @Autowired
    private VehiculoService vehiculoService;


    @GetMapping("/vehiculos/vehiculo/{id}")
    public String mostrarVehiculo(@PathVariable Long id, Model model) {
        Optional<VehiculoEntity> vehiculo = Optional.ofNullable(vehiculoService.obtenerPorId(id));
        if (vehiculo.isPresent()) {
            VehiculoEntity vehiculoEntity = vehiculo.get();
            model.addAttribute("vehiculo", vehiculoEntity);
        }
        return "VisualizarVehiculo"; // Reemplaza "VisualizarVehiculo" con el nombre de tu vista
    }

    @GetMapping("/vehiculos/vehiculo")
    public String VehiculoForm(Model model) {
		model.addAttribute("vehiculo", new VehiculoEntity());
        return "IngresarVehiculo";
    }

	@PostMapping("/vehiculos/vehiculo")
	public ModelAndView nuevoVehiculo(
			@RequestParam("patente") String patente,
			@RequestParam("marca") String marca,
			@RequestParam("modelo") String modelo,
			@RequestParam("annoFabricacion") String annoFabricacion,
			@RequestParam("tipoVehiculo") String tipoVehiculo,
			@RequestParam("tipoMotor") String tipoMotor,
			@RequestParam("nroAsientos") Integer nroAsientos,
			@RequestParam("kilometraje") Integer kilometraje
	) {
		VehiculoEntity vehiculo = vehiculoService.guardarVehiculo(patente, marca, modelo, tipoVehiculo, annoFabricacion, tipoMotor, nroAsientos, kilometraje);
		ModelAndView modelAndView = new ModelAndView("IngresarVehiculo");
		modelAndView.addObject("vehiculoID", vehiculo.getId());
		return modelAndView;
	}

    
	@GetMapping("/vehiculos")
	public String listar(Model model) {
		List<VehiculoEntity> vehiculos = vehiculoService.obtenerVehiculos();
		model.addAttribute("vehiculos", vehiculos);
		return "VisualizarVehiculos";
	}

/*
	@PostMapping("/reparaciones/reparacion/{idVehiculo}")
	public ModelAndView nuevaReparacion(
			@PathVariable("idVehiculo") String idVehiculo,
			@RequestParam("fechaHoraIngreso") LocalDateTime fechaHoraIngreso,
			@RequestParam("tipoReparacion") String tipoReparacion
	) {
		// Guardar la información del profesor
		ReparacionEntity reparacion = vehiculoService.crearReparacion(fechaHoraIngreso, tipoReparacion, idVehiculo);

		// Create a ModelAndView object and add the data you want to pass to the view
		ModelAndView modelAndView = new ModelAndView("IngresarReparacion");

		// Add the ID of the ProfesorEntity to the model
		modelAndView.addObject("reparacionID", reparacion.getId());

		return modelAndView;
	}

*/
	
}


