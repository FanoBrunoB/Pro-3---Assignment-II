package com.example.assignment;

import com.example.assignment.Entities.DistributionProduct;
import com.example.assignment.Repositories.TraceabilityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TraceabilityRepositoryTest {

    @Autowired
    private TraceabilityRepository repository;

    @Test
    void cowIdsByProduct() {
        assertEquals(List.of(1L, 2L, 3L), repository.findCowIdsByProductId(1L));
        assertEquals(List.of(1L, 2L), repository.findCowIdsByProductId(2L));
        assertEquals(List.of(3L, 4L), repository.findCowIdsByProductId(3L));
        assertEquals(List.of(4L), repository.findCowIdsByProductId(4L));
    }

    @Test
    void cowIdsByProductThatDoesNotExistIsEmpty() {
        assertTrue(repository.findCowIdsByProductId(999L).isEmpty());
    }

    @Test
    void productsByCow() {
        assertEquals(List.of(1L, 2L), productIds(repository.findProductsByCowId(1L)));
        assertEquals(List.of(1L, 3L), productIds(repository.findProductsByCowId(3L)));
        assertEquals(List.of(3L, 4L), productIds(repository.findProductsByCowId(4L)));
    }

    @Test
    void cowWithoutProductsIsEmpty() {
        assertTrue(repository.findProductsByCowId(5L).isEmpty());
    }

    @Test
    void existenceChecks() {
        assertTrue(repository.cowExists(5L));
        assertFalse(repository.cowExists(999L));
        assertTrue(repository.existsById(1L));
        assertFalse(repository.existsById(999L));
    }

    private List<Long> productIds(List<DistributionProduct> products) {
        return products.stream().map(DistributionProduct::getId).toList();
    }
}
