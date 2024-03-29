package edu.mtisw.testingwebapp;

import edu.mtisw.testingwebapp.entities.PrestamoEntity;
import edu.mtisw.testingwebapp.entities.ProfesorEntity;
import edu.mtisw.testingwebapp.entities.ProjectorEntity;
import edu.mtisw.testingwebapp.repositories.PrestamoRepository;
import edu.mtisw.testingwebapp.repositories.ProfesorRepository;

import edu.mtisw.testingwebapp.repositories.ProjectorRepository;
import edu.mtisw.testingwebapp.services.PrestamoService;
import edu.mtisw.testingwebapp.services.ProfesorService;

import edu.mtisw.testingwebapp.services.ProjectorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class testPrestamo {


    @Autowired
    private ProjectorRepository projectorRepository;
    @Autowired
    private ProjectorService projectorService;

    @Autowired
    private ProfesorRepository profesorRepository;

    @Autowired
    private ProfesorService profesorService;

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private PrestamoService prestamoService;

    @Test
    @Transactional
    void testDetallePagoEntityAttributes() {

        String rut = "20623522";
        String nombre = "John";
        String apellido = "Doe";

        ProfesorEntity profesor = profesorService.guardarProfesor(rut, nombre, apellido);


        String tipoPago = "cuotas";
        int cuotasPactadas = 5;
        ProjectorEntity projector = projectorService.guardarProjector("Proyector 1", "Epson");

        System.out.println(projector.getId());

        PrestamoEntity reparacion = new PrestamoEntity();

        // Test 
/*
        assertEquals(montoPago, reparacion.getMontoPago());

        // Test Fecha de Pago
        LocalDate fechaPago = LocalDate.of(2023, 10, 13);
        reparacion.setFechaPago(fechaPago);
        assertEquals(fechaPago, reparacion.getFechaPago());

        // Test Estado de Pago
        boolean notpagado = false;
        reparacion.setPagado(notpagado);
        assertEquals(notpagado, reparacion.isPagado());

        // Test Fecha de Vencimiento
        LocalDate fechaVencimiento = LocalDate.of(2023, 11, 1);
        reparacion.setFechaVencimiento(fechaVencimiento);
        assertEquals(fechaVencimiento, reparacion.getFechaVencimiento());


        List<PrestamoEntity> prestamoEntities =  prestamoService.guardarDetallesPagos(projector.getId(), cuotasPactadas,projector.getMontoTotal()); //hay que guardarlo en el repo para ir a buscarlo y para eso necesitamos historial arancel
        PrestamoEntity prestamo1 = prestamoService.guardarDetallePago(projector.getId(), 6,projector.getMontoTotal(),6);
        //prestamoService.obtenerPorProjectorID(projector.getId());


       */

    }


 /*  
        @Test
        public void testCalcularMesesAtraso() {
            // Fecha de pago actual y fecha de vencimiento
            LocalDate pagoActual = LocalDate.of(2023, 7, 15);
            LocalDate fechaVencimiento = LocalDate.of(2023, 5, 1);

            // Prueba con las fechas anteriores
            int resultado = prestamoService.calcularAtrazo(pagoActual, fechaVencimiento);
            assertEquals(2, resultado);

            // Prueba con las fechas iguales
            fechaVencimiento = LocalDate.of(2023, 7, 15);
            resultado = prestamoService.calcularMesesAtraso(pagoActual, fechaVencimiento);
            assertEquals(0, resultado);

            // Prueba con las fechas futuras
            fechaVencimiento = LocalDate.of(2023, 9, 1);
            resultado = prestamoService.calcularMesesAtraso(pagoActual, fechaVencimiento);
            assertEquals(-1, resultado);
        }
*/
  
    }


