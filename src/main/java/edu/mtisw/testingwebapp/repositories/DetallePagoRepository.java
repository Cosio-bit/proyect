package edu.mtisw.testingwebapp.repositories;
import edu.mtisw.testingwebapp.entities.DetallePagoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface DetallePagoRepository extends JpaRepository<DetallePagoEntity, Long> {
    List<DetallePagoEntity> findByFechaPago(LocalDate fechaPago);

    List<DetallePagoEntity> findByFechaVencimiento(LocalDate fechaVencimiento);

    List<DetallePagoEntity> findByPagado(boolean pagado);

    List<DetallePagoEntity> findByPagadoFalse();

    // Custom query method to find DetallePago by fechaPago using named parameters
    @Query("SELECT dp FROM DetallePagoEntity dp WHERE dp.fechaPago = :fechaPago")
    List<DetallePagoEntity> findDetallePagosByFechaPago(@Param("fechaPago") LocalDate fechaPago);

    // Custom query method to find DetallePago by fechaVencimiento using named parameters
    @Query("SELECT dp FROM DetallePagoEntity dp WHERE dp.fechaVencimiento = :fechaVencimiento")
    List<DetallePagoEntity> findDetallePagosByFechaVencimiento(@Param("fechaVencimiento") LocalDate fechaVencimiento);

    // Custom query method to find DetallePago by pagado (true or false) using named parameters
    @Query("SELECT dp FROM DetallePagoEntity dp WHERE dp.pagado = :pagado")
    List<DetallePagoEntity> findDetallePagosByPagado(@Param("pagado") boolean pagado);

    // Custom query method to find DetallePago by not pagado (false) using named parameters
    @Query("SELECT dp FROM DetallePagoEntity dp WHERE dp.pagado = false")
    List<DetallePagoEntity> findDetallePagosByNotPagado();

    @Query("SELECT dp FROM DetallePagoEntity dp WHERE dp.historialArancelID = :historialArancelID")
    List<DetallePagoEntity> findByHistorialArancelID(@Param("historialArancelID") Long historialArancelID);

    @Query("SELECT dp FROM DetallePagoEntity dp WHERE dp.pagado = false AND dp.historialArancelID = :historialArancelID")
    List<DetallePagoEntity> findDetallePagosByNotPagadoAndHistorialArancelID(@Param("historialArancelID") Long historialArancelID);


}
