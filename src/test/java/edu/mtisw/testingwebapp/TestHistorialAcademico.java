package edu.mtisw.testingwebapp;

import edu.mtisw.testingwebapp.entities.ProfesorEntity;
import edu.mtisw.testingwebapp.entities.HistorialAcademicoEntity;
import edu.mtisw.testingwebapp.repositories.ProfesorRepository;
import edu.mtisw.testingwebapp.repositories.HistorialAcademicoRepository;
import edu.mtisw.testingwebapp.services.ProfesorService;
import edu.mtisw.testingwebapp.services.HistorialAcademicoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TestHistorialAcademico {
    @Autowired
    private ProfesorService profesorService; // Add this annotation

    @Autowired
    private HistorialAcademicoService historialAcademicoService; // Add this annotation

    @Autowired
    private ProfesorRepository profesorRepository; // Add this annotation if needed

    @Autowired
    private HistorialAcademicoRepository historialAcademicoRepository; // Add this annotation if needed


    @Test
    @Transactional
    void testHistorialAcademicoEntityAttributes() {

        // Create a student
        String nombre = "John";
        String apellido = "Doe";
        LocalDate fechaNacimiento = LocalDate.of(1990, 5, 15);
        String tipoColegio = "Subvencionado";
        String nombreColegio = "Colegio XYZ";
        String rut = "20623522";
        LocalDate annoEgreso = LocalDate.of(2023, 6, 30);
        LocalDate periodoInscripcion = LocalDate.of(2023, 9, 1);
        ProfesorEntity profesor1 = profesorService.guardarProfesor(rut, nombre, apellido, "2000/02/02", tipoColegio, nombreColegio, "2020/02/02");

        // Initialize the student's academic history
        HistorialAcademicoEntity historialAcademico = new HistorialAcademicoEntity();

        // Test Número de Exámenes
        int nroExamenes = 5;
        historialAcademico.setNroExamenes(nroExamenes);
        assertEquals(nroExamenes, historialAcademico.getNroExamenes());

        // Test Promedio de Exámenes
        double promedioExamenes = 85.5;
        historialAcademico.setPromedioExamenes(promedioExamenes);
        assertEquals(promedioExamenes, historialAcademico.getPromedioExamenes());

        // Test Notas (List of Integers)
        List<Integer> notas = List.of(90, 88, 92, 85, 87);
        historialAcademico.setNotas(notas);
        assertEquals(notas, historialAcademico.getNotas());

        // Save the academic history for the student
        HistorialAcademicoEntity historialAcademico1 = historialAcademicoService.guardarHistorialAcademico(profesor1.getId(), "900,800,700");

        // Calculate the average
        double promedio = historialAcademicoService.calcularPromedioHistorial(historialAcademico1);

        // Access other methods you need to test
        historialAcademicoService.obtenerHistorialAcademicos();


        historialAcademicoService.anadirNota(historialAcademico1.getId(), 900);
        historialAcademicoService.obtenerPorProfesorId(profesor1.getId());

        System.out.println(historialAcademicoService.obtenerPorProfesorId(profesor1.getId()).getPromedioExamenes());


        historialAcademicoService.anadirNotaConFecha(historialAcademico1.getId(), 900, "2020/02/02");
    }
}
