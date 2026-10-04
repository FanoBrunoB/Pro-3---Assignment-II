package com.example.assignment.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tray")
public class Tray {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double maxWeight;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cut_part")
    private CutPart cutPart;

    public Tray(){
    }

    public Tray(double maxWeight, CutPart cutPart) {
        this.maxWeight = maxWeight;
        this.cutPart = cutPart;
    }

    public Long getId() {
        return id;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(double maxWeight) {
        this.maxWeight = maxWeight;
    }

    public CutPart getCutPart() {
        return cutPart;
    }

    public void setCutPart(CutPart cutPart) {
        this.cutPart = cutPart;
    }
}
