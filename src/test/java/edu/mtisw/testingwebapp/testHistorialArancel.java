package edu.mtisw.testingwebapp;

import edu.mtisw.testingwebapp.entities.EstudianteEntity;
import edu.mtisw.testingwebapp.entities.HistorialArancelEntity;
import edu.mtisw.testingwebapp.repositories.EstudianteRepository;
import edu.mtisw.testingwebapp.repositories.HistorialArancelRepository;
import edu.mtisw.testingwebapp.services.EstudianteService;
import edu.mtisw.testingwebapp.services.HistorialArancelService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class testHistorialArancel {

    @Autowired
    private HistorialArancelRepository historialArancelRepository;
    @Autowired
    private HistorialArancelService historialArancelService;

    @Autowired
    private EstudianteRepository estudianteRepository;
    @Autowired
    private EstudianteService estudianteService;

        @Test
        @Transactional
        void testHistorialArancelEntityAttributes() {
            //para historial arancel debo tener un estudiante
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

            Long historialID = historialArancelService.obtenerPorEstudianteId(estudiante1.getId()).get().getId();
            System.out.println(historialID);

            historialArancelService.obtenerHistorialArancels();

            historialArancelService.obtenerPorId(historialID);

            historialArancelService.anadirPago(historialID, 200000);

        }





}
