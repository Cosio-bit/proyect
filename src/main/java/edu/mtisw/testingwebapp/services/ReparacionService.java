package edu.mtisw.testingwebapp.services;

import edu.mtisw.testingwebapp.entities.ReparacionEntity;
import edu.mtisw.testingwebapp.repositories.ReparacionRepository;
import edu.mtisw.testingwebapp.repositories.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class ReparacionService {
    @Autowired
    ReparacionRepository reparacionRepository;
    @Autowired
    VehiculoRepository vehiculoRepository;

    public ArrayList<ReparacionEntity> obtenerReparaciones(){
        return (ArrayList<ReparacionEntity>) reparacionRepository.findAll();
    }

    public ArrayList<ReparacionEntity> obtenerReparacionesPorIdVehiculo(String idVehiculo) {
        return (ArrayList<ReparacionEntity>)  reparacionRepository.findByVehiculoID(idVehiculo);
    }

    public ReparacionEntity guardarReparacion(LocalDateTime fechaHoraIngreso,
                                              String tipoReparacion,
                                              String idVehiculo){

        ReparacionEntity reparacion = new ReparacionEntity();

        reparacion.setFechaHoraIngreso(fechaHoraIngreso);
        reparacion.setTipoReparacion(tipoReparacion);
        reparacion.setIdVehiculo(idVehiculo);

        //print in console if the reparacion is saved
        System.out.println("Reparacion saved: " + reparacion);

        return reparacionRepository.save(reparacion);
    }

    public void updateReparacion(ReparacionEntity reparacion){

        //Long vehiculoId = Long.parseLong(reparacion.getVehiculo());
        //VehiculoEntity vehiculo = vehiculoRepository.findById(vehiculoId).orElse(null);

        if (reparacion.getIdVehiculo() != null) {
            if (reparacion.getFechaHoraSalida() == null) {
                reparacion.setFechaHoraSalida(LocalDateTime.now());

            }
            else if (reparacion.getFechaHoraRetiro()==null){
                reparacion.setFechaHoraRetiro(LocalDateTime.now());

        } //arreglar para que se actualice el estado del reparacion
        reparacionRepository.save(reparacion);
        }
    }

    //    public void updateReparaciones(List<ReparacionEntity> reparacionEntities){ //obtener el promedio dada que es el mismo id de profesor ver como hacerlo porque solo profesor tiene todos los repos juntos
//
//        for(int i=0; i!=reparacionEntities.size();i++){
//            updateReparacion(reparacionEntities.get(i), "Devuelto"); //actualiza el estado de los reparaciones a lo que realmente utilizaremos
//        
//       }
//    }

    public ReparacionEntity findById(Long reparacionId) {
        Optional<ReparacionEntity> optionalReparacion = reparacionRepository.findById(reparacionId);
        return optionalReparacion.orElse(null);
    }

    public int calcularAtrazo(LocalDate fechaActual, LocalDate fechaVencimiento) {
        // Calcula el período entre la fecha del último pago y la fecha actual
        Period periodo = Period.between(fechaVencimiento, fechaActual);

        // Calcula el total de meses de atraso
        int aniosDiferencia = periodo.getYears();
        int mesesDiferencia = periodo.getMonths();

        return aniosDiferencia * 12 + mesesDiferencia;
    }

    //public void devolver(Long reparacionID) {
    //    Optional<ReparacionEntity> optionalReparacion = reparacionRepository.findById(reparacionID);

    //    if (optionalReparacion.isPresent()){ //&& optionalReparacion.get().getEstado().equals("No Devuelto")) {
    //      ReparacionEntity reparacion = optionalReparacion.get();
            //reparacion.setEstadoDanado("Devuelto");// Establece el estado pagado en true
    //    reparacionRepository.save(reparacion); // Guarda la entidad actualizada

            // Calcula el nuevo monto total para el historial de arancel
    //        Long vehiculoId = Long.parseLong(reparacion.getIdVehiculo());
    //        VehiculoEntity vehiculo = vehiculoRepository.findById(vehiculoId).orElse(null);

    //        if (vehiculo != null) {
    //            vehiculo.setEstado("Disponible");
                //setear lo necesario, ver despues
    //            vehiculoRepository.save(vehiculo);
    //        }
    //    }
    //}
}