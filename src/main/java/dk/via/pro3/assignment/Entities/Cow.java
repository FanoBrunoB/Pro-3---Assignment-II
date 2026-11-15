package dk.via.pro3.assignment.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "cow")
@Getter
@Setter
@NoArgsConstructor
public class Cow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double weight;

    @Column(name = "arrival_date", nullable = false)
    private LocalDate arrivalDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "supplier_name", referencedColumnName = "name")
    private Supplier supplier;

    public Cow(LocalDate arrivalDate, double weight, Supplier supplier) {
        this.arrivalDate = arrivalDate;
        this.weight = weight;
        this.supplier = supplier;
    }
}
