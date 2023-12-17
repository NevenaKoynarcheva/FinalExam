package com.finalExam.EmployeeProjects.repository;


import com.finalExam.EmployeeProjects.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
