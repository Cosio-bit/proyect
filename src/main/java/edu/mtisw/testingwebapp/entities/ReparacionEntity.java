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

    String idVehiculo;

    String fechaIngreso;
    String horaIngeso;
    String tipoReparacion;
    Integer montoTotal;
    String fechaSalidaReparacion;
    String horaSalidaReparacion;
    String fechaSalidaCliente;
    String horaSalidaCliente;





}
