package edu.mtisw.testingwebapp.repositories;

import edu.mtisw.testingwebapp.entities.ProyectorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ProyectorRepository extends JpaRepository<ProyectorEntity, Long> {

}
