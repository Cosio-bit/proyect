package edu.mtisw.testingwebapp.entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "reparaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrestamoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    String fechaPrestamo;
    String horaPrestamo;
    String utilizacionHoras;
    String fechaDevolucion;
    String horaDevolucion;
    String estadoDanado;
    String uso;

    String idProjector;
    String idProfesor;

}
