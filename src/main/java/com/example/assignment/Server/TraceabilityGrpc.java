package com.example.assignment.Server;

import com.example.assignment.grpc.AnimalRequest;
import com.example.assignment.grpc.AnimalsResponse;
import com.example.assignment.grpc.ProductRequest;
import com.example.assignment.grpc.ProductSummary;
import com.example.assignment.grpc.ProductsResponse;
import com.example.assignment.grpc.TraceabilityServiceGrpc;
import com.example.assignment.Service.NotFoundException;
import com.example.assignment.Service.ProductInfo;
import com.example.assignment.Service.TraceabilityService;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import org.springframework.grpc.server.service.GrpcService;

import java.util.List;

@GrpcService
public class TraceabilityGrpc extends TraceabilityServiceGrpc.TraceabilityServiceImplBase {

    private final TraceabilityService service;

    public TraceabilityGrpc(TraceabilityService service) {
        this.service = service;
    }

    @Override
    public void getAnimalsByProduct(ProductRequest request, StreamObserver<AnimalsResponse> responseObserver) {
        try {
            List<Long> registrationNumbers = service.getRegistrationNumbersByProduct(request.getProductId());

            responseObserver.onNext(AnimalsResponse.newBuilder()
                    .addAllRegistrationNumbers(registrationNumbers)
                    .build());
            responseObserver.onCompleted();
        } catch (RuntimeException e) {
            responseObserver.onError(toStatus(e));
        }
    }

    @Override
    public void getProductsByAnimal(AnimalRequest request, StreamObserver<ProductsResponse> responseObserver) {
        try {
            List<ProductInfo> products = service.getProductsByAnimal(request.getAnimalId());

            ProductsResponse.Builder response = ProductsResponse.newBuilder();
            for (ProductInfo product : products) {
                response.addProducts(ProductSummary.newBuilder()
                        .setId(product.id())
                        .setHalf(product.half())
                        .build());
            }
            responseObserver.onNext(response.build());
            responseObserver.onCompleted();
        } catch (RuntimeException e) {
            responseObserver.onError(toStatus(e));
        }
    }

    private StatusRuntimeException toStatus(RuntimeException e) {
        if (e instanceof NotFoundException) {
            return Status.NOT_FOUND.withDescription(e.getMessage()).asRuntimeException();
        }
        if (e instanceof IllegalArgumentException) {
            return Status.INVALID_ARGUMENT.withDescription(e.getMessage()).asRuntimeException();
        }
        return Status.INTERNAL.withDescription("Unexpected error").withCause(e).asRuntimeException();
    }
}