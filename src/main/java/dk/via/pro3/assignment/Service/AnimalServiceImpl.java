package dk.via.pro3.assignment.Service;

import dk.via.pro3.assignment.Entities.Cow;
import dk.via.pro3.assignment.Entities.Supplier;
import dk.via.pro3.assignment.Repositories.CowRepository;
import dk.via.pro3.assignment.Repositories.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimalServiceImpl implements AnimalService{

    private final CowRepository cowRepository;
    private final SupplierRepository supplierRepository;

    @Override
    public List<Cow> getAllCows() {
        return cowRepository.findAll();
    }

    @Override
    public Cow getCowById(Long id) {
        return cowRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cow not found"));
    }

    @Override
    public Cow addCow(Cow cow) {
        Supplier supplier = supplierRepository.findById(cow.getSupplier().getName())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Origin '" + cow.getSupplier().getName() + "' does not exist"));
        cow.setSupplier(supplier);
        return cowRepository.save(cow);
    }

    @Override
    public Cow updateCow(Cow cow) {
        Cow outdatedCow = getCowById(cow.getId());
        outdatedCow.setArrivalDate(cow.getArrivalDate());
        outdatedCow.setSupplier(cow.getSupplier());
        outdatedCow.setWeight(cow.getWeight());
        return cowRepository.save(outdatedCow);
    }

    @Override
    public void deleteCow(Long id) {
        cowRepository.deleteById(id);
    }

    @Override
    public List<Cow> getCowsByArrivingDate(LocalDate arrivingDate) {
        return cowRepository.findByArrivalDate(arrivingDate);
    }

    @Override
    public List<Cow> getCowsBySupplierName(String supplierName) {
        return cowRepository.findBySupplier_Name(supplierName);
    }
}
