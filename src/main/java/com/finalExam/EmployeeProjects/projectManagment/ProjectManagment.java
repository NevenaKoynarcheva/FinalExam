package com.finalExam.EmployeeProjects.projectManagment;
import com.finalExam.EmployeeProjects.model.Employee;
import com.finalExam.EmployeeProjects.service.EmployeeService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

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
    //The method returns people who work together and how many days they worked together
    public Map<List<Integer>, Integer> togetherLong(){
        Map<Integer, List<Integer>> days = working();
        Map<List<Integer>, Integer> daysTogether = new LinkedHashMap<>();

        for (Map.Entry<Integer, List<Integer>> entry : days.entrySet()){
            List<Integer> sortedList = new ArrayList<>(entry.getValue());
            Collections.sort(sortedList);
            if (!daysTogether.containsKey(sortedList)){
                daysTogether.put(sortedList,0);
            }
            daysTogether.put(sortedList,daysTogether.get(sortedList)+entry.getKey());
        }
        return daysTogether;

    }

    // We save information about the employees working together on the same project.
    private Map<Integer, List<Integer>> working() {
        Map<Integer, List<Employee>> mathEmployee = employeeProjects();
        Map<Integer, List<Integer>> workingHard = new TreeMap<>(Comparator.reverseOrder());
        int workingDays = 0;
        for (Map.Entry<Integer, List<Employee>> entry : mathEmployee.entrySet()) {
            List<Employee> employeesInProject = entry.getValue();
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

    public Map<Integer,Integer> daysOnProject(){
        Map<Integer, Integer> days = new HashMap<>();

            for (Map.Entry<Integer,List<Integer>> entry1 : working().entrySet()) {
                {
                    for (Map.Entry<Integer, List<Employee>> entry : employeeProjects().entrySet()) {
                        List<Integer> employee = new ArrayList<>();
                        for (Employee e : entry.getValue()) {
                            employee.add(e.getIdSystem());
                        }

                        List<Integer> value = entry1.getValue();
                        Collections.sort(employee);
                        Collections.sort(value);
                        if (employee.equals(value)) {
                            if (!days.containsKey(entry.getKey())) {
                                days.put(entry.getKey(), entry1.getKey());
                                break;
                            }

                        }
                    }
                }
            }
        return days;
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
