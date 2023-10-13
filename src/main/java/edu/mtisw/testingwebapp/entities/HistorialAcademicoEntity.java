package edu.mtisw.testingwebapp.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "historial_academicos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistorialAcademicoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    private int nroExamenes;
    private double promedioExamenes;

    @ElementCollection // Esta anotación indica que notas es una colección de elementos
    private List<Integer> notas; // Cambia ArrayList<int> a List<Integer>


    private Long estudianteID;
    /*
    @OneToOne(cascade = CascadeType.ALL, optional = true)
    private EstudianteEntity estudiante;*/



}
