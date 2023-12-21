package edu.mtisw.testingwebapp;

import edu.mtisw.testingwebapp.entities.ProfesorEntity;
import edu.mtisw.testingwebapp.entities.ProjectorEntity;
import edu.mtisw.testingwebapp.repositories.ProfesorRepository;
import edu.mtisw.testingwebapp.repositories.ProjectorRepository;
import edu.mtisw.testingwebapp.services.ProfesorService;
import edu.mtisw.testingwebapp.services.ProjectorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class testProjector {

    @Autowired
    private ProjectorRepository projectorRepository;
    @Autowired
    private ProjectorService projectorService;

    @Autowired
    private ProfesorRepository profesorRepository;
    @Autowired
    private ProfesorService profesorService;

        @Test
        @Transactional
        void testProjectorEntityAttributes() {
            //para historial arancel debo tener un profesor
            String nombre ="John";
            String apellido = "doe";
            LocalDate fechaNacimiento = LocalDate.of(1990, 5, 15);
            String tipoColegio = "Subvencionado";
            String nombreColegio = "Colegio XYZ";
            String rut = "20623522";
            LocalDate annoEgreso = LocalDate.of(2023, 6, 30);
            LocalDate periodoInscripcion = LocalDate.of(2023, 9, 1);

            ProfesorEntity profesor1 = profesorService.guardarProfesor(rut, nombre, apellido, "2000/02/02", tipoColegio, nombreColegio,"2020/02/02");



            ProjectorEntity projector = new ProjectorEntity();

            // Test Monto Total
            double monto = 1500.0;
            projector.setMontoTotal(monto);
            assertEquals(monto, projector.getMontoTotal());

            // Test Tipo de Pago
            String tipoPago = "cuotas";
            projector.setTipoPago(tipoPago);
            assertEquals(tipoPago, projector.getTipoPago());

            // Test Cuotas Pactadas
            int cuotasPactadas = 5;
            projector.setCuotasPactadas(cuotasPactadas);
            assertEquals(cuotasPactadas, projector.getCuotasPactadas());

            // Test Cuotas Pagadas
            int cuotasPagadas = 3;
            projector.setCuotasPagadas(cuotasPagadas);
            assertEquals(cuotasPagadas, projector.getCuotasPagadas());

            // Test Total Pagado
            double totalPagado = 1200.0;
            projector.setTotalPagado(totalPagado);
            assertEquals(totalPagado, projector.getTotalPagado());

            // Test Último Pago
            LocalDate ultimoPago = LocalDate.of(2023, 10, 13);
            projector.setUltimoPago(ultimoPago);
            assertEquals(ultimoPago, projector.getUltimoPago());

            // Test Saldo por Pagar
            double saldoPorPagar = 300.0;
            projector.setSaldoPorPagar(saldoPorPagar);
            assertEquals(saldoPorPagar, projector.getSaldoPorPagar());

            // Test Cuotas de Retraso
            int cuotasRetraso = 2;
            projector.setCuotasRetraso(cuotasRetraso);
            assertEquals(cuotasRetraso, projector.getCuotasRetraso());

            projectorService.guardarProjector(profesor1.getId(),profesor1.getTipoColegio(), "2020/02/02",projector.getTipoPago(), String.valueOf(projector.getCuotasPactadas())) ;//lo mismo, crear profesor primero

            Long historialID = projectorService.obtenerPorProfesorId(profesor1.getId()).get().getId();
            System.out.println(historialID);

            projectorService.obtenerProjectors();

            projectorService.obtenerPorId(historialID);

            projectorService.anadirPago(historialID, 200000);

        }





}
