package com.finalExam.EmployeeProjects.service;


import com.finalExam.EmployeeProjects.model.Employee;
import com.finalExam.EmployeeProjects.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.EmptyStackException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public void saveEmployee(Employee employee){
        employeeRepository.save(employee);
    }
    public List<Employee> findAll(){
        return employeeRepository.findAll();

    }
    public boolean isExist(Employee e){
        for (Employee employee : employeeRepository.findAll()){
          if (e.getIdSystem() == employee.getIdSystem() && e.getProjectId() == employee.getProjectId()
                  && e.getStartDate().equals( employee.getStartDate())) {
              return true;

          }
      }
       return false;

    }
    public void delete(Long id){
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);

        }else {
            throw new NoSuchElementException("Employee not found");
        }
    }
    //it's not editing in csv file
    public void editById(Long id, Employee employee){
        if (findEmployee(id)!=null) {
            Employee e = findEmployee(id);
            if (e!=null) {
                e.setIdSystem(employee.getIdSystem());
                e.setProjectId(employee.getProjectId());
                e.setStartDate(employee.getStartDate());
                e.setEndDate(employee.getEndDate());

                employeeRepository.save(e);

            }
        }else {
            throw new NoSuchElementException("Employee not found");
        }

    }
    public Employee findEmployee(Long id) {
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
