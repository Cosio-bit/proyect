/*

package edu.mtisw.testingwebapp;

import edu.mtisw.testingwebapp.entities.ReparacionEntity;
import edu.mtisw.testingwebapp.entities.ProfesorEntity;
import edu.mtisw.testingwebapp.entities.VehiculoEntity;
import edu.mtisw.testingwebapp.repositories.ReparacionRepository;
import edu.mtisw.testingwebapp.repositories.ProfesorRepository;

import edu.mtisw.testingwebapp.repositories.VehiculoRepository;
import edu.mtisw.testingwebapp.services.ReparacionService;

import edu.mtisw.testingwebapp.services.VehiculoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class testReparacion {


    @Autowired
    private VehiculoRepository vehiculoRepository;
    @Autowired
    private VehiculoService vehiculoService;

    @Autowired
    private ProfesorRepository profesorRepository;


    @Autowired
    private ReparacionRepository reparacionRepository;

    @Autowired
    private ReparacionService reparacionService;

    @Test
    @Transactional
    void testDetallePagoEntityAttributes() {

        String rut = "20623522";
        String nombre = "John";
        String apellido = "Doe";

        //ProfesorEntity profesor = profesorService.guardarProfesor(rut, nombre, apellido);


        String tipoPago = "cuotas";
        int cuotasPactadas = 5;
        //VehiculoEntity vehiculo = vehiculoService.guardarVehiculo("Proyector 1", "Epson");

        System.out.println(vehiculo.getId());

        ReparacionEntity reparacion = new ReparacionEntity();

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


        List<ReparacionEntity> reparacionEntities =  reparacionService.guardarDetallesPagos(vehiculo.getId(), cuotasPactadas,vehiculo.getMontoTotal()); //hay que guardarlo en el repo para ir a buscarlo y para eso necesitamos historial arancel
        ReparacionEntity reparacion1 = reparacionService.guardarDetallePago(vehiculo.getId(), 6,vehiculo.getMontoTotal(),6);
        //reparacionService.obtenerPorVehiculoID(vehiculo.getId());




    }



        @Test
        public void testCalcularMesesAtraso() {
            // Fecha de pago actual y fecha de vencimiento
            LocalDate pagoActual = LocalDate.of(2023, 7, 15);
            LocalDate fechaVencimiento = LocalDate.of(2023, 5, 1);

            // Prueba con las fechas anteriores
            int resultado = reparacionService.calcularAtrazo(pagoActual, fechaVencimiento);
            assertEquals(2, resultado);

            // Prueba con las fechas iguales
            fechaVencimiento = LocalDate.of(2023, 7, 15);
            resultado = reparacionService.calcularMesesAtraso(pagoActual, fechaVencimiento);
            assertEquals(0, resultado);

            // Prueba con las fechas futuras
            fechaVencimiento = LocalDate.of(2023, 9, 1);
            resultado = reparacionService.calcularMesesAtraso(pagoActual, fechaVencimiento);
            assertEquals(-1, resultado);
        }

  
    }


*/