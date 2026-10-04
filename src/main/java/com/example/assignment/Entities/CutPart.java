package com.example.assignment.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "cut_part")
public class CutPart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "part_type")
    private PartType partType;

    @Column(nullable = false)
    private double weight;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cow")
    private Cow cow;

    public CutPart(){
    }

    public CutPart(PartType partType, double weight, Cow cow) {
        this.partType = partType;
        this.weight = weight;
        this.cow = cow;
    }

    public Long getId() {
        return id;
    }

    public PartType getPartType() {
        return partType;
    }

    public void setPartType(PartType partType) {
        this.partType = partType;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Cow getCow() {
        return cow;
    }

    public void setCow(Cow cow) {
        this.cow = cow;
    }
}
