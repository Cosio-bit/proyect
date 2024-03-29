package edu.mtisw.testingwebapp.repositories;
import edu.mtisw.testingwebapp.entities.ReparacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface ReparacionRepository extends JpaRepository<ReparacionEntity, Long> {
    //all queries
    @Query(value = "SELECT * FROM reparacion   os WHERE id = :id", nativeQuery = true)
    Optional<ReparacionEntity> findById(@Param("id") Long id);

    @Query(value = "SELECT * FROM reparaciones WHERE id_vehiculo = :idVehiculo", nativeQuery = true)
    List<ReparacionEntity> findByIdVehiculo(@Param("idVehiculo") String long1);

    @Query(value = "SELECT * FROM reparaciones WHERE fecha_reparacion = :fechaReparacion", nativeQuery = true)
    Optional<ReparacionEntity> findByFechaReparacion(@Param("fechaReparacion") String fechaReparacion);

    @Query(value = "SELECT * FROM reparaciones WHERE hora_reparacion = :horaReparacion", nativeQuery = true)
    Optional<ReparacionEntity> findByHoraReparacion(@Param("horaReparacion") String horaReparacion);

    @Query(value = "SELECT * FROM reparaciones WHERE utilizacion_horas = :utilizacionHoras", nativeQuery = true)
    Optional<ReparacionEntity> findByUtilizacionHoras(@Param("utilizacionHoras") String utilizacionHoras);

    @Query(value = "SELECT * FROM reparaciones WHERE fecha_devolucion = :fechaDevolucion", nativeQuery = true)
    Optional<ReparacionEntity> findByFechaDevolucion(@Param("fechaDevolucion") String fechaDevolucion);

    @Query(value = "SELECT * FROM reparaciones WHERE hora_devolucion = :horaDevolucion", nativeQuery = true)
    Optional<ReparacionEntity> findByHoraDevolucion(@Param("horaDevolucion") String horaDevolucion);

    @Query(value = "SELECT * FROM reparaciones WHERE estado_danado = :estadoDanado", nativeQuery = true)
    Optional<ReparacionEntity> findByEstadoDanado(@Param("estadoDanado") String estadoDanado);

    @Query(value = "SELECT * FROM reparaciones WHERE uso = :uso", nativeQuery = true)
    Optional<ReparacionEntity> findByUso(@Param("uso") String uso);

    @Query(value = "SELECT * FROM reparaciones WHERE id_vehiculo = :idVehiculo AND id_profesor = :idProfesor", nativeQuery = true)
    Optional<ReparacionEntity> findByIdVehiculoAndIdProfesor(@Param("idVehiculo") String idVehiculo, @Param("idProfesor") String idProfesor);


}
