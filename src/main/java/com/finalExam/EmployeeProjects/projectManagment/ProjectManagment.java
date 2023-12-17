package com.finalExam.EmployeeProjects.projectManagment;

import com.finalExam.EmployeeProjects.file.CSVReadService;
import com.finalExam.EmployeeProjects.file.CSVReader;
import com.finalExam.EmployeeProjects.model.Employee;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
@Component
public class ProjectManagment {
    @Autowired
    private CSVReader csvReader = new CSVReadService();


    private final List<Employee> employees = csvReader.read("/src/main/resources/csv/csvFile.csv");


    private LinkedHashMap<Integer, List<Employee>> employeeProjects() {
        LinkedHashMap<Integer, List<Employee>> projects = new LinkedHashMap<>();
        for (Employee employee : employees) {
            if (!projects.containsKey(employee.getProjectId())) {
                projects.put(employee.getProjectId(), new ArrayList<>());
            }
            projects.get(employee.getProjectId()).add(employee);

        }
        return projects;
    }
    public LinkedHashMap<Integer, Integer> workingOnProject(){
        LinkedHashMap<Integer, List<Employee>> project = employeeProjects();
        LinkedHashMap<Integer,Integer> workingOnProject = new LinkedHashMap<>();
        for (Map.Entry<Integer, List<Employee>> entry : project.entrySet()){
            if (!workingOnProject.containsKey(entry.getKey())){
                workingOnProject.put(entry.getKey(),0);
            }
            for (Employee date : entry.getValue())
            {
                workingOnProject.put(entry.getKey(),workingOnProject.get(entry.getKey())+((int)date.getStartDate().until(date.getEndDate(),ChronoUnit.DAYS)));
            }
        }
        return workingOnProject;
    }
    // We save information about the employees working together on the same project.
    public LinkedHashMap<Integer, List<Integer>> workingHours() {
        LinkedHashMap<Integer, List<Employee>> mathEmployee = employeeProjects();
        LinkedHashMap<Integer, List<Integer>> workingHard = new LinkedHashMap<>();
        int workingDays = 0;
        for (Map.Entry<Integer, List<Employee>> entry : mathEmployee.entrySet()) {
            List<Employee> employeesInProject = entry.getValue();
            for (int first = 0; first < employeesInProject.size(); first++) {
                for (int last = employeesInProject.indexOf(employeesInProject.get(first)) + 1; last < employeesInProject.size(); last++) {
                    //We are comparing the work periods to determine if there is any overlap.
                    if ((employeesInProject.get(first).getStartDate().isBefore(employeesInProject.get(last).getStartDate()))
                            || (employeesInProject.get(first).getStartDate().equals(employeesInProject.get(last).getStartDate())
                            && (employeesInProject.get(first).getEndDate().isAfter(employeesInProject.get(last).getStartDate())))) {

                        workingDays = calculateWorkingDay(employeesInProject.get(last).getStartDate(), employeesInProject.get(first).getEndDate(), employeesInProject.get(last).getEndDate());

                    } else if (((employeesInProject.get(last).getStartDate().isBefore(employeesInProject.get(first).getStartDate()))
                            || (employeesInProject.get(last).getStartDate().equals(employeesInProject.get(first).getStartDate())
                            && (employeesInProject.get(last).getEndDate().isAfter(employeesInProject.get(first).getStartDate()))))) {

                        workingDays = calculateWorkingDay(employeesInProject.get(first).getStartDate(), employeesInProject.get(last).getEndDate(), employeesInProject.get(first).getEndDate());
                    }

                    if (workingDays > 0) {
                          if (!workingHard.containsKey(workingDays)) {
                            workingHard.put(workingDays, new ArrayList<>());
                        }

                        workingHard.get(workingDays).add(employeesInProject.get(last).getIdSystem());
                        workingHard.get(workingDays).add(employeesInProject.get(first).getIdSystem());
                    }
                }
            }

        }

        return workingHard;
    }
    private int calculateWorkingDay(LocalDate lastStart, LocalDate firstEnd, LocalDate lastEnd ){
        int workingDays = 0;
        if (lastEnd.isBefore(firstEnd)){
            workingDays = (int) lastStart.until(lastEnd, ChronoUnit.DAYS);
        }else if(lastEnd.isAfter(firstEnd)){
            workingDays = (int) lastStart.until(firstEnd,ChronoUnit.DAYS);
        }else if(lastEnd.equals(firstEnd)){
            workingDays = (int) lastStart.until(firstEnd,ChronoUnit.DAYS);
        }

        return workingDays;
    }


}
