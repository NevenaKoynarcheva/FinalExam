package com.finalExam.EmployeeProjects.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
@Entity
@Table(name = "employee")

public class Employee {
    @NotBlank
    private int id;
    @NotBlank
    private LocalDate startDate;
    @NotBlank
    private LocalDate endDate;
    @NotBlank
    private int projectId;


    public Employee(int id, int projectId, LocalDate startDate, LocalDate endDate ) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.projectId = projectId;
    }
    public Employee(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
