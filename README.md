# Final Exam

This application retrieves information about workers from a CSV file and returns as a result the pair of workers 
who have worked the most days together and their respective projects.

## Technologies Used
Spring MVC Pattern: Utilized for the web component.
PostgreSQL: Database management system.
Spring Data: Used for data access.
HTML and CSS: For web interface styling.
### Connection to PostgreSQL
The connection to PostgreSQL is established manually in the main class. Hibernate is employed to send queries to our database, where all worker information from the file is stored.

## Finding Workers with Most Work Days Together
To identify the pair of workers who have worked the most days together:

First, we create a data structure to store all projects and the workers involved in them.
Next, we check which workers have overlapping work periods and whether they have worked on the same project.
If there is a match, we save the pair of workers, along with their project, and calculate the days they have worked together on it.
To find the pair with the most days worked together, we iterate through the entire structure. If we find that a pair has worked together on another project,
we aggregate the days worked on that project in a separate structure. Finally, we sort the structure by days and display only those with the most working days.

### Displaying Project Information
In the end, we retrieve all projects in which this pair has participated and output the project ID along with the number of days spent on it.
