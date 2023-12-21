package edu.mtisw.testingwebapp.services;

import edu.mtisw.testingwebapp.entities.ProjectorEntity;
import edu.mtisw.testingwebapp.repositories.ProjectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
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
    public ProjectorEntity guardarProjector(Long projectorID, String nombre, String tipo, String estado){
        ProjectorEntity projector = new ProjectorEntity();
        //OficinaRRHH oficinaRRHH = new OficinaRRHH();
        projector.setId(projectorID);
        projector.setNombre(nombre);
        projector.setTipo(tipo);
        projector.setEstado(estado);
        return projectorRepository.save(projector);
    }

    
    public Optional<ProjectorEntity> anadirPrestamo(Long id, double efectivo) {
        // First, check if the entity with the given id exists
        Optional<ProjectorEntity> projector= projectorRepository.findById(id);
        
        return projector;
    }
    public Optional<ProjectorEntity> obtenerPorId(Long id){
        return projectorRepository.findById(id);
    }


}