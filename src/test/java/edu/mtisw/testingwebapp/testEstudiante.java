package edu.mtisw.testingwebapp;

import edu.mtisw.testingwebapp.entities.EstudianteEntity;
import edu.mtisw.testingwebapp.repositories.EstudianteRepository;
import edu.mtisw.testingwebapp.services.EstudianteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class testEstudiante {

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private EstudianteService estudianteService;


    @Test
        void testEstudianteEntityAttributes() {


            EstudianteEntity estudiante = new EstudianteEntity();

            // Test Nombre
            estudiante.setNombre("John");
            assertEquals("John", estudiante.getNombre());

            // Test Apellido
            estudiante.setApellido("Doe");
            assertEquals("Doe", estudiante.getApellido());

            // Test Fecha de Nacimiento
            LocalDate fechaNacimiento = LocalDate.of(1990, 5, 15);
            estudiante.setFechaNacimiento(fechaNacimiento);
            assertEquals(fechaNacimiento, estudiante.getFechaNacimiento());

            // Test Tipo de Colegio
            estudiante.setTipoColegio("Subvencionado");
            assertEquals("Subvencionado", estudiante.getTipoColegio());

            // Test Nombre del Colegio
            estudiante.setNombreColegio("Colegio XYZ");
            assertEquals("Colegio XYZ", estudiante.getNombreColegio());

            // Test Rut
            estudiante.setRut("20623522");
            assertEquals("20623522", estudiante.getRut());

            // Test Año de Egreso
            LocalDate annoEgreso = LocalDate.of(2023, 6, 30);
            estudiante.setAnnoEgreso(annoEgreso);
            assertEquals(annoEgreso, estudiante.getAnnoEgreso());

            // Test Periodo de Inscripción
            LocalDate periodoInscripcion = LocalDate.of(2023, 9, 1);
            estudiante.setPeriodoInscripcion(periodoInscripcion);
            assertEquals(periodoInscripcion, estudiante.getPeriodoInscripcion());

            EstudianteEntity estudiante1 = estudianteService.guardarEstudiante(estudiante.getRut(), estudiante.getNombre(), estudiante.getApellido(), "2000/02/02", estudiante.getTipoColegio(), estudiante.getNombreColegio(),"2020/02/02" , "2020/02/02");

            estudianteService.obtenerPorId(estudiante1.getId());

            estudianteService.obtenerPorRut(estudiante.getRut());

            estudianteService.obtenerEstudiantes();

            estudianteService.obtenerRuts();

            List<List<String>> lista = estudianteService.ExcelImporterToList("examen");




        }
    }




    // Add more test methods as needed to cover different aspects of your entity class.

