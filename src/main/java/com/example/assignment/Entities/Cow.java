package com.example.assignment.Entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "cow")
public class Cow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double weight;

    @Column(name = "arrival_date", nullable = false)
    private LocalDateTime arrivalDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "supplier_name", referencedColumnName = "name")
    private Supplier supplier;

    public Cow(){
    }

    public Cow (LocalDateTime arrivalDate, double weight, Supplier supplier) {
        this.arrivalDate = arrivalDate;
        this.weight = weight;
        this.supplier = supplier;
    }

    public Long getId() {
        return id;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public LocalDateTime getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDateTime arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}
