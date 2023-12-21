package edu.mtisw.testingwebapp;

import edu.mtisw.testingwebapp.entities.PrestamoEntity;
import edu.mtisw.testingwebapp.entities.ProfesorEntity;
import edu.mtisw.testingwebapp.entities.HistorialAcademicoEntity;
import edu.mtisw.testingwebapp.entities.ProjectorEntity;
import edu.mtisw.testingwebapp.repositories.ProfesorRepository;
import edu.mtisw.testingwebapp.services.PrestamoService;
import edu.mtisw.testingwebapp.services.ProfesorService;
import edu.mtisw.testingwebapp.services.HistorialAcademicoService;
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
    private HistorialAcademicoService historialAcademicoService;

    @Autowired
    private ProjectorService projectorService;

    @Autowired
    private PrestamoService prestamoService;


    @Test
        void testProfesorEntityAttributes() {


        ProfesorEntity profesor = new ProfesorEntity();

        String nombre = "John";
        String apellido = "Doe";
        String tipoColegio = "Subvencionado";
        String nombreColegio = "Colegio XYZ";
        String rut = "20623522";


        // Test Nombre
        profesor.setNombre(nombre);
        assertEquals("John", profesor.getNombre());

        // Test Apellido
        profesor.setApellido(apellido);
        assertEquals("Doe", profesor.getApellido());

        // Test Fecha de Nacimiento
        LocalDate fechaNacimiento = LocalDate.of(1990, 5, 15);
        profesor.setFechaNacimiento(fechaNacimiento);
        assertEquals(fechaNacimiento, profesor.getFechaNacimiento());

        // Test Tipo de Colegio
        profesor.setTipoColegio(tipoColegio);
        assertEquals("Subvencionado", profesor.getTipoColegio());

        // Test Nombre del Colegio
        profesor.setNombreColegio(nombreColegio);
        assertEquals("Colegio XYZ", profesor.getNombreColegio());

        // Test Rut
        profesor.setRut(rut);
        assertEquals("20623522", profesor.getRut());

        // Test Año de Egreso
        LocalDate annoEgreso = LocalDate.of(2023, 6, 30);
        profesor.setAnnoEgreso(annoEgreso);
        assertEquals(annoEgreso, profesor.getAnnoEgreso());

    }


    @Test
    void testservice(){
            String nombre ="John";
            String apellido = "Doe";
            String tipoColegio = "Subvencionado";
            String nombreColegio = "Colegio XYZ";
            String rut = "20623525";
            //por separado para 1 profesor

            ProfesorEntity profesor1 = profesorService.guardarProfesor(rut, nombre, apellido, "2000/02/02", tipoColegio, nombreColegio,"2020/02/02");

            // Save the academic history for the student
            HistorialAcademicoEntity historialAcademico1 = historialAcademicoService.guardarHistorialAcademico(profesor1.getId(), "900,800,700");

            String tipoPago = "cuotas";
            int cuotasPactadas = 5;
            ProjectorEntity projector = projectorService.guardarProjector(profesor1.getId(),tipoColegio, "2020/02/02",tipoPago, "5") ;//lo mismo, crear profesor primero

            List<PrestamoEntity> prestamoEntities =  prestamoService.guardarDetallesPagos(projector.getId(), cuotasPactadas,projector.getMontoTotal()); //hay que guardarlo en el repo para ir a buscarlo y para eso necesitamos historial arancel
        }



        @Test
        void testManage(){

            // Asumiendo que este es el lugar en tu código donde deseas llamar la función manageGuardar
            ProfesorEntity profesor2 = profesorService.manageGuardar("20623522", "andrea", "cosio", "2000/02/02", "Municipal", "cosaco", "2020/02/02", "cuotas", "5", "900,800,700");
            //profesorService.manageGuardar(rut, nombre, apellido,"2000/02/02", tipoColegio,nombreColegio,"2020/02/02",tipoPago, "5","900,800,700","2020/02/02");
            //profesorService.cuotaUpdate(profesorService.obtenerProfesors());

            profesorService.obtenerPorId(profesor2.getId());

            //profesorService.obtenerPorRut(profesor2.getRut());

            List<ProfesorEntity> profesorEntities = profesorService.obtenerProfesors();

            profesorService.obtenerRuts();
            profesorService.obtenerPorRut(profesor2.getRut());

            List<List<String>> lista = profesorService.ExcelImporterToList("examen");

            //profesorService.agregarNotasAHistorial("examen");

            //profesorService.cuotaUpdate(profesorEntities);



        }
    }




    // Add more test methods as needed to cover different aspects of your entity class.

