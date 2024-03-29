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
    @Query(value = "SELECT * FROM prestam   os WHERE id = :id", nativeQuery = true)
    Optional<PrestamoEntity> findById(@Param("id") Long id);

    @Query(value = "SELECT * FROM reparaciones WHERE id_projector = :idProjector", nativeQuery = true)
    List<PrestamoEntity> findByIdProjector(@Param("idProjector") String long1);

    @Query(value = "SELECT * FROM reparaciones WHERE id_profesor = :idProfesor", nativeQuery = true)
    Optional<PrestamoEntity> findByIdProfesor(@Param("idProfesor") String long1);

    @Query(value = "SELECT * FROM reparaciones WHERE fecha_prestamo = :fechaPrestamo", nativeQuery = true)
    Optional<PrestamoEntity> findByFechaPrestamo(@Param("fechaPrestamo") String fechaPrestamo);

    @Query(value = "SELECT * FROM reparaciones WHERE hora_prestamo = :horaPrestamo", nativeQuery = true)
    Optional<PrestamoEntity> findByHoraPrestamo(@Param("horaPrestamo") String horaPrestamo);

    @Query(value = "SELECT * FROM reparaciones WHERE utilizacion_horas = :utilizacionHoras", nativeQuery = true)
    Optional<PrestamoEntity> findByUtilizacionHoras(@Param("utilizacionHoras") String utilizacionHoras);

    @Query(value = "SELECT * FROM reparaciones WHERE fecha_devolucion = :fechaDevolucion", nativeQuery = true)
    Optional<PrestamoEntity> findByFechaDevolucion(@Param("fechaDevolucion") String fechaDevolucion);

    @Query(value = "SELECT * FROM reparaciones WHERE hora_devolucion = :horaDevolucion", nativeQuery = true)
    Optional<PrestamoEntity> findByHoraDevolucion(@Param("horaDevolucion") String horaDevolucion);

    @Query(value = "SELECT * FROM reparaciones WHERE estado_danado = :estadoDanado", nativeQuery = true)
    Optional<PrestamoEntity> findByEstadoDanado(@Param("estadoDanado") String estadoDanado);

    @Query(value = "SELECT * FROM reparaciones WHERE uso = :uso", nativeQuery = true)
    Optional<PrestamoEntity> findByUso(@Param("uso") String uso);

    @Query(value = "SELECT * FROM reparaciones WHERE id_projector = :idProjector AND id_profesor = :idProfesor", nativeQuery = true)
    Optional<PrestamoEntity> findByIdProjectorAndIdProfesor(@Param("idProjector") String idProjector, @Param("idProfesor") String idProfesor);


}
