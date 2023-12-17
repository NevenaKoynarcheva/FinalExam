package com.finalExam.EmployeeProjects.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
@Entity
@Table(name = "employee")

public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private int idSystem;
    @NotBlank
    private LocalDate startDate;
    @NotBlank
    private LocalDate endDate;
    @NotBlank
    private int projectId;


    public Employee(int idSystem, int projectId, LocalDate startDate, LocalDate endDate ) {
        this.idSystem = idSystem;
        this.startDate = startDate;
        this.endDate = endDate;
        this.projectId = projectId;
    }
    public Employee(){}

    public int getIdSystem() {
        return idSystem;
    }

    public void setIdSystem(int idSystem) {
        this.idSystem = idSystem;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getProjectId() {
        return projectId;
    }
}
