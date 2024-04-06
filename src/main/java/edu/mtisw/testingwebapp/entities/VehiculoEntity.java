package edu.mtisw.testingwebapp.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "vehiculos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehiculoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String patente;
    private String marca;
    private String modelo;
    private String annoFabricacion;
    private String tipoVehiculo;
    private String tipoMotor;
    private Integer nroAsientos;
    private Integer kilometraje;



}

