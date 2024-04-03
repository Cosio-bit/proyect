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
    @Query(value = "SELECT * FROM reparaciones WHERE id = :id", nativeQuery = true)
    Optional<ReparacionEntity> findById(@Param("id") Long id);

    @Query(value = "SELECT * FROM reparaciones WHERE idVehiculo = :idVehiculo", nativeQuery = true)
    Optional<ReparacionEntity> findByIdVehiculo(@Param("idVehiculo") String idVehiculo);

    @Query(value = "SELECT * FROM reparaciones WHERE fechaIngreso = :fechaIngreso", nativeQuery = true)
    Optional<ReparacionEntity> findByFechaIngreso(@Param("fechaIngreso") String fechaIngreso);

    @Query(value = "SELECT * FROM reparaciones WHERE horaIngreso = :horaIngreso", nativeQuery = true)
    Optional<ReparacionEntity> findByHoraIngreso(@Param("horaIngreso") String horaIngreso);

    @Query(value = "SELECT * FROM reparaciones WHERE tipoReparacion = :tipoReparacion", nativeQuery = true)
    Optional<ReparacionEntity> findByTipoReparacion(@Param("tipoReparacion") String tipoReparacion);

    @Query(value = "SELECT * FROM reparaciones WHERE montoTotal = :montoTotal", nativeQuery = true)
    Optional<ReparacionEntity> findByMontoTotal(@Param("montoTotal") Integer montoTotal);

    @Query(value = "SELECT * FROM reparaciones WHERE fechaSalida = :fechaSalida", nativeQuery = true)
    Optional<ReparacionEntity> findByFechaSalida(@Param("fechaSalida") String fechaSalida);

    @Query(value = "SELECT * FROM reparaciones WHERE horaSalida = :horaSalida", nativeQuery = true)
    Optional<ReparacionEntity> findByHoraSalida(@Param("horaSalida") String horaSalida);

    @Query(value = "SELECT * FROM reparaciones WHERE fechaRetiro = :fechaRetiro", nativeQuery = true)
    Optional<ReparacionEntity> findByFechaRetiro(@Param("fechaRetiro") String fechaRetiro);

    @Query(value = "SELECT * FROM reparaciones WHERE horaRetiro = :horaRetiro", nativeQuery = true)
    Optional<ReparacionEntity> findByHoraRetiro(@Param("horaRetiro") String horaRetiro);

    //find all reparaciones
    @Query(value = "SELECT * FROM reparaciones", nativeQuery = true)
    List<ReparacionEntity> findAllReparaciones();

    @Query(value = "SELECT * FROM reparaciones WHERE idVehiculo = :idVehiculo", nativeQuery = true)
    List<ReparacionEntity> findByVehiculoID(@Param("idVehiculo") String idVehiculo);



}
