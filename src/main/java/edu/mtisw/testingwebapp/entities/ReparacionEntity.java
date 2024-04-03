package edu.mtisw.testingwebapp.entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
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

    @Column(nullable = false)
    private String idVehiculo;

    @Column(nullable = false)
    private String fechaIngreso;

    @Column(nullable = false)
    private String horaIngreso;

    @Column(nullable = false)
    private String tipoReparacion;

    @Column(nullable = true)
    private Integer montoTotal;

    @Column(nullable = true) // Assuming this field can be null
    private String fechaSalida;

    @Column(nullable = true) // Assuming this field can be null
    private String horaSalida;

    @Column(nullable = true) // Assuming this field can be null
    private String fechaRetiro;

    @Column(nullable = true) // Assuming this field can be null
    private String horaRetiro;
}
