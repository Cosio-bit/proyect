package edu.mtisw.testingwebapp.repositories;

import edu.mtisw.testingwebapp.entities.ProjectorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ProjectorRepository extends JpaRepository<ProjectorEntity, Long> {
    //all queries
    @Query(value = "SELECT * FROM projectores WHERE id = :id", nativeQuery = true)
    Optional<ProjectorEntity> findById(@Param("id") Long id);

    @Query(value = "SELECT * FROM projectores WHERE nombre = :nombre", nativeQuery = true)
    Optional<ProjectorEntity> findByNombre(@Param("nombre") String nombre);

    @Query(value = "SELECT * FROM projectores WHERE tipo = :tipo", nativeQuery = true)
    Optional<ProjectorEntity> findByTipo(@Param("tipo") String tipo);

    @Query(value = "SELECT * FROM projectores WHERE estado = :estado", nativeQuery = true)
    Optional<ProjectorEntity> findByEstado(@Param("estado") String estado);

    //find all projectores
    @Query(value = "SELECT * FROM projectores", nativeQuery = true)
    ProjectorEntity findAllProjectores();
    

}
