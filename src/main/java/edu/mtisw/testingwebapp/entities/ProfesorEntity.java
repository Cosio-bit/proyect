package edu.mtisw.testingwebapp.entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Table(name = "profesores")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfesorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    String rut;
    String nombre;
    String apellido;
    int infracciones; //inborrable, 3 veces y se bloquea
    int atrasos; //reset cada 1 semana

}
