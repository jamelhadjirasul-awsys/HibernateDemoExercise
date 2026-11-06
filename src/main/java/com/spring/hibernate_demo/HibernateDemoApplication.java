package com.spring.hibernate_demo;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.spring.hibernate_demo.dao.StudentDao;
import com.spring.hibernate_demo.entity.Student;

/**
 * Main class that bootstraps the Spring Boot application and demonstrates
 * CRUD operations on Student entities using Hibernate.
 */
@SpringBootApplication
public class HibernateDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(HibernateDemoApplication.class, args);
	}

	@Bean 
	CommandLineRunner commandLineRunner(StudentDao studentDao) {
		return runner -> {
			createStudent(studentDao);
			//readStudent(studentDao);
			//queryStudents(studentDao);
			//queryStudentLastName(studentDao);
			//updateStudent(studentDao);
			//deleteStudent(studentDao);
			//deleteAllStudents(studentDao);
		};
	}

	// create student
	private void createStudent(StudentDao studentDao){
		// create the student object
		System.out.println("Creating student information...");
		Student student = new Student("Naruto", 
									  "Uzumaki", 
									  "naruto.uzumaki@example.com");
	
		// save the student object
		System.out.println("Saving student information...");
		studentDao.save(student);

		//display the id of the saved student
		System.out.println("Saved! Generated id: " + student.getId());
	}

	// read student based on the id
	private void readStudent(StudentDao studentDao){
		// retrieve student based on the id
		System.out.println("Retrieving student id 2...");
		Student myStudent = studentDao.findById(2);
		
		// display student
		System.out.println("Found the student: " + myStudent);
	}

	// query students
	private void queryStudents(StudentDao studentDao){
		// get a list of students
		List<Student> students = studentDao.findAll();
		
		// display list of students
		for (Student s: students){
			System.out.println(s);
		}
	}

	// query students by last name
	private void queryStudentLastName(StudentDao studentDao){
		// get a list of students
		List<Student> students = studentDao.findByLastName("Uzumaki");
		
		// display list of students
		for (Student s: students){
			System.out.println(s);
		}
	}

	// update student based on the id
	private void updateStudent(StudentDao studentDao){
		// retrieve student based on the id
		int id = 1;
		System.out.println("Student ID: " + id);
		Student student = studentDao.findById(id);
		
		// update student name 
		System.out.println("Updating student name...");
		student.setFirstName("Strawberry");
	
		// update the student
		studentDao.update(student);
		
		// display updated student information
		System.out.println("Updated student information: " + student);
	}

	// delete student based on the id
	private void deleteStudent(StudentDao studentDao){
		// delete student based on the id
		int id = 2;
		System.out.println("Deleting ID: " + id);

		// delete the student
		studentDao.delete(id);
	}

	// delete all students
	private void deleteAllStudents(StudentDao studentDao){
		// delete all students
		System.out.println("Deleting all student information...");
		int rowsDeleted = studentDao.deleteAll();
		System.out.println("Number of deleted rows: " + rowsDeleted);
	}
}
