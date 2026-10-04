package com.example.assignment.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "supplier")
public class Supplier {

    @Id
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    public Supplier() {
    }

    public Supplier(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
