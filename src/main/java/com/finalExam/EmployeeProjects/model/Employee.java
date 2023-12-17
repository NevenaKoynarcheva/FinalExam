package com.finalExam.EmployeeProjects.model;

import java.time.LocalDate;

public class Employee {
    private int id;
    private LocalDate startDate;
    private LocalDate endDate;
    private int projectId;


    public Employee(int id, int projectId, LocalDate startDate, LocalDate endDate ) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.projectId = projectId;
    }

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
