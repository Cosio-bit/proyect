package edu.mtisw.testingwebapp.controllers;

import com.google.common.base.Optional;
import edu.mtisw.testingwebapp.entities.ReparacionEntity;
import edu.mtisw.testingwebapp.services.ReparacionService;
import edu.mtisw.testingwebapp.services.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping
public class ReparacionController {

    @Autowired
    private ReparacionService reparacionService;
    @Autowired
    private VehiculoService vehiculoService;

    @GetMapping("/reparaciones")
    public String listar(Model model) {
        List<ReparacionEntity> reparaciones = reparacionService.obtenerReparaciones();
        model.addAttribute("reparaciones", reparaciones);
        return "VisualizarReparaciones";
    }


    @GetMapping("/reparacion/{id}")
    public String mostrarReparacion(@PathVariable Long id, Model model) {
        Optional<ReparacionEntity> reparacion = Optional.fromNullable((reparacionService.findById(id)));
        if (reparacion.isPresent()) {
            ReparacionEntity reparacionEntity = reparacion.get();
            model.addAttribute("reparacion", reparacionEntity);
        }
        return "VisualizarReparacion";
    }

    @GetMapping("/vehiculo/reparaciones/{idVehiculo}")
    public String mostrarReparaciones(@PathVariable("idVehiculo") String idVehiculo, Model model) {
        List<ReparacionEntity> reparaciones = reparacionService.obtenerReparacionesPorIdVehiculo(idVehiculo);
        model.addAttribute("reparaciones", reparaciones);
        return "VisualizarReparaciones";
    }



    @PostMapping("/crearReparacion/{idVehiculo}")
    public ModelAndView guardarReparacion(
            @PathVariable String idVehiculo,
            @RequestParam String tipoReparacion) {
        ReparacionEntity reparacion = reparacionService.guardarReparacion(LocalDateTime.now(), tipoReparacion, idVehiculo);
        //print it out in console to see it
        System.out.println(reparacion);
        ModelAndView modelAndView = new ModelAndView("IngresarReparacion");
        modelAndView.addObject("idVehiculo", idVehiculo);
        return modelAndView;
    }


    @GetMapping("/crearReparacion/{idVehiculo}")
    public String VehiculoForm(Model model, @PathVariable String idVehiculo) {
        model.addAttribute("reparacion", new ReparacionEntity());
        model.addAttribute("idVehiculo", idVehiculo);
        return "IngresarReparacion";
    }




// Update "salida" date and time
        //@PutMapping("/reparaciones/reparacion/{reparacionId}/salida")
        //public ResponseEntity<?> updateSalidaDateAndTime(@PathVariable Long reparacionId, @RequestParam LocalDate salidaDate, @RequestParam LocalDate salidaTime) {
          //  ReparacionEntity reparacion = reparacionService.findById(reparacionId);
            //reparacionService.updateReparacion(reparacion);
            //return ResponseEntity.ok().build();
        //}

        // Update "retiro" date and time
        //@PutMapping("/reparaciones/reparacion/{reparacionId}/retiro")
        //public ResponseEntity<?> updateRetiroDateAndTime(@PathVariable Long reparacionId, @RequestBody LocalDateTime retiroDateTime) {
         //   ReparacionEntity reparacion = reparacionService.findById(reparacionId);
           // reparacionService.updateReparacion(reparacion);
            //return ResponseEntity.ok().build();
        //}

    
}





