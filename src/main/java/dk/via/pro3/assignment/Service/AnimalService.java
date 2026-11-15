package dk.via.pro3.assignment.Service;

import dk.via.pro3.assignment.Entities.Cow;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AnimalService {

    List<Cow> getAllCows();
    Cow getCowById(Long id);
    Cow addCow(Cow cow);
    Cow updateCow(Cow cow);
    void deleteCow(Long id);
    List<Cow> getCowsByArrivingDate(LocalDate arrivingDate);
    List<Cow> getCowsBySupplierName(String supplierName);
}
