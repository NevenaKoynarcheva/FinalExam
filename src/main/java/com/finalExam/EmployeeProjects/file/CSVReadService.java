package com.finalExam.EmployeeProjects.file;


import com.finalExam.EmployeeProjects.model.Employee;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.*;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Service
public class CSVReadService implements CSVReader {
    //TODO more Data format
    @Override
    public List<Employee> read(String path) {
        List<Employee> employees = new ArrayList<>();
        ClassPathResource resource = new ClassPathResource("csv/csvFile.csv");


        try (InputStream inputStream = resource.getInputStream() ; BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream)) {
        }) {
            String reading = bufferedReader.readLine();
            while (reading != null)

            {
                if (!reading.isEmpty()) {
                    String[] read = reading.split(", ");
                    if (isInteger(read[0]) && isInteger(read[1])) {
                        int id = Integer.parseInt(read[0]);
                        int projectId = Integer.parseInt(read[1]);
                        LocalDate dateStart = parseDynamicDateFormat(read[2]);
                        LocalDate dateEnd = LocalDate.now();
                        if (!read[3].equals("NULL")) {
                            dateEnd = parseDynamicDateFormat(read[3]);
                        }

                        if (isCorrectDate(dateStart, dateEnd)) {
                            Employee employee = new Employee(id, projectId, dateStart, dateEnd);
                            employees.add(employee);
                        }
                    }

                }
                reading = bufferedReader.readLine();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return employees;
    }
     private static LocalDate parseDynamicDateFormat (String input){
        List<DateTimeFormatter> formatters = Arrays.asList(
                DateTimeFormatter.ofPattern("yyyy-MM-dd"),
                DateTimeFormatter.ofPattern("MM-dd-yyyy"),
                DateTimeFormatter.ofPattern("dd/MM/yyyy")

        );

        for (DateTimeFormatter formatter : formatters) {
            try {
                return LocalDate.parse(input, formatter);
            } catch (Exception ignored) {

            }
        }

        throw new IllegalArgumentException("Unsupported date format: " + input);
    }
    private boolean isInteger(String number){
        try {
            Integer.parseInt(number);
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    private boolean isCorrectDate(LocalDate start, LocalDate end) throws Exception {
        if (start.until(end, ChronoUnit.DAYS) >= 0){
            return true;
        }else {
            throw new DateTimeException("Start date can't be after end date");
        }
    }
}
