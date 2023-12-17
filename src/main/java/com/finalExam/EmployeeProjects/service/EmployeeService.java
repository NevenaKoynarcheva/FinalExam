package com.finalExam.EmployeeProjects.service;

import com.finalExam.EmployeeProjects.model.Employee;
import com.finalExam.EmployeeProjects.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    public String createEmployee(Employee employee){
        try {
            employeeRepository.save(employee);
        }catch (Exception e){
            return "Wrong information";
        }
        return "Success";
    }

    public List<Employee> findAll(){
        return employeeRepository.findAll();

    }
    public String delete(Long id){
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return "Success";
        }else {
            return "There is no such employee";
        }
    }
    public String editById(Long id, Employee employee){
        if (findEmployee(id)!=null) {
            Employee e = findEmployee(id);
            e.setIdSystem(employee.getIdSystem());
            e.setStartDate(employee.getStartDate());
            e.setEndDate(employee.getEndDate());
            employeeRepository.save(e);
            return "Success";
        }else {
            return "Wrong information";
        }

    }
    private Employee findEmployee(Long id) {
        List<Employee> employees = employeeRepository.findAll();
        int index = -1;
        for (Employee employee : employees) {
            if (employee.getId().equals(id)) {
                index = employees.indexOf(employee);
            }
        }

        if (index == -1) {
            return null;
        }else {
            return employees.get(index);
        }
    }
}
