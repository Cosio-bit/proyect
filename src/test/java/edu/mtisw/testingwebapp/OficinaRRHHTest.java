package edu.mtisw.testingwebapp;

import edu.mtisw.testingwebapp.entities.*;
import edu.mtisw.testingwebapp.services.OficinaRRHH;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static edu.mtisw.testingwebapp.services.OficinaRRHH.convertirFecha;
import static edu.mtisw.testingwebapp.services.OficinaRRHH.stringToList;
import static org.junit.jupiter.api.Assertions.assertEquals;

class OficinaRRHHTest {
    OficinaRRHH oficinaRRHH = new OficinaRRHH();
    EstudianteEntity estudiante = new EstudianteEntity();
    HistorialAcademicoEntity historialAcademico = new HistorialAcademicoEntity();
    HistorialArancelEntity historialArancel = new HistorialArancelEntity();
    DetallePagoEntity detallePago = new DetallePagoEntity();
    List<DetallePagoEntity> detallePagos = new ArrayList<>();

    @Test
    void testValidDateConversion() {
        // Input date string in the correct format
        String validDateStr = "2023/10/13";
        LocalDate result = convertirFecha(validDateStr);
        LocalDate expected = LocalDate.of(2023, 10, 13);
        assertEquals(expected, result);
    }
    @Test
    void calcArancelTipoColegio(){
        estudiante.setRut("12.345.678-2");
        estudiante.setNombre("Raul");
        estudiante.setTipoColegio("subencionado");
        historialAcademico.setEstudianteID(estudiante.getId());
        historialArancel.setEstudianteID(estudiante.getId());
        historialArancel.setMontoTotal(1500000);

        double arancelTipoColegio = oficinaRRHH.calcularArancelTipoColegio(estudiante.getTipoColegio(),historialArancel.getMontoTotal() );
        assertEquals(1350000, arancelTipoColegio, 0.0);
    }
    @Test
    void calcAnnosDesdeEgreso(){
        estudiante.setRut("12.345.678-2");
        estudiante.setNombre("Raul");
        estudiante.setAnnoEgreso(LocalDate.of(2020,1,1));
        int annos = oficinaRRHH.calcularAnnoEgreso(estudiante);
        assertEquals(3,annos, 0.0);

    }
    @Test
    void calcArancelAnnoEgreso(){
        estudiante.setRut("12.345.678-2");
        estudiante.setNombre("Raul");
        estudiante.setTipoColegio("subencionado");
        estudiante.setAnnoEgreso(LocalDate.of(2020,1,1));
        historialAcademico.setEstudianteID(estudiante.getId());
        historialArancel.setEstudianteID(estudiante.getId());
        historialArancel.setMontoTotal(1500000);

        double arancelAnnos = oficinaRRHH.calcularArancelAnnoEgreso(oficinaRRHH.calcularAnnoEgreso(estudiante.getAnnoEgreso()), historialArancel.getMontoTotal());

        assertEquals(1440000,arancelAnnos, 0.0);

    }
    @Test
    void calcMaxCuotas(){
        estudiante.setRut("12.345.678-2");
        estudiante.setNombre("Raul");
        estudiante.setTipoColegio("subencionado");

        double nroCuotas = oficinaRRHH.maxcuotas(estudiante);
        double nroC = oficinaRRHH.maxcuotas(estudiante.getTipoColegio());
        assertEquals(7, nroC, 0.0);
        assertEquals(7, nroCuotas, 0.0);

    }
    @Test
    void calcPromedio(){
        estudiante.setRut("12.345.678-2");
        estudiante.setNombre("Raul");
        estudiante.setTipoColegio("subencionado");
        historialAcademico.setEstudianteID(estudiante.getId());
        historialArancel.setEstudianteID(estudiante.getId());
        historialArancel.setMontoTotal(1500000);

        String nota= "850,920,780,880,950";

        historialAcademico.setNotas(stringToList(nota));
        double promedio = oficinaRRHH.calcularPromedio(nota);

        historialAcademico.setPromedioExamenes(promedio);
        assertEquals(876, promedio, 0.0);

    }
    @Test
    void calcArancelNotas(){
        estudiante.setRut("12.345.678-2");
        estudiante.setNombre("Raul");
        estudiante.setTipoColegio("subencionado");
        historialAcademico.setEstudianteID(estudiante.getId());
        historialArancel.setEstudianteID(estudiante.getId());
        historialArancel.setMontoTotal(1500000);

        List<Integer> notas = new ArrayList<>();
        // Agregar elementos a la lista
        notas.add(850);
        notas.add(920);
        notas.add(780);
        notas.add(880);
        notas.add(950);
        String nota= "850,920,780,880,950";
        historialAcademico.setNotas(notas);
        double promedio = oficinaRRHH.calcularPromedio(nota);
        historialAcademico.setPromedioExamenes(promedio);
        assertEquals(876, promedio, 0.0);

        historialArancel.setMontoTotal(1500000);
        double arancelPromedio = oficinaRRHH.calcularArancelNotas(promedio, historialArancel.getMontoTotal());
        assertEquals(1470000, arancelPromedio, 0.0);

    }
    @Test
    void calcMesesAtraso(){
        estudiante.setRut("12.345.678-2");
        estudiante.setNombre("Raul");


        detallePago.setFechaPago(LocalDate.of(2020, 3, 1));
        detallePago.setMontoPago(20000);
        // Crear otro objeto LocalDate para la fecha de vencimiento si es diferente
        detallePago.setFechaVencimiento(LocalDate.of(2020, 1, 1));
        detallePago.setPagado(false);
        detallePagos.add(detallePago);

        historialArancel.setUltimoPago(detallePago.getFechaPago()); // Usar la fechaPago creada anteriormente

        double atraso= oficinaRRHH.calcularMesesAtraso(detallePago.getFechaPago(), detallePago.getFechaVencimiento());
        assertEquals(2, atraso, 0.0);


    }

