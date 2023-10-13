package edu.mtisw.testingwebapp.repositories;

import edu.mtisw.testingwebapp.entities.HistorialAcademicoEntity;
import edu.mtisw.testingwebapp.entities.EstudianteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HistorialAcademicoRepository extends JpaRepository<HistorialAcademicoEntity, Long> {


    // Custom query method to find HistorialAcademico by nroExamenes using named parameters
    @Query("SELECT ha FROM HistorialAcademicoEntity ha WHERE ha.nroExamenes = :nroExamenes")
    HistorialAcademicoEntity findHistorialAcademicoByNroExamenes(@Param("nroExamenes") int nroExamenes);

    // Custom query method to find HistorialAcademico by promedioExamenes using named parameters
    @Query("SELECT ha FROM HistorialAcademicoEntity ha WHERE ha.promedioExamenes = :promedioExamenes")
    HistorialAcademicoEntity findHistorialAcademicoByPromedioExamenes(@Param("promedioExamenes") double promedioExamenes);

    @Query("SELECT ha FROM HistorialAcademicoEntity ha WHERE ha.estudianteID = :estudianteID")
    HistorialAcademicoEntity findHistorialAcademicoByEstudianteID(@Param("estudianteID") Long estudianteID);

    /*
    // Custom query method to get the Estudiante associated with a HistorialAcademico by HistorialAcademico id
    @Query("SELECT ha.estudiante FROM HistorialAcademicoEntity ha WHERE ha.id = :historialAcademicoId")
    Optional<EstudianteEntity> findEstudianteByHistorialAcademicoId(@Param("historialAcademicoId") Long historialAcademicoId);

    // You can add more custom query methods as needed*/

}
