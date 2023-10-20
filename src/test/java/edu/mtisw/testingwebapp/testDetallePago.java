package edu.mtisw.testingwebapp;

import edu.mtisw.testingwebapp.entities.DetallePagoEntity;
import edu.mtisw.testingwebapp.entities.EstudianteEntity;
import edu.mtisw.testingwebapp.entities.HistorialArancelEntity;
import edu.mtisw.testingwebapp.repositories.DetallePagoRepository;
import edu.mtisw.testingwebapp.repositories.EstudianteRepository;
import edu.mtisw.testingwebapp.repositories.HistorialAcademicoRepository;
import edu.mtisw.testingwebapp.repositories.HistorialArancelRepository;
import edu.mtisw.testingwebapp.services.DetallePagoService;
import edu.mtisw.testingwebapp.services.EstudianteService;
import edu.mtisw.testingwebapp.services.HistorialAcademicoService;
import edu.mtisw.testingwebapp.services.HistorialArancelService;
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
    private HistorialArancelRepository historialArancelRepository;
    @Autowired
    private HistorialArancelService historialArancelService;

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private EstudianteService estudianteService;

    @Autowired
    private DetallePagoRepository detallePagoRepository;

    @Autowired
    private DetallePagoService detallePagoService;

    @Test
    @Transactional
    void testDetallePagoEntityAttributes() {

        //para detalle pago necesito el id de historial arancel y id de estudiante

        String nombre ="John";
        String apellido = "doe";
        String tipoColegio = "Subvencionado";
        String nombreColegio = "Colegio XYZ";
        String rut = "20623522";

        EstudianteEntity estudiante = estudianteService.guardarEstudiante(rut, nombre, apellido, "2000/02/02", tipoColegio, nombreColegio,"2020/02/02");


        String tipoPago = "cuotas";
        int cuotasPactadas = 5;
        HistorialArancelEntity historialArancel = historialArancelService.guardarHistorialArancel(estudiante.getId(),tipoColegio, "2020/02/02",tipoPago, "5") ;//lo mismo, crear estudiante primero

        System.out.println(historialArancel.getId());

        DetallePagoEntity detallePago = new DetallePagoEntity();
        // Test Monto de Pago
        double montoPago = 1000.0;
        detallePago.setMontoPago(montoPago);
        assertEquals(montoPago, detallePago.getMontoPago());

        // Test Fecha de Pago
        LocalDate fechaPago = LocalDate.of(2023, 10, 13);
        detallePago.setFechaPago(fechaPago);
        assertEquals(fechaPago, detallePago.getFechaPago());

        // Test Estado de Pago
        boolean notpagado = false;
        detallePago.setPagado(notpagado);
        assertEquals(notpagado, detallePago.isPagado());

        // Test Fecha de Vencimiento
        LocalDate fechaVencimiento = LocalDate.of(2023, 11, 1);
        detallePago.setFechaVencimiento(fechaVencimiento);
        assertEquals(fechaVencimiento, detallePago.getFechaVencimiento());


        List<DetallePagoEntity> detallePagoEntities =  detallePagoService.guardarDetallesPagos(historialArancel.getId(), cuotasPactadas,historialArancel.getMontoTotal()); //hay que guardarlo en el repo para ir a buscarlo y para eso necesitamos historial arancel
        DetallePagoEntity detallePago1 = detallePagoService.guardarDetallePago(historialArancel.getId(), 6,historialArancel.getMontoTotal(),6);
        //detallePagoService.obtenerPorHistorialArancelID(historialArancel.getId());


        int size = detallePagoEntities.size();

        detallePagoService.findbynotpagado(historialArancel.getId());

        System.out.println(size);

        detallePagoService.updateDetallesPagos(detallePagoEntities, 700);

        detallePagoService.updateDetallePago(detallePagoEntities.get(0),900,LocalDate.now());

        detallePagoService.obtenerDetallesPagos();

        detallePagoService.pagar(detallePagoEntities.get(0).getId());

        detallePagoService.obtenerPorHistorialArancelID(historialArancel.getId());

    }


        @Test
        public void testCalcularArancelNotas() {
            // Prueba con un promedio en el rango de 950 a 1000
            double resultado = detallePagoService.calcularArancelNotas(950, 1000);
            assertEquals(0.90 * 1000, resultado, 0.01);

            // Prueba con un promedio en el rango de 900 a 949
            resultado = detallePagoService.calcularArancelNotas(925, 1000);
            assertEquals(0.95 * 1000, resultado, 0.01);

            // Prueba con un promedio en el rango de 850 a 899
            resultado = detallePagoService.calcularArancelNotas(875, 1000);
            assertEquals(0.98 * 1000, resultado, 0.01);

            // Prueba con un promedio fuera de los rangos
            resultado = detallePagoService.calcularArancelNotas(800, 1000);
            assertEquals(1000, resultado, 0.01);
        }

        @Test
        public void testCalcularMesesAtraso() {
            // Fecha de pago actual y fecha de vencimiento
            LocalDate pagoActual = LocalDate.of(2023, 7, 15);
            LocalDate fechaVencimiento = LocalDate.of(2023, 5, 1);

            // Prueba con las fechas anteriores
            int resultado = detallePagoService.calcularMesesAtraso(pagoActual, fechaVencimiento);
            assertEquals(2, resultado);

            // Prueba con las fechas iguales
            fechaVencimiento = LocalDate.of(2023, 7, 15);
            resultado = detallePagoService.calcularMesesAtraso(pagoActual, fechaVencimiento);
            assertEquals(0, resultado);

            // Prueba con las fechas futuras
            fechaVencimiento = LocalDate.of(2023, 9, 1);
            resultado = detallePagoService.calcularMesesAtraso(pagoActual, fechaVencimiento);
            assertEquals(-1, resultado);
        }

        @Test
        public void testCalcularArancelInteres() {
            // Prueba con un castigo de interés igual a 1
            double resultado = detallePagoService.calcularArancelInteres(1, 1000);
            assertEquals(1.03 * 1000, resultado, 0.01);

            // Prueba con un castigo de interés igual a 2
            resultado = detallePagoService.calcularArancelInteres(2, 1000);
            assertEquals(1.06 * 1000, resultado, 0.01);

            // Prueba con un castigo de interés igual a 3
            resultado = detallePagoService.calcularArancelInteres(3, 1000);
            assertEquals(1.09 * 1000, resultado, 0.01);

            // Prueba con un castigo de interés mayor a 3
            resultado = detallePagoService.calcularArancelInteres(4, 1000);
            assertEquals(1.15 * 1000, resultado, 0.01);
        }
    }


