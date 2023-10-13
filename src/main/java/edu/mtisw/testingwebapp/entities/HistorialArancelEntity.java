package edu.mtisw.testingwebapp.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "historial_arancels")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistorialArancelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    private double montoTotal;
    private String tipoPago;
    private int cuotasPactadas;
    private int cuotasPagadas;
    private double totalPagado;
    private LocalDate ultimoPago;
    private double saldoPorPagar;
    private int cuotasRetraso;

    private int castigoInteres=0;

    private Long estudianteID;
/*
    @ElementCollection // Esta anotación indica que notas es una colección de elementos
    private List<Long> detallePagosID; // Cambia ArrayList<int> a List<Integer>*/

    /*
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "historialArancel")
    private List<DetallePagoEntity> detallePagos;*/



/*
    @OneToOne(cascade = CascadeType.ALL, optional = true)
    private EstudianteEntity estudiante;*/

}

    /* all the report must have the following
    private String rut;
    private String nombre;
    private int nroExamenes;
    private double promedioExamenes;
    private double montoTotal;
    private String tipoPago;
    private int cuotasPactadas;
    private int cuotasPagadas;
    private double totalPagado;
    private LocalDate ultimoPago;
    private double saldoPorPagar;
    private int cuotasRetraso;*/
