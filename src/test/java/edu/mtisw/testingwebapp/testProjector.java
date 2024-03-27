package edu.mtisw.testingwebapp;

import edu.mtisw.testingwebapp.entities.ProfesorEntity;
import edu.mtisw.testingwebapp.entities.ProjectorEntity;
import edu.mtisw.testingwebapp.repositories.ProfesorRepository;
import edu.mtisw.testingwebapp.repositories.ProjectorRepository;
import edu.mtisw.testingwebapp.services.ProfesorService;
import edu.mtisw.testingwebapp.services.ProjectorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class testProjector {

    @Autowired
    private ProjectorRepository projectorRepository;
    @Autowired
    private ProjectorService projectorService;

    @Autowired
    private ProfesorRepository profesorRepository;
    @Autowired
    private ProfesorService profesorService;

    @Test
    @Transactional
    void testProjectorEntityAttributes() {

        String nombre = "Proyector 1";
        String tipo = "Proyector de datos";

        ProjectorEntity projectEnt = new ProjectorEntity(null, nombre, tipo,"disponible");

        ProjectorEntity projectServ = projectorService.guardarProjector(nombre, tipo);

        ProjectorEntity projectRep = projectorRepository.findByNombre(nombre);

        assertEquals(nombre, projectServ.getNombre());
        assertEquals(tipo, projectServ.getTipo());
        //assertEquals("disponible", projector.getEstado());
        assertEquals("Proyector 1", projectRep.getNombre());
    }




}
