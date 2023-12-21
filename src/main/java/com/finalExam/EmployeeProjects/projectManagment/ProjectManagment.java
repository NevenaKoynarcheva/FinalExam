package com.finalExam.EmployeeProjects.projectManagment;
import com.finalExam.EmployeeProjects.model.Employee;
import com.finalExam.EmployeeProjects.service.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;


@Component
public class ProjectManagment {
    @Autowired
    private EmployeeService employeeService;
    private List<Employee> employees = new ArrayList<>();
    //Method return all projects and there employees
    private Map<Integer, List<Employee>> employeeProjects() {
        if (employeeService.findAll() != null){
            this.employees =  employeeService.findAll();
        }

        Map<Integer, List<Employee>> projects = new LinkedHashMap<>();
        for (Employee employee : employees) {
            if (!projects.containsKey(employee.getProjectId())) {
                projects.put(employee.getProjectId(), new ArrayList<>());
            }
            projects.get(employee.getProjectId()).add(employee);

        }
        return projects;
    }


    // We save information about the employees working together on the same project.
    private Map<List<Employee>, Integer> working() {
        Map<Integer, List<Employee>> mathEmployee = employeeProjects();
        Map<List<Employee>, Integer> workingHard = new HashMap<>();

        for (Map.Entry<Integer, List<Employee>> entry : mathEmployee.entrySet()) {
            List<Employee> employeesInProject = entry.getValue();
            int workingDays = 0;
            for (int first = 0; first < employeesInProject.size(); first++) {
                for (int last = employeesInProject.indexOf(employeesInProject.get(first)) + 1; last < employeesInProject.size(); last++) {
                    //We are comparing the work periods to determine if there is any overlap.
                    if (dateInterval(employeesInProject.get(first).getStartDate(), employeesInProject.get(first).getStartDate(),
                            employeesInProject.get(last).getStartDate(), employeesInProject.get(last).getEndDate())) {

                        workingDays = calculateWorkingDay(employeesInProject.get(last).getStartDate(), employeesInProject.get(first).getEndDate(), employeesInProject.get(last).getEndDate());

                    } else if (dateInterval(employeesInProject.get(last).getStartDate(), employeesInProject.get(last).getEndDate(),
                            employeesInProject.get(first).getStartDate(), employeesInProject.get(first).getEndDate())) {

                        workingDays = calculateWorkingDay(employeesInProject.get(first).getStartDate(), employeesInProject.get(last).getEndDate(), employeesInProject.get(first).getEndDate());
                    }

                    if (workingDays > 0) {
                        List<Employee> employees1 = new ArrayList<>();
                        employees1.add(employeesInProject.get(first));
                        employees1.add(employeesInProject.get(last));
                        employees1.sort(Comparator.comparingInt(Employee::getIdSystem));
                        if (!workingHard.containsKey(employees1)) {
                            workingHard.put(employees1,workingDays);
                        }

                    }
                }
            }

        }


        return workingHard;
    }

    //All days that the pair of employees are worked together
    public Map<List<Integer>,Integer> daysOnProjects(){
        Map<List<Integer>, Integer> days = new HashMap<>();
        for (Map.Entry<List<Employee>, Integer> entry : working().entrySet()){
            List<Employee> employees1 = entry.getKey();
            List<Integer> id = new ArrayList<>();
            for (Employee e : employees1){
                id.add(e.getIdSystem());
            }
            Collections.sort(id);
            if (!days.containsKey(id)){
                days.put(id,0);
            }
            days.put(id, days.get(id) + entry.getValue());
        }

        Map<List<Integer>, Integer> sortedMap = days.entrySet()
                .stream()
                .sorted(Map.Entry.<List<Integer>, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
        int count = 0;
        Map<List<Integer>,Integer> daysOnProject = new LinkedHashMap<>();
        for (Map.Entry<List<Integer>,Integer> entry : sortedMap.entrySet()){
            while (count < 1){
                daysOnProject.put(entry.getKey(),entry.getValue());
                count++;
            }
        }
        return daysOnProject;
    }
    public Map<Integer, Integer> project(){
        Map<List<Integer>,Integer> employee = daysOnProjects();
        Map<List<Employee> , Integer> projectDay = working();
        Map<Integer, Integer> projects = new LinkedHashMap<>();
        for (Map.Entry<List<Integer>, Integer> entry : employee.entrySet()){
            for (Map.Entry<List<Employee>, Integer> entry1 : projectDay.entrySet()){
                List<Integer> integers = new ArrayList<>();
                int projectId = 0;
                for (Employee employee1 : entry1.getKey()){
                    integers.add(employee1.getIdSystem());
                    projectId = employee1.getProjectId();
                }

                Collections.sort(integers);
                if (entry.getKey().equals(integers)){
                    if (!projects.containsKey(projectId)){
                        projects.put(projectId,entry1.getValue());
                    }
                }
            }
        }

        return projects;
    }


    private boolean dateInterval (LocalDate firstStart, LocalDate firstEnd, LocalDate lastStart, LocalDate lastEnd){
        if ((firstStart.isBefore(lastStart)
                || (firstStart.equals(lastStart))
                && (firstEnd.isAfter(lastStart)))){
            return true;
        }else{
            return false;
        }
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