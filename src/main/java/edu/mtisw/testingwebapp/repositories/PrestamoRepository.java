package edu.mtisw.testingwebapp.repositories;
import edu.mtisw.testingwebapp.entities.PrestamoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PrestamoRepository extends JpaRepository<PrestamoEntity, Long> {
    //all queries
    @Query(value = "SELECT * FROM prestamos WHERE id = :id", nativeQuery = true)
    Optional<PrestamoEntity> findById(@Param("id") Long id);

    @Query(value = "SELECT * FROM prestamos WHERE fecha_prestamo = :fechaPrestamo", nativeQuery = true)
    Optional<PrestamoEntity> findByFechaPrestamo(@Param("fechaPrestamo") LocalDate fechaPrestamo);

    @Query(value = "SELECT * FROM prestamos WHERE fecha_entrega = :fechaEntrega", nativeQuery = true)
    Optional<PrestamoEntity> findByFechaEntrega(@Param("fechaEntrega") LocalDate fechaEntrega);

    @Query(value = "SELECT * FROM prestamos WHERE fecha_devolucion = :fechaDevolucion", nativeQuery = true)
    Optional<PrestamoEntity> findByFechaDevolucion(@Param("fechaDevolucion") LocalDate fechaDevolucion);

    @Query(value = "SELECT * FROM prestamos WHERE estado = :estado", nativeQuery = true)
    Optional<PrestamoEntity> findByEstado(@Param("estado") String estado);

    @Query(value = "SELECT * FROM prestamos WHERE projector_id = :projectorID", nativeQuery = true)
    Optional<PrestamoEntity> findByProjectorID(@Param("projectorID") Long projectorID);

    @Query(value = "SELECT * FROM prestamos WHERE profesor_id = :profesorID", nativeQuery = true)
    Optional<PrestamoEntity> findByProfesorID(@Param("profesorID") Long profesorID);

    //find all prestamos
    @Query(value = "SELECT * FROM prestamos", nativeQuery = true)
    List<PrestamoEntity> findAllPrestamos();

    @Query(value = "SELECT * FROM prestamos WHERE estado = :estado", nativeQuery = true)
    List<PrestamoEntity> findProjectorByNotEntregado(Long projectorID);

    @Query(value = "SELECT * FROM prestamos WHERE projector_id = :projectorID", nativeQuery = true)
    List<PrestamoEntity> findProjectorByProjectorID(Long projectorID);

}
