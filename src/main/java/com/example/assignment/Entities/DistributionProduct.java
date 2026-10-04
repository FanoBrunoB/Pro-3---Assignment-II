package com.example.assignment.Entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "distribution_product")
public class DistributionProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private boolean isHalf;

    @ManyToMany
    @JoinTable(
            name = "distribution_product_cut_part",
            joinColumns = @JoinColumn(name = "distribution_product_id"),
            inverseJoinColumns = @JoinColumn(name = "cut_part_id")
    )
    private Set<CutPart> compositionCutParts = new HashSet<>();

    public DistributionProduct(){
    }

    public DistributionProduct(boolean isHalf, Set<CutPart> compositionCutParts) {
        this.isHalf = isHalf;
        this.compositionCutParts = compositionCutParts;
    }

    public Long getId() {
        return id;
    }

    public boolean isHalf() {
        return isHalf;
    }

    public void setHalf(boolean half) {
        isHalf = half;
    }

    public Set<CutPart> getCompositionCutParts() {
        return compositionCutParts;
    }

    public void setCompositionCutParts(Set<CutPart> compositionCutParts) {
        this.compositionCutParts = compositionCutParts;
    }
}
