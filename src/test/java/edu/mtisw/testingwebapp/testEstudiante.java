package edu.mtisw.testingwebapp;

import edu.mtisw.testingwebapp.entities.PrestamoEntity;
import edu.mtisw.testingwebapp.entities.EstudianteEntity;
import edu.mtisw.testingwebapp.entities.HistorialAcademicoEntity;
import edu.mtisw.testingwebapp.entities.ProyectorEntity;
import edu.mtisw.testingwebapp.repositories.ProfesorRepository;
import edu.mtisw.testingwebapp.services.PrestamoService;
import edu.mtisw.testingwebapp.services.ProfesorService;
import edu.mtisw.testingwebapp.services.HistorialAcademicoService;
import edu.mtisw.testingwebapp.services.ProyectorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class testEstudiante {

    @Autowired
    private ProfesorRepository estudianteRepository;

    @Autowired
    private ProfesorService estudianteService;

    @Autowired
    private HistorialAcademicoService historialAcademicoService;

    @Autowired
    private ProyectorService historialArancelService;

    @Autowired
    private PrestamoService detallePagoService;


    @Test
        void testEstudianteEntityAttributes() {


        EstudianteEntity profesor = new EstudianteEntity();

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

            EstudianteEntity estudiante1 = estudianteService.guardarEstudiante(rut, nombre, apellido, "2000/02/02", tipoColegio, nombreColegio,"2020/02/02");

            // Save the academic history for the student
            HistorialAcademicoEntity historialAcademico1 = historialAcademicoService.guardarHistorialAcademico(estudiante1.getId(), "900,800,700");

            String tipoPago = "cuotas";
            int cuotasPactadas = 5;
            ProyectorEntity historialArancel = historialArancelService.guardarHistorialArancel(estudiante1.getId(),tipoColegio, "2020/02/02",tipoPago, "5") ;//lo mismo, crear profesor primero

            List<PrestamoEntity> detallePagoEntities =  detallePagoService.guardarDetallesPagos(historialArancel.getId(), cuotasPactadas,historialArancel.getMontoTotal()); //hay que guardarlo en el repo para ir a buscarlo y para eso necesitamos historial arancel
        }



        @Test
        void testManage(){

            // Asumiendo que este es el lugar en tu código donde deseas llamar la función manageGuardar
            EstudianteEntity estudiante2 = estudianteService.manageGuardar("20623522", "andrea", "cosio", "2000/02/02", "Municipal", "cosaco", "2020/02/02", "cuotas", "5", "900,800,700");
            //estudianteService.manageGuardar(rut, nombre, apellido,"2000/02/02", tipoColegio,nombreColegio,"2020/02/02",tipoPago, "5","900,800,700","2020/02/02");
            //estudianteService.cuotaUpdate(estudianteService.obtenerEstudiantes());

            estudianteService.obtenerPorId(estudiante2.getId());

            //estudianteService.obtenerPorRut(estudiante2.getRut());

            List<EstudianteEntity> estudianteEntities = estudianteService.obtenerEstudiantes();

            estudianteService.obtenerRuts();
            estudianteService.obtenerPorRut(estudiante2.getRut());

            List<List<String>> lista = estudianteService.ExcelImporterToList("examen");

            //estudianteService.agregarNotasAHistorial("examen");

            //estudianteService.cuotaUpdate(estudianteEntities);



        }
    }




    // Add more test methods as needed to cover different aspects of your entity class.

