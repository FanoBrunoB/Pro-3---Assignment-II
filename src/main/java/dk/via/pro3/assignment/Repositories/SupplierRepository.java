package dk.via.pro3.assignment.Repositories;

import dk.via.pro3.assignment.Entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, String> {
}
