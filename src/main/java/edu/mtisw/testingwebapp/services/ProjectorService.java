package edu.mtisw.testingwebapp.services;

import edu.mtisw.testingwebapp.entities.PrestamoEntity;
import edu.mtisw.testingwebapp.entities.ProjectorEntity;
import edu.mtisw.testingwebapp.repositories.ProjectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.Optional;

@Service
public class ProjectorService {
    @Autowired
    ProjectorRepository projectorRepository;
    @Autowired
    PrestamoService prestamoService;

    public ArrayList<ProjectorEntity> obtenerProjectores(){
        return (ArrayList<ProjectorEntity>) projectorRepository.findAll();
    }
    public ProjectorEntity guardarProjector(String nombre, String tipo){
        ProjectorEntity projector = new ProjectorEntity();
        //OficinaRRHH oficinaRRHH = new OficinaRRHH();
        projector.setNombre(nombre);
        projector.setTipo(tipo);
        projector.setEstado("disponible");
        return projectorRepository.save(projector);
    }

    public PrestamoEntity guardarPrestamo(String fechaPrestamo, String fechaEntrega, String fechaDevolucion, String estado, Long projectorID, Long profesorID){
        return prestamoService.guardarPrestamo(fechaPrestamo, fechaEntrega, fechaDevolucion, estado, projectorID, profesorID);
    }

    
    public Optional<ProjectorEntity> anadirPrestamo(Long id, double efectivo) {
        // First, check if the entity with the given id exists
        Optional<ProjectorEntity> projector= projectorRepository.findById(id);
        
        return projector;
    }
    public Optional<ProjectorEntity> obtenerPorId(Long id){
        return projectorRepository.findById(id);
    }

    public Optional<ProjectorEntity> obtenerPorNombre(String nombre){
        return projectorRepository.findByNombre(nombre);
    }

    public Optional<ProjectorEntity> obtenerPorID(Long id){
        return projectorRepository.findById(id);
    }


}