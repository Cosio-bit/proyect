/*
package edu.mtisw.testingwebapp;

import edu.mtisw.testingwebapp.entities.VehiculoEntity;
import edu.mtisw.testingwebapp.repositories.VehiculoRepository;
import edu.mtisw.testingwebapp.services.VehiculoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class testVehiculo {

    @Autowired
    private VehiculoRepository vehiculoRepository;
    @Autowired
    private VehiculoService vehiculoService;

    @Test
    @Transactional
    void testVehiculoEntityAttributes() {

        String patente = "Proyector 1";
        String tipo = "Proyector de datos";

        //VehiculoEntity projectEnt = new VehiculoEntity(null, nombre, tipo,"disponible");

        VehiculoEntity projectServ = vehiculoService.guardarVehiculo(patente, tipo);

        Optional<VehiculoEntity> projectRep = vehiculoRepository.findByPatente(patente);

        assertEquals(nombre, projectServ.getNombre());
        assertEquals(tipo, projectServ.getTipo());
        //assertEquals("disponible", vehiculo.getEstado());
        assertEquals("Proyector 1", projectRep.getNombre());
    }




}
*/