    /*
    @Test
    void calcArancelInteres(){
        estudiante.setRut("12.345.678-2");
        estudiante.setNombre("Raul");
        historialArancel.setMontoTotal(1500000);

        detallePago.setFechaPago(LocalDate.of(2020, 3, 1));
        detallePago.setMontoPago(20000);
        // Crear otro objeto LocalDate para la fecha de vencimiento si es diferente
        detallePago.setFechaVencimiento(LocalDate.of(2020, 1, 1));
        detallePago.setPagado(false);
        detallePagos.add(detallePago);

        historialArancel.setUltimoPago(detallePago.getFechaPago()); // Usar la fechaPago creada anteriormente
        detallePago.setHistorialArancelID(historialArancel.getId());

        double atraso= oficinaRRHH.calcularMesesAtraso(detallePago.getFechaPago(), detallePago.getFechaVencimiento());

        double atrasoArancel = oficinaRRHH.calcularArancelInteres(detallePago.getFechaPago(), detallePago.getFechaVencimiento(), 15000000)
        assertEquals(1590000, atrasoArancel, 0.0);

    }*/
    @Test
    void genArancel() {
        estudiante.setRut("12.345.678-2");
        estudiante.setNombre("Raul");
        estudiante.setTipoColegio("subencionado");



        estudiante.setAnnoEgreso(LocalDate.of(2020, 1, 1));
        String annioEgreso = "2020/02/02";
        historialArancel.setMontoTotal(1500000);

        List<Integer> notas = new ArrayList<>();
        notas.add(850);
        notas.add(920);
        notas.add(780);
        notas.add(880);
        notas.add(950);
        String nota= "850,920,780,880,950";
        historialAcademico.setNotas(notas);
        double promedio = oficinaRRHH.calcularPromedio(nota);
        historialAcademico.setPromedioExamenes(promedio);


        detallePago.setFechaPago(LocalDate.of(2020, 3, 1));
        detallePago.setMontoPago(20000);
        detallePago.setFechaVencimiento(LocalDate.of(2020, 1, 1));
        detallePago.setPagado(false);
        detallePagos.add(detallePago);
        detallePago.setHistorialArancelID(historialArancel.getId());

        historialArancel.setUltimoPago(detallePago.getFechaPago());


        int atraso= (int) oficinaRRHH.calcularMesesAtraso(detallePago.getFechaPago(), detallePago.getFechaVencimiento());
        historialArancel.setCastigoInteres(atraso);
        double descuentos = oficinaRRHH.calcularDescuentos( annioEgreso, estudiante.getTipoColegio(), detallePago.getMontoPago());


        assertEquals(1296000, descuentos, 0.0);}
/*
    @Test
    void exelImport(){
        List<List<String>> examenEstudiantes = oficinaRRHH.ExcelImporterToList("examen");
        List<String> examenEstudiante = examenEstudiantes.get(0);
        double puntaje = Double.parseDouble(examenEstudiante.get(0)); // Realizamos el casting de String a double
        assertEquals(20623522, puntaje, 0);
    }*/
}