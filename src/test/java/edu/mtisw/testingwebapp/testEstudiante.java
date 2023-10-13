package edu.mtisw.testingwebapp;

import edu.mtisw.testingwebapp.entities.EstudianteEntity;
import edu.mtisw.testingwebapp.repositories.EstudianteRepository;
import edu.mtisw.testingwebapp.services.EstudianteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class testEstudiante {

    EstudianteEntity estudiante = new EstudianteEntity();
    EstudianteService estudianteService = new EstudianteService();

    @Test
    public void testEstudianteEntity() {
        // You can add test cases here to validate the behavior of your EstudianteEntity class.

        // Example:
        estudiante.setNombre("John");
        estudiante.setApellido("Doe");

        assertEquals("John", estudiante.getNombre());
        assertEquals("Doe", estudiante.getApellido());


    }



    // Add more test methods as needed to cover different aspects of your entity class.
}
