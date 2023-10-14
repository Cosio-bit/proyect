package edu.mtisw.testingwebapp.repositories;

import edu.mtisw.testingwebapp.entities.DetallePagoEntity;
import edu.mtisw.testingwebapp.entities.HistorialAcademicoEntity;
import edu.mtisw.testingwebapp.entities.HistorialArancelEntity;
import edu.mtisw.testingwebapp.entities.EstudianteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface HistorialArancelRepository extends JpaRepository<HistorialArancelEntity, Long> {
    // Custom query method to find HistorialArancel by montoTotal using named parameters
    @Query("SELECT ha FROM HistorialArancelEntity ha WHERE ha.montoTotal = :montoTotal")
    HistorialArancelEntity findHistorialArancelByMontoTotal(@Param("montoTotal") double montoTotal);

    // Custom query method to find HistorialArancel by tipoPago using named parameters
    @Query("SELECT ha FROM HistorialArancelEntity ha WHERE ha.tipoPago = :tipoPago")
    HistorialArancelEntity findHistorialArancelByTipoPago(@Param("tipoPago") String tipoPago);

    @Query("SELECT ha FROM HistorialArancelEntity ha WHERE ha.estudianteID = :estudianteID")
    Optional<HistorialArancelEntity> findHistorialArancelByEstudianteID(@Param("estudianteID") Long estudianteID);

    // Custom query method to find HistorialArancel by ultimoPago using named parameters
    @Query("SELECT ha FROM HistorialArancelEntity ha WHERE ha.ultimoPago = :ultimoPago")
    HistorialArancelEntity findHistorialArancelByUltimoPago(@Param("ultimoPago") LocalDate ultimoPago);

    // Custom query method to find HistorialArancel by cuotasRetraso using named parameters
    @Query("SELECT ha FROM HistorialArancelEntity ha WHERE ha.cuotasRetraso = :cuotasRetraso")
    HistorialArancelEntity findHistorialArancelByCuotasRetraso(@Param("cuotasRetraso") int cuotasRetraso);

}
