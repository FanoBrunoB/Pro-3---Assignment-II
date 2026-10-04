package com.example.assignment;

import com.example.assignment.grpc.AnimalRequest;
import com.example.assignment.grpc.AnimalsResponse;
import com.example.assignment.grpc.ProductRequest;
import com.example.assignment.grpc.ProductSummary;
import com.example.assignment.grpc.ProductsResponse;
import com.example.assignment.grpc.TraceabilityServiceGrpc;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.grpc.test.autoconfigure.AutoConfigureTestGrpcTransport;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.grpc.client.ImportGrpcClients;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestGrpcTransport
@ImportGrpcClients(types = TraceabilityServiceGrpc.TraceabilityServiceBlockingStub.class)
class TraceabilityGrpcTest {

    @Autowired
    private TraceabilityServiceGrpc.TraceabilityServiceBlockingStub stub;

    //GetAnimalsByProduct

    @Test
    void getAnimalsByProduct_returnsRegistrationNumbers() {
        assertEquals(List.of(1L, 2L, 3L), animalsOf(1L));
        assertEquals(List.of(1L, 2L), animalsOf(2L));
        assertEquals(List.of(3L, 4L), animalsOf(3L));
        assertEquals(List.of(4L), animalsOf(4L));
    }

    @Test
    void getAnimalsByProduct_unknownProduct_returnsNotFound() {
        StatusRuntimeException e = assertThrows(StatusRuntimeException.class, () -> animalsOf(999L));
        assertEquals(Status.Code.NOT_FOUND, e.getStatus().getCode());
    }

    @Test
    void getAnimalsByProduct_invalidId_returnsInvalidArgument() {
        StatusRuntimeException zero = assertThrows(StatusRuntimeException.class, () -> animalsOf(0L));
        assertEquals(Status.Code.INVALID_ARGUMENT, zero.getStatus().getCode());

        StatusRuntimeException negative = assertThrows(StatusRuntimeException.class, () -> animalsOf(-1L));
        assertEquals(Status.Code.INVALID_ARGUMENT, negative.getStatus().getCode());
    }

    //GetProductsByAnimal

    @Test
    void getProductsByAnimal_returnsProducts() {
        List<ProductSummary> products = productsOf(1L);

        assertEquals(List.of(1L, 2L), products.stream().map(ProductSummary::getId).toList());
        assertTrue(products.get(0).getHalf());
        assertFalse(products.get(1).getHalf());

        assertEquals(List.of(3L, 4L), productIdsOf(4L));
    }

    @Test
    void getProductsByAnimal_animalWithoutProducts_returnsEmptyList() {
        assertTrue(productsOf(5L).isEmpty());
    }

    @Test
    void getProductsByAnimal_unknownAnimal_returnsNotFound() {
        StatusRuntimeException e = assertThrows(StatusRuntimeException.class, () -> productsOf(999L));
        assertEquals(Status.Code.NOT_FOUND, e.getStatus().getCode());
    }

    @Test
    void getProductsByAnimal_invalidId_returnsInvalidArgument() {
        StatusRuntimeException e = assertThrows(StatusRuntimeException.class, () -> productsOf(0L));
        assertEquals(Status.Code.INVALID_ARGUMENT, e.getStatus().getCode());
    }

    //helpers

    private List<Long> animalsOf(long productId) {
        AnimalsResponse response = stub.getAnimalsByProduct(
                ProductRequest.newBuilder().setProductId(productId).build());
        return response.getRegistrationNumbersList();
    }

    private List<ProductSummary> productsOf(long animalId) {
        ProductsResponse response = stub.getProductsByAnimal(
                AnimalRequest.newBuilder().setAnimalId(animalId).build());
        return response.getProductsList();
    }

    private List<Long> productIdsOf(long animalId) {
        return productsOf(animalId).stream().map(ProductSummary::getId).toList();
    }
}