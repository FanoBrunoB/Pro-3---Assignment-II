package com.example.assignment.Service;

import com.example.assignment.Repositories.TraceabilityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TraceabilityService {

    private final TraceabilityRepository repository;

    public TraceabilityService(TraceabilityRepository repository) {
        this.repository = repository;
    }

    public List<Long> getRegistrationNumbersByProduct(Long productId) {
        validateId(productId, "productId");

        if (!repository.existsById(productId)) {
            throw new NotFoundException("Product " + productId + " not found");
        }
        return repository.findCowIdsByProductId(productId);
    }

    public List<ProductInfo> getProductsByAnimal(Long animalId) {
        validateId(animalId, "animalId");

        if (!repository.cowExists(animalId)) {
            throw new NotFoundException("Animal " + animalId + " not found");
        }
        return repository.findProductsByCowId(animalId).stream()
                .map(p -> new ProductInfo(p.getId(), p.isHalf()))
                .toList();
    }

    private void validateId(Long id, String name) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException(name + " must be a positive number");
        }
    }
}
