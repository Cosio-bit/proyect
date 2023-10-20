package edu.mtisw.testingwebapp;

import edu.mtisw.testingwebapp.entities.DetallePagoEntity;
import edu.mtisw.testingwebapp.entities.EstudianteEntity;
import edu.mtisw.testingwebapp.entities.HistorialAcademicoEntity;
import edu.mtisw.testingwebapp.entities.HistorialArancelEntity;
import edu.mtisw.testingwebapp.repositories.EstudianteRepository;
import edu.mtisw.testingwebapp.services.DetallePagoService;
import edu.mtisw.testingwebapp.services.EstudianteService;
import edu.mtisw.testingwebapp.services.HistorialAcademicoService;
import edu.mtisw.testingwebapp.services.HistorialArancelService;
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

    @Autowired
    private HistorialAcademicoService historialAcademicoService;

    @Autowired
    private HistorialArancelService historialArancelService;

    @Autowired
    private DetallePagoService detallePagoService;


    @Test
        void testEstudianteEntityAttributes() {


        EstudianteEntity estudiante = new EstudianteEntity();

        String nombre = "John";
        String apellido = "Doe";
        String tipoColegio = "Subvencionado";
        String nombreColegio = "Colegio XYZ";
        String rut = "20623522";


        // Test Nombre
        estudiante.setNombre(nombre);
        assertEquals("John", estudiante.getNombre());

        // Test Apellido
        estudiante.setApellido(apellido);
        assertEquals("Doe", estudiante.getApellido());

        // Test Fecha de Nacimiento
        LocalDate fechaNacimiento = LocalDate.of(1990, 5, 15);
        estudiante.setFechaNacimiento(fechaNacimiento);
        assertEquals(fechaNacimiento, estudiante.getFechaNacimiento());

        // Test Tipo de Colegio
        estudiante.setTipoColegio(tipoColegio);
        assertEquals("Subvencionado", estudiante.getTipoColegio());

        // Test Nombre del Colegio
        estudiante.setNombreColegio(nombreColegio);
        assertEquals("Colegio XYZ", estudiante.getNombreColegio());

        // Test Rut
        estudiante.setRut(rut);
        assertEquals("20623522", estudiante.getRut());

        // Test Año de Egreso
        LocalDate annoEgreso = LocalDate.of(2023, 6, 30);
        estudiante.setAnnoEgreso(annoEgreso);
        assertEquals(annoEgreso, estudiante.getAnnoEgreso());

    }


    @Test
    void testservice(){
            String nombre ="John";
            String apellido = "Doe";
            String tipoColegio = "Subvencionado";
            String nombreColegio = "Colegio XYZ";
            String rut = "20623525";
            //por separado para 1 estudiante

            EstudianteEntity estudiante1 = estudianteService.guardarEstudiante(rut, nombre, apellido, "2000/02/02", tipoColegio, nombreColegio,"2020/02/02");

            // Save the academic history for the student
            HistorialAcademicoEntity historialAcademico1 = historialAcademicoService.guardarHistorialAcademico(estudiante1.getId(), "900,800,700");

            String tipoPago = "cuotas";
            int cuotasPactadas = 5;
            HistorialArancelEntity historialArancel = historialArancelService.guardarHistorialArancel(estudiante1.getId(),tipoColegio, "2020/02/02",tipoPago, "5") ;//lo mismo, crear estudiante primero

            List<DetallePagoEntity> detallePagoEntities =  detallePagoService.guardarDetallesPagos(historialArancel.getId(), cuotasPactadas,historialArancel.getMontoTotal()); //hay que guardarlo en el repo para ir a buscarlo y para eso necesitamos historial arancel
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

