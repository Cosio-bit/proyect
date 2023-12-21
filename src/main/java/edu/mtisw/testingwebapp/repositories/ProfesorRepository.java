package edu.mtisw.testingwebapp.repositories;

import edu.mtisw.testingwebapp.entities.ProfesorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProfesorRepository extends JpaRepository<ProfesorEntity, Long> {
    //all queries
    @Query(value = "SELECT * FROM profesores WHERE id = :id", nativeQuery = true)
    Optional<ProfesorEntity> findById(@Param("id") Long id);

    @Query(value = "SELECT * FROM profesores WHERE nombre = :nombre", nativeQuery = true)
    Optional<ProfesorEntity> findByNombre(@Param("nombre") String nombre);

    @Query(value = "SELECT * FROM profesores WHERE apellido = :apellido", nativeQuery = true)
    Optional<ProfesorEntity> findByApellido(@Param("apellido") String apellido);

    @Query(value = "SELECT * FROM profesores WHERE rut = :rut", nativeQuery = true)
    Optional<ProfesorEntity> findByRut(@Param("rut") String rut);

    @Query(value = "SELECT * FROM profesores WHERE infracciones = :infracciones", nativeQuery = true)
    Optional<ProfesorEntity> findByInfracciones(@Param("infracciones") int infracciones);

    @Query(value = "SELECT * FROM profesores WHERE atrasos = :atrasos", nativeQuery = true)
    Optional<ProfesorEntity> findByAtrasos(@Param("atrasos") int atrasos);


    //find all profesores
    @Query(value = "SELECT * FROM profesores", nativeQuery = true)
    List<ProfesorEntity> findAllProfesores();

    ArrayList<String> findAllRuts();


}
