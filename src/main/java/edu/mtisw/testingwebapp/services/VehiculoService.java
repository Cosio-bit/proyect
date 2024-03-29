package edu.mtisw.testingwebapp.services;

import edu.mtisw.testingwebapp.entities.ReparacionEntity;
import edu.mtisw.testingwebapp.entities.VehiculoEntity;
import edu.mtisw.testingwebapp.repositories.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import java.util.Optional;

@Service
public class VehiculoService {
    @Autowired
    VehiculoRepository vehiculoRepository;
    @Autowired
    ReparacionService reparacionService;

    public ArrayList<VehiculoEntity> obtenerVehiculoes(){
        return (ArrayList<VehiculoEntity>) vehiculoRepository.findAll();
    }
    public VehiculoEntity guardarVehiculo(String nombre, String tipo){
        VehiculoEntity vehiculo = new VehiculoEntity();
        //OficinaRRHH oficinaRRHH = new OficinaRRHH();
        vehiculo.setNombre(nombre);
        vehiculo.setTipo(tipo);
        vehiculo.setEstado("disponible");
        return vehiculoRepository.save(vehiculo);
    }

    public ReparacionEntity guardarReparacion(String fechaReparacion,String horaReparacion,String utilizacionHoras,String fechaDevolucion,String horaDevolucion,String estadoDanado,String uso, String idVehiculo,String idProfesor){
        return reparacionService.guardarReparacion(fechaReparacion, horaReparacion, utilizacionHoras, fechaDevolucion, horaDevolucion, estadoDanado, uso, idVehiculo, idProfesor);
    }

    
    public Optional<VehiculoEntity> anadirReparacion(Long id, double efectivo) {
        // First, check if the entity with the given id exists
        Optional<VehiculoEntity> vehiculo= vehiculoRepository.findById(id);
        
        return vehiculo;
    }
    public Optional<VehiculoEntity> obtenerPorId(Long id){
        return vehiculoRepository.findById(id);
    }

    public Optional<VehiculoEntity> obtenerPorNombre(String nombre){
        return Optional.ofNullable(vehiculoRepository.findByNombre(nombre));
    }

    public Optional<VehiculoEntity> obtenerPorID(Long id){
        return vehiculoRepository.findById(id);
    }


}