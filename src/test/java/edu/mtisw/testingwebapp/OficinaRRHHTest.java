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
    ProfesorEntity profesor = new ProfesorEntity();
    HistorialAcademicoEntity historialAcademico = new HistorialAcademicoEntity();
    ProjectorEntity projector = new ProjectorEntity();
    PrestamoEntity prestamo = new PrestamoEntity();
    List<PrestamoEntity> prestamos = new ArrayList<>();

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
        profesor.setRut("12.345.678-2");
        profesor.setNombre("Raul");
        profesor.setTipoColegio("subencionado");
        historialAcademico.setProfesorID(profesor.getId());
        projector.setProfesorID(profesor.getId());
        projector.setMontoTotal(1500000);

        double arancelTipoColegio = oficinaRRHH.calcularArancelTipoColegio(profesor.getTipoColegio(),projector.getMontoTotal() );
        assertEquals(1350000, arancelTipoColegio, 0.0);
    }
    @Test
    void calcAnnosDesdeEgreso(){
        profesor.setRut("12.345.678-2");
        profesor.setNombre("Raul");
        profesor.setAnnoEgreso(LocalDate.of(2020,1,1));
        int annos = oficinaRRHH.calcularAnnoEgreso(profesor);
        assertEquals(3,annos, 0.0);

    }
    @Test
    void calcArancelAnnoEgreso(){
        profesor.setRut("12.345.678-2");
        profesor.setNombre("Raul");
        profesor.setTipoColegio("subencionado");
        profesor.setAnnoEgreso(LocalDate.of(2020,1,1));
        historialAcademico.setProfesorID(profesor.getId());
        projector.setProfesorID(profesor.getId());
        projector.setMontoTotal(1500000);

        double arancelAnnos = oficinaRRHH.calcularArancelAnnoEgreso(oficinaRRHH.calcularAnnoEgreso(profesor.getAnnoEgreso()), projector.getMontoTotal());

        assertEquals(1440000,arancelAnnos, 0.0);

    }
    @Test
    void calcMaxCuotas(){
        profesor.setRut("12.345.678-2");
        profesor.setNombre("Raul");
        profesor.setTipoColegio("subencionado");

        double nroCuotas = oficinaRRHH.maxcuotas(profesor);
        double nroC = oficinaRRHH.maxcuotas(profesor.getTipoColegio());
        assertEquals(7, nroC, 0.0);
        assertEquals(7, nroCuotas, 0.0);

    }
    @Test
    void calcPromedio(){
        profesor.setRut("12.345.678-2");
        profesor.setNombre("Raul");
        profesor.setTipoColegio("subencionado");
        historialAcademico.setProfesorID(profesor.getId());
        projector.setProfesorID(profesor.getId());
        projector.setMontoTotal(1500000);

        String nota= "850,920,780,880,950";

        historialAcademico.setNotas(stringToList(nota));
        double promedio = oficinaRRHH.calcularPromedio(nota);

        historialAcademico.setPromedioExamenes(promedio);
        assertEquals(876, promedio, 0.0);

    }
    @Test
    void calcArancelNotas(){
        profesor.setRut("12.345.678-2");
        profesor.setNombre("Raul");
        profesor.setTipoColegio("subencionado");
        historialAcademico.setProfesorID(profesor.getId());
        projector.setProfesorID(profesor.getId());
        projector.setMontoTotal(1500000);

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

        projector.setMontoTotal(1500000);
        double arancelPromedio = oficinaRRHH.calcularArancelNotas(promedio, projector.getMontoTotal());
        assertEquals(1470000, arancelPromedio, 0.0);

    }
    @Test
    void calcMesesAtraso(){
        profesor.setRut("12.345.678-2");
        profesor.setNombre("Raul");


        prestamo.setFechaPago(LocalDate.of(2020, 3, 1));
        prestamo.setMontoPago(20000);
        // Crear otro objeto LocalDate para la fecha de vencimiento si es diferente
        prestamo.setFechaVencimiento(LocalDate.of(2020, 1, 1));
        prestamo.setPagado(false);
        prestamos.add(prestamo);

        projector.setUltimoPago(prestamo.getFechaPago()); // Usar la fechaPago creada anteriormente

        double atraso= oficinaRRHH.calcularMesesAtraso(prestamo.getFechaPago(), prestamo.getFechaVencimiento());
        assertEquals(2, atraso, 0.0);


    }

    /*
    @Test
    void calcArancelInteres(){
        profesor.setRut("12.345.678-2");
        profesor.setNombre("Raul");
        projector.setMontoTotal(1500000);

        prestamo.setFechaPago(LocalDate.of(2020, 3, 1));
        prestamo.setMontoPago(20000);
        // Crear otro objeto LocalDate para la fecha de vencimiento si es diferente
        prestamo.setFechaVencimiento(LocalDate.of(2020, 1, 1));
        prestamo.setPagado(false);
        prestamos.add(prestamo);

        projector.setUltimoPago(prestamo.getFechaPago()); // Usar la fechaPago creada anteriormente
        prestamo.setProjectorID(projector.getId());

        double atraso= oficinaRRHH.calcularMesesAtraso(prestamo.getFechaPago(), prestamo.getFechaVencimiento());

        double atrasoArancel = oficinaRRHH.calcularArancelInteres(prestamo.getFechaPago(), prestamo.getFechaVencimiento(), 15000000)
        assertEquals(1590000, atrasoArancel, 0.0);

    }*/
    @Test
    void genArancel() {
        profesor.setRut("12.345.678-2");
        profesor.setNombre("Raul");
        profesor.setTipoColegio("subencionado");



        profesor.setAnnoEgreso(LocalDate.of(2020, 1, 1));
        String annioEgreso = "2020/02/02";
        projector.setMontoTotal(1500000);

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


        prestamo.setFechaPago(LocalDate.of(2020, 3, 1));
        prestamo.setMontoPago(20000);
        prestamo.setFechaVencimiento(LocalDate.of(2020, 1, 1));
        prestamo.setPagado(false);
        prestamos.add(prestamo);
        prestamo.setProjectorID(projector.getId());

        projector.setUltimoPago(prestamo.getFechaPago());


        int atraso= (int) oficinaRRHH.calcularMesesAtraso(prestamo.getFechaPago(), prestamo.getFechaVencimiento());
        projector.setCastigoInteres(atraso);
        double descuentos = oficinaRRHH.calcularDescuentos( annioEgreso, profesor.getTipoColegio(), projector.getMontoTotal());


        assertEquals(1296000, descuentos, 0.0);}
/*
    @Test
    void exelImport(){
        List<List<String>> examenProfesors = oficinaRRHH.ExcelImporterToList("examen");
        List<String> examenProfesor = examenProfesors.get(0);
        double puntaje = Double.parseDouble(examenProfesor.get(0)); // Realizamos el casting de String a double
        assertEquals(20623522, puntaje, 0);
    }*/
}