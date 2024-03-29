package edu.mtisw.testingwebapp.services;

import edu.mtisw.testingwebapp.entities.PrestamoEntity;
import edu.mtisw.testingwebapp.entities.ProjectorEntity;
import edu.mtisw.testingwebapp.repositories.PrestamoRepository;
import edu.mtisw.testingwebapp.repositories.ProjectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PrestamoService {
    @Autowired
    PrestamoRepository prestamoRepository;
    @Autowired
    ProjectorRepository projectorRepository;

    public ArrayList<PrestamoEntity> obtenerPrestamos(){
        return (ArrayList<PrestamoEntity>) prestamoRepository.findAll();
    }
    public PrestamoEntity guardarPrestamo(String fechaPrestamo,String horaPrestamo,String utilizacionHoras,String fechaDevolucion,String horaDevolucion,String estadoDanado,String uso, String idProjector,String idProfesor){
        PrestamoEntity reparacion = new PrestamoEntity();
        System.out.println("agregarPrestamo llamado con paráme:");

        reparacion.setFechaPrestamo(fechaPrestamo);
        reparacion.setHoraPrestamo(horaPrestamo);
        reparacion.setUtilizacionHoras(utilizacionHoras);
        reparacion.setFechaDevolucion(fechaDevolucion);
        reparacion.setHoraDevolucion(horaDevolucion);
        reparacion.setEstadoDanado(estadoDanado);
        reparacion.setUso(uso);
        reparacion.setIdProjector(idProjector);
        reparacion.setIdProfesor(idProfesor); 

        System.out.println("reparacion: " + reparacion);


        return prestamoRepository.save(reparacion);
    }

    public void updatePrestamo(PrestamoEntity reparacion, String estado){

        Long projectorId = Long.parseLong(reparacion.getIdProjector());
        ProjectorEntity projector = projectorRepository.findById(projectorId).orElse(null);

        if (projector != null) {
            projector.setEstado(estado);
            projectorRepository.save(projector);
        } //arreglar para que se actualice el estado del reparacion
        prestamoRepository.save(reparacion);
    }

    public void updatePrestamos(List<PrestamoEntity> prestamoEntities){ //obtener el promedio dada que es el mismo id de profesor ver como hacerlo porque solo profesor tiene todos los repos juntos
        //LocalDate fechaActual = LocalDate.now();

        for(int i=0; i!=prestamoEntities.size();i++){
            updatePrestamo(prestamoEntities.get(i), "Devuelto"); //actualiza el estado de los reparaciones a lo que realmente utilizaremos
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

    public void devolver(Long prestamoID) {
        Optional<PrestamoEntity> optionalPrestamo = prestamoRepository.findById(prestamoID);

        if (optionalPrestamo.isPresent() && optionalPrestamo.get().getEstadoDanado().equals("No Devuelto")) {
            PrestamoEntity reparacion = optionalPrestamo.get();
            reparacion.setEstadoDanado("Devuelto");// Establece el estado pagado en true
            prestamoRepository.save(reparacion); // Guarda la entidad actualizada

            // Calcula el nuevo monto total para el historial de arancel
            Long projectorId = Long.parseLong(reparacion.getIdProjector());
            ProjectorEntity projector = projectorRepository.findById(projectorId).orElse(null);

            if (projector != null) {
                projector.setEstado("Disponible");
                //setear lo necesario, ver despues
                projectorRepository.save(projector);
            }
        }
    }

    public List<PrestamoEntity> obtenerPrestamosPorProjectorID(String long1) {
        return (List<PrestamoEntity>) prestamoRepository.findByIdProjector(long1);
    }




}