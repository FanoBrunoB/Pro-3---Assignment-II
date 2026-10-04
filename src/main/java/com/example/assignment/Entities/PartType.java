package com.example.assignment.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "part_type")
public class PartType {

    @Id
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    public PartType(){
    }

    public PartType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
