package dk.via.pro3.assignment.DTOs;

import dk.via.pro3.assignment.Entities.Cow;

import java.time.LocalDate;

public record AnimalResponse(
        Long registrationNumber,
        double weight,
        LocalDate arrivalDate,
        String origin
) {
    public static AnimalResponse from(Cow cow) {
        return new AnimalResponse(
                cow.getId(),
                cow.getWeight(),
                cow.getArrivalDate(),
                cow.getSupplier().getName()
        );
    }
}