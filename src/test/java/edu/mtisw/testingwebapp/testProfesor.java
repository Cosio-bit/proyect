package edu.mtisw.testingwebapp;

import edu.mtisw.testingwebapp.entities.PrestamoEntity;
import edu.mtisw.testingwebapp.entities.ProfesorEntity;
import edu.mtisw.testingwebapp.entities.ProjectorEntity;
import edu.mtisw.testingwebapp.repositories.ProfesorRepository;
import edu.mtisw.testingwebapp.services.PrestamoService;
import edu.mtisw.testingwebapp.services.ProfesorService;
import edu.mtisw.testingwebapp.services.ProjectorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class testProfesor {

    @Autowired
    private ProfesorRepository profesorRepository;

    @Autowired
    private ProfesorService profesorService;

    @Autowired
    private ProjectorService projectorService;

    @Autowired
    private PrestamoService prestamoService;


    @Test
        void testProfesorEntityAttributes() {


        ProfesorEntity profesor = new ProfesorEntity();

        // Test Nombre
        profesor.setNombre("John");
        assertEquals("John", profesor.getNombre());

        // Test Apellido
        profesor.setApellido("Doe");
        assertEquals("Doe", profesor.getApellido());

        // Test Tipo de Colegio
        profesor.setAtrasos(0);
        assertEquals(0, profesor.getAtrasos());

        // Test Nombre del Colegio
        profesor.setInfracciones(0);
        assertEquals(0, profesor.getInfracciones());

        // Test Rut
        profesor.setRut("20623522");
        assertEquals("20623522", profesor.getRut());


    }


    @Test
    void testservice(){

            ProfesorEntity profesor1 = profesorService.guardarProfesor("20623522", "andrea", "cosio"); //lo mismo, crear profesor primero
            

            List<PrestamoEntity> prestamoEntities =  prestamoService.obtenerPrestamos();
        }



        @Test
        void testManage(){

            // Asumiendo que este es el lugar en tu código donde deseas llamar la función manageGuardar
            ProfesorEntity profesor2 = profesorService.guardarProfesor("20623522", "andrea", "cosio");
            //profesorService.manageGuardar(rut, nombre, apellido,"2000/02/02", tipoColegio,nombreColegio,"2020/02/02",tipoPago, "5","900,800,700","2020/02/02");
            //profesorService.cuotaUpdate(profesorService.obtenerProfesors());

            profesorService.obtenerPorId(profesor2.getId());

            //profesorService.obtenerPorRut(profesor2.getRut());

            List<ProfesorEntity> profesorEntities = profesorService.obtenerProfesores();


        }
    }




    // Add more test methods as needed to cover different aspects of your entity class.

