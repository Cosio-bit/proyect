package edu.mtisw.testingwebapp.services;

import edu.mtisw.testingwebapp.entities.ReparacionEntity;
import edu.mtisw.testingwebapp.entities.VehiculoEntity;
import edu.mtisw.testingwebapp.repositories.ReparacionRepository;
import edu.mtisw.testingwebapp.repositories.VehiculoRepository;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class ReparacionService {
    @Autowired
    ReparacionRepository reparacionRepository;
    @Autowired
    VehiculoRepository vehiculoRepository;

    public ArrayList<ReparacionEntity> obtenerReparacions(){
        return (ArrayList<ReparacionEntity>) reparacionRepository.findAll();
    }
    public ReparacionEntity guardarReparacion(LocalDate fechaIngreso,
                                              LocalDate horaIngreso,
                                              String tipoReparacion,
                                              Integer montoTotal,
                                              LocalDate fechaSalidaReparacion,
                                              LocalDate horaSalidaReparacion,
                                              LocalDate fechaSalidaCliente,
                                              LocalDate horaSalidaCliente,
                                              String idVehiculo){

        ReparacionEntity reparacion = new ReparacionEntity();

        reparacion.setFechaIngreso(fechaIngreso.toString());
        reparacion.setHoraIngeso(horaIngreso.toString());
        reparacion.setTipoReparacion(tipoReparacion);
        reparacion.setMontoTotal(montoTotal);
        reparacion.setFechaSalidaReparacion(fechaSalidaReparacion.toString());
        reparacion.setHoraSalidaReparacion(horaSalidaReparacion.toString());
        reparacion.setFechaSalidaCliente(fechaSalidaCliente.toString());
        reparacion.setHoraSalidaCliente(horaSalidaCliente.toString());
        reparacion.setIdVehiculo(idVehiculo);

        System.out.println("agregarReparacion llamado con paráme:");
        System.out.println("fechaIngreso: " + fechaIngreso);
        System.out.println("agregarReparacion llamado con paráme:");
        System.out.println("agregarReparacion llamado con paráme:");
        System.out.println("agregarReparacion llamado con paráme:");



        return reparacionRepository.save(reparacion);
    }
/*
    public void updatePrestamo(ReparacionEntity reparacion, String estado){

        Long vehiculoId = Long.parseLong(reparacion.getIdVehiculo());
        VehiculoEntity vehiculo = vehiculoRepository.findById(vehiculoId).orElse(null);

        if (vehiculo != null) {
            vehiculo.setEstado(estado);
            vehiculoRepository.save(vehiculo);
        } //arreglar para que se actualice el estado del reparacion
        reparacionRepository.save(reparacion);
    }

    public void updateReparaciones(List<ReparacionEntity> reparacionEntities){ //obtener el promedio dada que es el mismo id de profesor ver como hacerlo porque solo profesor tiene todos los repos juntos
        //LocalDate fechaActual = LocalDate.now();

        for(int i=0; i!=reparacionEntities.size();i++){
            updatePrestamo(reparacionEntities.get(i), "Devuelto"); //actualiza el estado de los reparaciones a lo que realmente utilizaremos
        }
    }


    public int calcularAtrazo(LocalDate pagoActual, LocalDate fechaVencimiento) {
        // Calcula el período entre la fecha del último pago y la fecha actual
        Period periodo = Period.between(fechaVencimiento, pagoActual);

        // Calcula el total de meses de atraso
        int aniosDiferencia = periodo.getYears();
        int mesesDiferencia = periodo.getMonths();

        return aniosDiferencia * 12 + mesesDiferencia;
    }

    public void devolver(Long reparacionID) {
        Optional<ReparacionEntity> optionalPrestamo = reparacionRepository.findById(reparacionID);

        if (optionalPrestamo.isPresent() && optionalPrestamo.get().getEstadoDanado().equals("No Devuelto")) {
            ReparacionEntity reparacion = optionalPrestamo.get();
            reparacion.setEstadoDanado("Devuelto");// Establece el estado pagado en true
            reparacionRepository.save(reparacion); // Guarda la entidad actualizada

            // Calcula el nuevo monto total para el historial de arancel
            Long vehiculoId = Long.parseLong(reparacion.getIdVehiculo());
            VehiculoEntity vehiculo = vehiculoRepository.findById(vehiculoId).orElse(null);

            if (vehiculo != null) {
                vehiculo.setEstado("Disponible");
                //setear lo necesario, ver despues
                vehiculoRepository.save(vehiculo);
            }
        }
    }

    public List<ReparacionEntity> obtenerReparacionesPorVehiculoID(String long1) {
        return (List<ReparacionEntity>) reparacionRepository.findByIdVehiculo(long1);
    }

*/


}