package edu.mtisw.testingwebapp.repositories;

import edu.mtisw.testingwebapp.entities.EstudianteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EstudianteRepository extends JpaRepository<EstudianteEntity, Long> {
    @Query("select e from EstudianteEntity e where e.rut = :rut")
    EstudianteEntity findByRut(@Param("rut")String rut);

    @Query("SELECT e.rut FROM EstudianteEntity e")
    List<String> findAllRuts();

    // Custom query method to find Estudiante by nombre using named parameters
    @Query("SELECT e FROM EstudianteEntity e WHERE e.nombre = :nombre")
    EstudianteEntity findEstudianteByNombre(@Param("nombre") String nombre);

    // Custom query method to find Estudiante by apellido using named parameters
    @Query("SELECT e FROM EstudianteEntity e WHERE e.apellido = :apellido")
    EstudianteEntity findEstudianteByApellido(@Param("apellido") String apellido);

    // Custom query method to find Estudiante by fechaNacimiento using named parameters
    @Query("SELECT e FROM EstudianteEntity e WHERE e.fechaNacimiento = :fechaNacimiento")
    EstudianteEntity findEstudiantesByFechaNacimiento(@Param("fechaNacimiento") LocalDate fechaNacimiento);

    // Custom query method to find Estudiante by tipoColegio using named parameters
    @Query("SELECT e FROM EstudianteEntity e WHERE e.tipoColegio = :tipoColegio")
    EstudianteEntity findEstudiantesByTipoColegio(@Param("tipoColegio") String tipoColegio);

    // Custom query method to find Estudiante by nombreColegio using named parameters
    @Query("SELECT e FROM EstudianteEntity e WHERE e.nombreColegio = :nombreColegio")
    EstudianteEntity findEstudiantesByNombreColegio(@Param("nombreColegio") String nombreColegio);

    // Custom query method to find Estudiante by AnnoEgreso using named parameters
    @Query("SELECT e FROM EstudianteEntity e WHERE e.AnnoEgreso = :AnnoEgreso")
    EstudianteEntity findEstudiantesByAnnoEgreso(@Param("AnnoEgreso") LocalDate AnnoEgreso);

    @Query("SELECT e FROM EstudianteEntity e WHERE e.periodoInscripcion = :periodoInscripcion")
    EstudianteEntity findEstudiantesByPeriodoInscripcion(@Param("periodoInscripcion") LocalDate periodoInscripcion);


}
