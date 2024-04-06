package edu.mtisw.testingwebapp.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reparaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReparacionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private String idVehiculo;

    @Column(nullable = false)
    private LocalDateTime fechaHoraIngreso;

    @Column(nullable = false)
    private String tipoReparacion;

    @Column(nullable = true)
    private Integer montoTotal;

    @Column(nullable = true) // Assuming this field can be null
    private LocalDateTime fechaHoraSalida;

    @Column(nullable = true) // Assuming this field can be null
    private LocalDateTime fechaHoraRetiro;


}
