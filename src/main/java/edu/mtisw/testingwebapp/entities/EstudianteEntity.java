package edu.mtisw.testingwebapp.entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "estudiantes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstudianteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    private String rut;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private String tipoColegio;
    private String nombreColegio;
    private LocalDate AnnoEgreso;
    private LocalDate periodoInscripcion;


    /*
    @OneToOne(cascade = CascadeType.ALL, optional = true)
    private HistorialAcademicoEntity historialAcademico;

    @OneToOne(cascade = CascadeType.ALL, optional = true)
    private HistorialArancelEntity historialArancel;*/


}
