package com.finalExam.EmployeeProjects.controller;
import com.finalExam.EmployeeProjects.file.CSVReader;
import com.finalExam.EmployeeProjects.model.Employee;
import com.finalExam.EmployeeProjects.projectManagment.ProjectManagment;
import com.finalExam.EmployeeProjects.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

@Controller
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private CSVReader csvRead;
    @Autowired
    private ProjectManagment projectManagment ;

    @GetMapping ("/addNewEmployee")
    public String importNewEmployees(Model model) {
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
    @GetMapping("/")
    public String findAll(Model model){
        model.addAttribute("employees",employeeService.findAll());
        return "employee";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id){
        employeeService.delete(id);
        return "redirect:/";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id,@ModelAttribute Employee employee){
        if (employee.getEndDate() == null){
            employee.setEndDate(LocalDate.now());
        }
        if (employee.getStartDate().until(employee.getEndDate(),ChronoUnit.DAYS) > 0) {
            employeeService.editById(id, employee);
        }
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String editTodoForm(@PathVariable Long id,Model model){
        try {
            model.addAttribute("employee", employeeService.findEmployee(id));
        }catch (Exception e){
            return "redirect:/";
        }

        return "editEmployee";
    }

    @GetMapping("/employee/together")
    public String workTogether(Model model){
        Map<List<Integer>,Integer> working = projectManagment.togetherLong();
        model.addAttribute("project",working);
        return "workingTogether";
    }


}

