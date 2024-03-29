package edu.mtisw.testingwebapp.entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Table(name = "projectores")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    private String nombre;
    private String tipo;
    private String estado;

    private String patente;
    private String marca;
    private String modelo;
    private String annoFabricacion;
    private String tipoMotor;
    private Integer nroAsientos;

}

