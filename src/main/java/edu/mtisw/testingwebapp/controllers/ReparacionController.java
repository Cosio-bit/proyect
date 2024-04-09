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

    @GetMapping("/reparaciones/{id}")
    public String mostrarReparaciones(@PathVariable("id") String id, Model model) {
        List<ReparacionEntity> reparaciones = reparacionService.obtenerReparacionesPorIdVehiculo(id);
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


    @GetMapping("/reparacion/{reparacionId}/salida")
    public String updateSalidaDateAndTime(@PathVariable Long reparacionId, Model model) {
        ReparacionEntity reparacion = reparacionService.findById(reparacionId);
        reparacionService.updateReparacion(reparacion);
        List<ReparacionEntity> reparaciones = reparacionService.obtenerReparaciones();
        model.addAttribute("reparaciones", reparaciones);
        return "VisualizarReparaciones";
    }

    @GetMapping("/reparacion/{reparacionId}/retiro")
    public String updateRetiroDateAndTime(@PathVariable Long reparacionId, Model model) {
        ReparacionEntity reparacion = reparacionService.findById(reparacionId);
        reparacionService.updateReparacion(reparacion);
        List<ReparacionEntity> reparaciones = reparacionService.obtenerReparaciones();
        model.addAttribute("reparaciones", reparaciones);
        return "VisualizarReparaciones";
    }


}





