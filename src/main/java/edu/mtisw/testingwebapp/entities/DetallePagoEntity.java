package edu.mtisw.testingwebapp.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "detalle_pagos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetallePagoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    private LocalDate fechaPago;
    private LocalDate fechaVencimiento;
    private double montoPago;
    //pagado true, no pagado false
    private boolean pagado;

    private Long historialArancelID;

    /*
    @ManyToOne
    @JoinColumn(name = "historial_arancel")
    private HistorialArancelEntity historialArancel;*/

}
