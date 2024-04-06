package edu.mtisw.testingwebapp.services;

import edu.mtisw.testingwebapp.entities.ReparacionEntity;
import edu.mtisw.testingwebapp.entities.VehiculoEntity;
import edu.mtisw.testingwebapp.repositories.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import java.util.ArrayList;

import java.util.Optional;

@Service
public class VehiculoService {
    @Autowired
    VehiculoRepository vehiculoRepository;
    @Autowired
    ReparacionService reparacionService;

    public ArrayList<VehiculoEntity> obtenerVehiculos(){
        return (ArrayList<VehiculoEntity>) vehiculoRepository.findAll();
    }

    public VehiculoEntity guardarVehiculo(String patente,String marca,String modelo,String annoFabricacion, String tipoVehiculo, String tipoMotor,Integer nroAsientos,Integer kilometraje){
        
        VehiculoEntity vehiculo = new VehiculoEntity();
        vehiculo.setPatente(patente);
        vehiculo.setMarca(marca);
        vehiculo.setModelo(modelo);
        vehiculo.setAnnoFabricacion(annoFabricacion);
        vehiculo.setTipoVehiculo(tipoVehiculo);
        vehiculo.setTipoMotor(tipoMotor);
        vehiculo.setNroAsientos(nroAsientos);
        vehiculo.setKilometraje(kilometraje);

        
        return vehiculoRepository.save(vehiculo);
    }


    public ReparacionEntity crearReparacion(LocalDateTime fechaHoraIngreso,
                                              String tipoReparacion,
                                              String vehiculoID){

        return reparacionService.guardarReparacion(fechaHoraIngreso, tipoReparacion, vehiculoID);
    
    }

    
    //public Optional<VehiculoEntity> anadirReparacion(Long id, double efectivo) {
        // First, check if the entity with the given id exist
    //    Optional<VehiculoEntity> vehiculo= vehiculoRepository.findById(id);
    //    
    //    return vehiculo;
    //}


    public VehiculoEntity obtenerPorId(Long id){
        return vehiculoRepository.findById(id).get();
    }

    public Optional<VehiculoEntity> obtenerPorPatente(String patente){
        return vehiculoRepository.findByPatente(patente);
    }


}