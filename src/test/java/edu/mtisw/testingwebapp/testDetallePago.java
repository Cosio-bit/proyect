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

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class testDetallePago {

    @Autowired
    private HistorialAcademicoRepository historialAcademicoRepository;
    @Autowired
    private HistorialAcademicoService historialAcademicoService;

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
    void testDetallePagoEntityAttributes() {

        //para detalle pago necesito el id de historial arancel y id de estudiante

        String nombre ="John";
        String apellido = "doe";
        LocalDate fechaNacimiento = LocalDate.of(1990, 5, 15);
        String tipoColegio = "Subvencionado";
        String nombreColegio = "Colegio XYZ";
        String rut = "20623522";
        LocalDate annoEgreso = LocalDate.of(2023, 6, 30);
        LocalDate periodoInscripcion = LocalDate.of(2023, 9, 1);

        EstudianteEntity estudiante1 = estudianteService.guardarEstudiante(rut, nombre, apellido, "2000/02/02", tipoColegio, nombreColegio,"2020/02/02" , "2020/02/02");

        HistorialArancelEntity historialArancel = new HistorialArancelEntity();

        // Test Monto Total
        double monto = 1500.0;
        historialArancel.setMontoTotal(monto);
        assertEquals(monto, historialArancel.getMontoTotal());

        // Test Tipo de Pago
        String tipoPago = "cuotas";
        historialArancel.setTipoPago(tipoPago);
        assertEquals(tipoPago, historialArancel.getTipoPago());

        // Test Cuotas Pactadas
        int cuotasPactadas = 5;
        historialArancel.setCuotasPactadas(cuotasPactadas);
        assertEquals(cuotasPactadas, historialArancel.getCuotasPactadas());

        // Test Cuotas Pagadas
        int cuotasPagadas = 3;
        historialArancel.setCuotasPagadas(cuotasPagadas);
        assertEquals(cuotasPagadas, historialArancel.getCuotasPagadas());

        // Test Total Pagado
        double totalPagado = 1200.0;
        historialArancel.setTotalPagado(totalPagado);
        assertEquals(totalPagado, historialArancel.getTotalPagado());

        // Test Último Pago
        LocalDate ultimoPago = LocalDate.of(2023, 10, 13);
        historialArancel.setUltimoPago(ultimoPago);
        assertEquals(ultimoPago, historialArancel.getUltimoPago());

        // Test Saldo por Pagar
        double saldoPorPagar = 300.0;
        historialArancel.setSaldoPorPagar(saldoPorPagar);
        assertEquals(saldoPorPagar, historialArancel.getSaldoPorPagar());

        // Test Cuotas de Retraso
        int cuotasRetraso = 2;
        historialArancel.setCuotasRetraso(cuotasRetraso);
        assertEquals(cuotasRetraso, historialArancel.getCuotasRetraso());

        historialArancelService.guardarHistorialArancel(estudiante1.getId(),estudiante1.getTipoColegio(), "2020/02/02",historialArancel.getTipoPago(), String.valueOf(historialArancel.getCuotasPactadas())) ;//lo mismo, crear estudiante primero




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


        detallePagoService.guardarDetallesPagos(historialArancel.getId(),historialArancel.getCuotasPactadas(),historialArancel.getMontoTotal()); //hay que guardarlo en el repo para ir a buscarlo y para eso necesitamos historial arancel

        List<DetallePagoEntity> detallePagoEntities = detallePagoService.obtenerPorHistorialArancelID(historialArancel.getId());
        int size = detallePagoEntities.size();

        System.out.println(size);

        //detallePagoService.updateDetallesPagos(detallePagoService.obtenerPorHistorialArancelID(historialArancel.getId()), 700);


    }

}