package com.finalExam.EmployeeProjects.controller;

import com.finalExam.EmployeeProjects.file.CSVReadService;
import com.finalExam.EmployeeProjects.file.CSVReader;
import com.finalExam.EmployeeProjects.model.Employee;
import com.finalExam.EmployeeProjects.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private CSVReader csvReader;

    public void setCsvReader(CSVReadService csvRead) {
        this.csvReader = csvRead;
    }

    @GetMapping("/employees")
    public List<Employee> importEmployees(Model model) {
        String csvFilePath = "/src/main/resources/csv/csvFile.csv"; // Update with the actual path

        List<Employee> employees = csvReader.read(csvFilePath);
        for (Employee e  : employees) {
            employeeService.saveEmployee(e);
        }
        return employeeService.findAll();
       // return "redirect:/employee/list"; // Redirect to a page showing the list of employees
    }

}
