package com.finalExam.EmployeeProjects;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.*;

@SpringBootApplication
public class EmployeeProjectsApplication {


	public static void main(String[] args) {
		Connection connection = null;
		Statement statement = null;
		try {

			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/", "postgres", "1234");
			statement = connection.createStatement();
			statement.executeQuery("SELECT count(*) FROM pg_database WHERE datname = 'employeedb'");
			ResultSet resultSet = statement.getResultSet();
			resultSet.next();
			int count = resultSet.getInt(1);

			if (count <= 0) {
				statement.executeUpdate("CREATE DATABASE employeedb");

			}
		} catch (SQLException e) {

		} finally {
			try {
				if (statement != null) {
					statement.close();

				}
				if (connection != null) {

					connection.close();
				}
			}catch (SQLException e){

			}
		}
		SpringApplication.run(EmployeeProjectsApplication.class, args);
	}

}
