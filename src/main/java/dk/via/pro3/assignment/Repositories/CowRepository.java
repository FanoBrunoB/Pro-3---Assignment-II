package dk.via.pro3.assignment.Repositories;

import dk.via.pro3.assignment.Entities.Cow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CowRepository extends JpaRepository<Cow, Long> {

    List<Cow> findByArrivalDate(LocalDate arrivingDate);
    List<Cow> findBySupplier_Name(String supplierName);
}
