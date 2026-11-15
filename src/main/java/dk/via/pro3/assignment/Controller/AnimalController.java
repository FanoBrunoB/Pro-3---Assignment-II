package dk.via.pro3.assignment.Controller;

import dk.via.pro3.assignment.DTOs.AnimalRequest;
import dk.via.pro3.assignment.DTOs.AnimalResponse;
import dk.via.pro3.assignment.Entities.Cow;
import dk.via.pro3.assignment.Entities.Supplier;
import dk.via.pro3.assignment.Service.AnimalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/animals")
@RequiredArgsConstructor
@Tag(name = "Animals", description = "Registration and lookup of animals arriving at the slaughterhouse")
public class AnimalController {

    private final AnimalService animalService;

    @Operation(summary = "Register an animal",
            description = "Stores weight, arrival date and origin (farm). The registration number is generated automatically.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Animal registered"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<AnimalResponse> registerAnimal(@RequestBody AnimalRequest request) {
        Cow cow = new Cow(request.arrivalDate(), request.weight(), new Supplier(request.origin()));
        Cow saved = animalService.addCow(cow);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(AnimalResponse.from(saved));
    }

    @Operation(summary = "Get an animal by registration number")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Animal found"),
            @ApiResponse(responseCode = "404", description = "Animal not found")
    })
    @GetMapping("/{registrationNumber}")
    public AnimalResponse getAnimal(
            @Parameter(description = "Registration number of the animal", example = "1")
            @PathVariable Long registrationNumber) {
        return AnimalResponse.from(animalService.getCowById(registrationNumber));
    }

    @Operation(summary = "List animals",
            description = "Without parameters returns all animals. Filter by arrival date (yyyy-MM-dd) or by origin (farm name).")
    @ApiResponse(responseCode = "200", description = "List of animals (may be empty)")
    @GetMapping
    public List<AnimalResponse> getAnimals(
            @Parameter(description = "Arrival date", example = "2026-09-01")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @Parameter(description = "Origin (farm name)", example = "Farm Alpha")
            @RequestParam(required = false) String origin) {

        List<Cow> cows;
        if (date != null) {
            cows = animalService.getCowsByArrivingDate(date);
        } else if (origin != null) {
            cows = animalService.getCowsBySupplierName(origin);
        } else {
            cows = animalService.getAllCows();
        }
        return cows.stream().map(AnimalResponse::from).toList();
    }
}