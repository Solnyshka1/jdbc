package com.example.jdbc;

public class Contractor extends Employee {
    private final double hourlyRate;
    private final int maxHours;

    public Contractor(String name, double hourlyRate, int maxHours) {
        super(name, "Contractor");
        this.hourlyRate = hourlyRate;
        this.maxHours = maxHours;
    }

    @Override
    public double calculateSalary() {
        return hourlyRate * maxHours;
    }
}

