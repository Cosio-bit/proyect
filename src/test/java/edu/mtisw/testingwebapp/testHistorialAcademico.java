package edu.mtisw.testingwebapp;

import edu.mtisw.testingwebapp.entities.EstudianteEntity;
import edu.mtisw.testingwebapp.entities.HistorialAcademicoEntity;
import edu.mtisw.testingwebapp.repositories.EstudianteRepository;
import edu.mtisw.testingwebapp.repositories.HistorialAcademicoRepository;
import edu.mtisw.testingwebapp.services.EstudianteService;
import edu.mtisw.testingwebapp.services.HistorialAcademicoService;
import edu.mtisw.testingwebapp.services.HistorialArancelService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class testHistorialAcademico {

    @Autowired
    private HistorialAcademicoRepository historialAcademicoRepository;
    @Autowired
    private HistorialAcademicoService historialAcademicoService;

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private EstudianteService estudianteService;


    @Test
    void testHistorialAcademicoEntityAttributes() {
        //para historial academico debo tener un estudiante
        String nombre ="John";
        String apellido = "doe";
        LocalDate fechaNacimiento = LocalDate.of(1990, 5, 15);
        String tipoColegio = "Subvencionado";
        String nombreColegio = "Colegio XYZ";
        String rut = "20623522";
        LocalDate annoEgreso = LocalDate.of(2023, 6, 30);
        LocalDate periodoInscripcion = LocalDate.of(2023, 9, 1);
        EstudianteEntity estudiante1 = estudianteService.guardarEstudiante(rut, nombre, apellido, "2000/02/02", tipoColegio, nombreColegio,"2020/02/02" , "2020/02/02");


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


        historialAcademicoService.guardarHistorialAcademico(estudiante1.getId(), "900,800,700"); //crear estudiante para ir a buscar cosas al repo
    }




}
