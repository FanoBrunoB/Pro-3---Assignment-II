package dk.via.pro3.assignment.DTOs;

import java.time.LocalDate;

public record AnimalRequest(
        double weight,
        LocalDate arrivalDate,
        String origin
) {
}