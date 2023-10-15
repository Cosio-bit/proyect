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

        EstudianteEntity estudiante = estudianteService.guardarEstudiante(rut, nombre, apellido, "2000/02/02", tipoColegio, nombreColegio,"2020/02/02" , "2020/02/02");


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

        //detallePagoService.obtenerPorHistorialArancelID(historialArancel.getId());


        int size = detallePagoEntities.size();

        System.out.println(size);

        detallePagoService.updateDetallesPagos(detallePagoEntities, 700);


    }

}