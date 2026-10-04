package com.example.assignment;

import com.example.assignment.Entities.DistributionProduct;
import com.example.assignment.Repositories.TraceabilityRepository;
import com.example.assignment.Service.NotFoundException;
import com.example.assignment.Service.ProductInfo;
import com.example.assignment.Service.TraceabilityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TraceabilityServiceTest {

    @Mock
    private TraceabilityRepository repository;

    @InjectMocks
    private TraceabilityService service;

    //registration numbers by product

    @Test
    void registrationNumbersByProduct_returnsCowIds() {
        when(repository.existsById(1L)).thenReturn(true);
        when(repository.findCowIdsByProductId(1L)).thenReturn(List.of(1L, 2L, 3L));

        assertEquals(List.of(1L, 2L, 3L), service.getRegistrationNumbersByProduct(1L));
    }

    @Test
    void registrationNumbersByProduct_unknownProduct_throwsNotFound() {
        when(repository.existsById(999L)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> service.getRegistrationNumbersByProduct(999L));
        verify(repository, never()).findCowIdsByProductId(any());
    }

    @Test
    void registrationNumbersByProduct_noAnimals_returnsEmptyList() {
        when(repository.existsById(7L)).thenReturn(true);
        when(repository.findCowIdsByProductId(7L)).thenReturn(List.of());

        assertTrue(service.getRegistrationNumbersByProduct(7L).isEmpty());
    }

    @Test
    void registrationNumbersByProduct_invalidId_throwsIllegalArgument() {
        assertThrows(IllegalArgumentException.class, () -> service.getRegistrationNumbersByProduct(null));
        assertThrows(IllegalArgumentException.class, () -> service.getRegistrationNumbersByProduct(0L));
        assertThrows(IllegalArgumentException.class, () -> service.getRegistrationNumbersByProduct(-5L));
    }

    //products by animal

    @Test
    void productsByAnimal_mapsProducts() {
        DistributionProduct p1 = mock(DistributionProduct.class);
        when(p1.getId()).thenReturn(1L);
        when(p1.isHalf()).thenReturn(true);
        DistributionProduct p2 = mock(DistributionProduct.class);
        when(p2.getId()).thenReturn(2L);
        when(p2.isHalf()).thenReturn(false);

        when(repository.cowExists(1L)).thenReturn(true);
        when(repository.findProductsByCowId(1L)).thenReturn(List.of(p1, p2));

        assertEquals(
                List.of(new ProductInfo(1L, true), new ProductInfo(2L, false)),
                service.getProductsByAnimal(1L));
    }

    @Test
    void productsByAnimal_unknownAnimal_throwsNotFound() {
        when(repository.cowExists(999L)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> service.getProductsByAnimal(999L));
        verify(repository, never()).findProductsByCowId(any());
    }

    @Test
    void productsByAnimal_noProducts_returnsEmptyList() {
        when(repository.cowExists(5L)).thenReturn(true);
        when(repository.findProductsByCowId(5L)).thenReturn(List.of());

        assertTrue(service.getProductsByAnimal(5L).isEmpty());
    }

    @Test
    void productsByAnimal_invalidId_throwsIllegalArgument() {
        assertThrows(IllegalArgumentException.class, () -> service.getProductsByAnimal(null));
        assertThrows(IllegalArgumentException.class, () -> service.getProductsByAnimal(0L));
    }
}
