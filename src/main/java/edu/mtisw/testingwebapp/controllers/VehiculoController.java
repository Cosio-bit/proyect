package edu.mtisw.testingwebapp.controllers;

import edu.mtisw.testingwebapp.entities.ReparacionEntity;
import edu.mtisw.testingwebapp.entities.VehiculoEntity;
import edu.mtisw.testingwebapp.services.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class VehiculoController {
    @Autowired
    private VehiculoService vehiculoService;
    //@Autowired
    //private ProfesorService profesorService;

    @GetMapping("/vehiculos/vehiculo/{id}")
    public String mostrarVehiculo(@PathVariable Long id, Model model) {
        Optional<VehiculoEntity> vehiculo = vehiculoService.obtenerPorId(id);

        if (vehiculo.isPresent()) {
            VehiculoEntity vehiculoEntity = vehiculo.get();

			//se podria agregar atributos para el countdown y mostrarlo live
            // Agregar el monto del reembolso al modelo
            //model.addAttribute("refundAmount", refundAmount);

            // Agregar el vehiculo al modelo
            model.addAttribute("vehiculo");
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
		List<VehiculoEntity> vehiculos = vehiculoService.obtenerVehiculos();
        //List<ProfesorEntity> profesores = profesorService.obtenerProfesores();

		// Check if the profesores list is null or empty before calling cuotaUpdate
		if (vehiculos != null && !vehiculos.isEmpty()) {
			//podria servir para con f5 actualizar la lista de vehiculos
			//vehiculoService.actualizar(vehiculos);
		}

		model.addAttribute("vehiculos", vehiculos);
        //model.addAttribute("profesores", profesores);
		return "VisualizarVehiculoes";
	}


	@PostMapping("/nuevaReparacion")
	public ModelAndView nuevoReparacion(
			@RequestParam("fechaIngreso") LocalDate fechaIngreso,
			@RequestParam("horaIngreso") LocalDate horaIngreso,
			@RequestParam("tipoReparacion") String tipoReparacion,
			@RequestParam("montoTotal") Integer montoTotal,
			@RequestParam("fechaSalidaReparacion") LocalDate fechaSalidaReparacion,
			@RequestParam("horaSalidaReparacion") LocalDate horaSalidaReparacion,
			@RequestParam("fechaSalidaCliente") LocalDate fechaSalidaCliente,
			@RequestParam("horaSalidaCliente") LocalDate horaSalidaCliente,
			@RequestParam("idVehiculo") String idVehiculo
	) {
		// Guardar la información del profesor
		ReparacionEntity reparacion = vehiculoService.nuevaReparacion(fechaIngreso, horaIngreso, tipoReparacion, montoTotal, fechaSalidaReparacion, horaSalidaReparacion, fechaSalidaCliente, horaSalidaCliente, idVehiculo);

		// Create a ModelAndView object and add the data you want to pass to the view
		ModelAndView modelAndView = new ModelAndView("IngresarReparacion");

		// Add the ID of the ProfesorEntity to the model
		modelAndView.addObject("reparacionID", reparacion.getId());

		return modelAndView;
	}
	
}


