package com.finalExam.EmployeeProjects.file;

import com.finalExam.EmployeeProjects.model.Employee;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CSVRead implements CSVReader {
    //TODO more Data format
    @Override
    public List<Employee> read(String path) {
        List<Employee> employees = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String reading = bufferedReader.readLine();
            while (reading != null) {
                if (!reading.isEmpty()) {
                    String[] read = reading.split(", ");
                    if (isInteger(read[0]) && isInteger(read[1])){
                        int id = Integer.parseInt(read[0]);
                        int projectId = Integer.parseInt(read[1]);
                        LocalDate dateStart = LocalDate.parse(read[2], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        LocalDate dateEnd = LocalDate.now();
                        if (!read[3].equals("NULL")){
                            dateEnd = LocalDate.parse(read[3],DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        }
                        Employee employee = new Employee(id,projectId,dateStart,dateEnd);
                        employees.add(employee);
                    }

                }
                reading = bufferedReader.readLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return employees;
    }
    private boolean isInteger(String number){
        try {
            Integer.parseInt(number);
        }catch (Exception e){
            return false;
        }
        return true;
    }
}
