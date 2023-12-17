package com.finalExam.EmployeeProjects.controller;

import com.finalExam.EmployeeProjects.file.CSVReadService;
import com.finalExam.EmployeeProjects.file.CSVReader;
import com.finalExam.EmployeeProjects.model.Employee;
import com.finalExam.EmployeeProjects.projectManagment.ProjectManagment;
import com.finalExam.EmployeeProjects.service.EmployeeService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.LinkedHashMap;
import java.util.List;

@Controller
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private CSVReader csvRead;
    @Autowired
    private ProjectManagment projectManagment ;
    @GetMapping("/employee/together")
    public String workTogether(Model model){
        LinkedHashMap<Integer,List<Integer>> working = projectManagment.workingHours();
        model.addAttribute("project",working);
        return "workingTogether";
    }
    @GetMapping("/project")
    public String projectTime(Model model){
        LinkedHashMap<Integer, Integer> projectTime = projectManagment.workingOnProject();
        model.addAttribute("projectTime",projectTime);
        return "project";
    }

    @GetMapping ("/employees")
    public String importEmployees(Model model) {
        String csvFilePath = "/src/main/resources/csv/csvFile.csv";
        List<Employee> employees = csvRead.read("/src/main/resources/csv/csvFile.csv");;
        for (Employee e : employees) {
            if (!employeeService.isExist(e)) {
                employeeService.saveEmployee(e);
            }

        }

        model.addAttribute("employees" ,employeeService.findAll());
        return "employee";

    }

}
