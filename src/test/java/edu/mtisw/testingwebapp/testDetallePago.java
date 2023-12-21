package edu.mtisw.testingwebapp;

import edu.mtisw.testingwebapp.entities.PrestamoEntity;
import edu.mtisw.testingwebapp.entities.ProfesorEntity;
import edu.mtisw.testingwebapp.entities.ProjectorEntity;
import edu.mtisw.testingwebapp.repositories.PrestamoRepository;
import edu.mtisw.testingwebapp.repositories.ProfesorRepository;
import edu.mtisw.testingwebapp.repositories.HistorialAcademicoRepository;
import edu.mtisw.testingwebapp.repositories.ProjectorRepository;
import edu.mtisw.testingwebapp.services.PrestamoService;
import edu.mtisw.testingwebapp.services.ProfesorService;
import edu.mtisw.testingwebapp.services.HistorialAcademicoService;
import edu.mtisw.testingwebapp.services.ProjectorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class testDetallePago {


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

        //para detalle pago necesito el id de historial arancel y id de profesor

        String nombre ="John";
        String apellido = "doe";
        String tipoColegio = "Subvencionado";
        String nombreColegio = "Colegio XYZ";
        String rut = "20623522";

        ProfesorEntity profesor = profesorService.guardarProfesor(rut, nombre, apellido, "2000/02/02", tipoColegio, nombreColegio,"2020/02/02");


        String tipoPago = "cuotas";
        int cuotasPactadas = 5;
        ProjectorEntity projector = projectorService.guardarProjector(profesor.getId(),tipoColegio, "2020/02/02",tipoPago, "5") ;//lo mismo, crear profesor primero

        System.out.println(projector.getId());

        PrestamoEntity prestamo = new PrestamoEntity();
        // Test Monto de Pago
        double montoPago = 1000.0;
        prestamo.setMontoPago(montoPago);
        assertEquals(montoPago, prestamo.getMontoPago());

        // Test Fecha de Pago
        LocalDate fechaPago = LocalDate.of(2023, 10, 13);
        prestamo.setFechaPago(fechaPago);
        assertEquals(fechaPago, prestamo.getFechaPago());

        // Test Estado de Pago
        boolean notpagado = false;
        prestamo.setPagado(notpagado);
        assertEquals(notpagado, prestamo.isPagado());

        // Test Fecha de Vencimiento
        LocalDate fechaVencimiento = LocalDate.of(2023, 11, 1);
        prestamo.setFechaVencimiento(fechaVencimiento);
        assertEquals(fechaVencimiento, prestamo.getFechaVencimiento());


        List<PrestamoEntity> prestamoEntities =  prestamoService.guardarDetallesPagos(projector.getId(), cuotasPactadas,projector.getMontoTotal()); //hay que guardarlo en el repo para ir a buscarlo y para eso necesitamos historial arancel
        PrestamoEntity prestamo1 = prestamoService.guardarDetallePago(projector.getId(), 6,projector.getMontoTotal(),6);
        //prestamoService.obtenerPorProjectorID(projector.getId());


        int size = prestamoEntities.size();

        prestamoService.findbynotpagado(projector.getId());

        System.out.println(size);

        prestamoService.updateDetallesPagos(prestamoEntities, 700);

        prestamoService.updateDetallePago(prestamoEntities.get(0),900,LocalDate.now());

        prestamoService.obtenerDetallesPagos();

        prestamoService.pagar(prestamoEntities.get(0).getId());

        prestamoService.obtenerPorProjectorID(projector.getId());

    }


        @Test
        public void testCalcularArancelNotas() {
            // Prueba con un promedio en el rango de 950 a 1000
            double resultado = prestamoService.calcularArancelNotas(950, 1000);
            assertEquals(0.90 * 1000, resultado, 0.01);

            // Prueba con un promedio en el rango de 900 a 949
            resultado = prestamoService.calcularArancelNotas(925, 1000);
            assertEquals(0.95 * 1000, resultado, 0.01);

            // Prueba con un promedio en el rango de 850 a 899
            resultado = prestamoService.calcularArancelNotas(875, 1000);
            assertEquals(0.98 * 1000, resultado, 0.01);

            // Prueba con un promedio fuera de los rangos
            resultado = prestamoService.calcularArancelNotas(800, 1000);
            assertEquals(1000, resultado, 0.01);
        }

        @Test
        public void testCalcularMesesAtraso() {
            // Fecha de pago actual y fecha de vencimiento
            LocalDate pagoActual = LocalDate.of(2023, 7, 15);
            LocalDate fechaVencimiento = LocalDate.of(2023, 5, 1);

            // Prueba con las fechas anteriores
            int resultado = prestamoService.calcularMesesAtraso(pagoActual, fechaVencimiento);
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

        @Test
        public void testCalcularArancelInteres() {
            // Prueba con un castigo de interés igual a 1
            double resultado = prestamoService.calcularArancelInteres(1, 1000);
            assertEquals(1.03 * 1000, resultado, 0.01);

            // Prueba con un castigo de interés igual a 2
            resultado = prestamoService.calcularArancelInteres(2, 1000);
            assertEquals(1.06 * 1000, resultado, 0.01);

            // Prueba con un castigo de interés igual a 3
            resultado = prestamoService.calcularArancelInteres(3, 1000);
            assertEquals(1.09 * 1000, resultado, 0.01);

            // Prueba con un castigo de interés mayor a 3
            resultado = prestamoService.calcularArancelInteres(4, 1000);
            assertEquals(1.15 * 1000, resultado, 0.01);
        }
    }


