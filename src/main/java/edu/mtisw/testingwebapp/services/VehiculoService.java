package edu.mtisw.testingwebapp.services;

import edu.mtisw.testingwebapp.entities.ReparacionEntity;
import edu.mtisw.testingwebapp.entities.VehiculoEntity;
import edu.mtisw.testingwebapp.repositories.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public VehiculoEntity guardarVehiculo(String patente,String marca,String modelo,String annoFabricacion,String tipoMotor,Integer nroAsientos,float kilometraje, String estado){
        
        VehiculoEntity vehiculo = new VehiculoEntity();
        vehiculo.setPatente(patente);
        vehiculo.setMarca(marca);
        vehiculo.setModelo(modelo);
        vehiculo.setAnnoFabricacion(annoFabricacion);
        vehiculo.setTipoMotor(tipoMotor);
        vehiculo.setNroAsientos(nroAsientos);
        vehiculo.setKilometraje(kilometraje);
        vehiculo.setEstado(estado);
        
        return vehiculoRepository.save(vehiculo);

    }



    public ReparacionEntity nuevaReparacion(LocalDate fechaIngreso,
                                              LocalDate horaIngreso,
                                              String tipoReparacion,
                                              Integer montoTotal,
                                              LocalDate fechaSalidaReparacion,
                                              LocalDate horaSalidaReparacion,
                                              LocalDate fechaSalidaCliente,
                                              LocalDate horaSalidaCliente,
                                              String idVehiculo){

        return reparacionService.guardarReparacion(fechaIngreso,horaIngreso,tipoReparacion,montoTotal,fechaSalidaReparacion,horaSalidaReparacion,fechaSalidaCliente,horaSalidaCliente,idVehiculo);
    
    }

    
    public Optional<VehiculoEntity> anadirReparacion(Long id, double efectivo) {
        // First, check if the entity with the given id exist
        Optional<VehiculoEntity> vehiculo= vehiculoRepository.findById(id);
        
        return vehiculo;
    }


    public Optional<VehiculoEntity> obtenerPorId(Long id){
        return vehiculoRepository.findById(id);
    }

    public Optional<VehiculoEntity> obtenerPorPatente(String patente){
        return vehiculoRepository.findByPatente(patente);
    }



}