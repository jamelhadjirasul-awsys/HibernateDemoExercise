package com.spring.hibernate_demo;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.spring.hibernate_demo.dao.AppDao;
import com.spring.hibernate_demo.dao.StudentDao;

import com.spring.hibernate_demo.entity.Student;
import com.spring.hibernate_demo.entity.Course;
import com.spring.hibernate_demo.entity.Review;
import com.spring.hibernate_demo.entity.Instructor;
import com.spring.hibernate_demo.entity.InstructorDetail;

/**
 * Main class that bootstraps the Spring Boot application and demonstrates
 * CRUD operations on Student entities using Hibernate.
 */
@SpringBootApplication
public class HibernateDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(HibernateDemoApplication.class, args);
	}

	// @Bean 
	// CommandLineRunner commandLineRunner(StudentDao studentDao) {
	// 	return runner -> {
	// 		//createStudent(studentDao);
	// 		//readStudent(studentDao);
	// 		//queryStudents(studentDao);
	// 		//queryStudentLastName(studentDao);
	// 		//updateStudent(studentDao);
	// 		//deleteStudent(studentDao);
	// 		//deleteAllStudents(studentDao);
	// 	};
	// }

	@Bean 
	CommandLineRunner commandLineRunner(AppDao app) {
		return runner -> {
			//createInstructor(app);
			//findInstructor(app);
			//deleteInstructor(app);
			//findInstructorDetail(app);
			//deleteInstructorDetail(app);
			//createInstructorWithCourses(app);
			//findInstructorwithCourses(app);
			//findCoursesForInstructor(app);
			//findInstructorWithCoursesJoinFetch(app);
			//updateInstructor(app);
			//deleteInstructorOnly(app);
			//createCourseAndReviews(app);
			//retrieveCourseAndReviews(app);
			//createCourseAndStudents(app);
			//findCourseAndStudents(app);
			//findStudentAndCourse(app);
			//addMoreCoursesForStudent(app);
			//deleteCourseById(app);
			//deleteStudentById(app);
		};
	}

	// create student
	private void createStudent(StudentDao studentDao){
		// create the student object
		System.out.println("Creating student information...");
		Student student = new Student("Anshin", 
									  "Suru", 
									  "anshin.suru@example.com");
	
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

	// create instructor
	private void createInstructor(AppDao app){
		// create the instructor object
		Instructor ins = new Instructor("Marie",
									 "Curie", 
									 "marie.curie@example.com");
		InstructorDetail instructorDetails = 
					new InstructorDetail("Science", "Discovering radium");	
		
		// associate the objects
		ins.setInstructorDetails(instructorDetails);

		// save the instructor
		// this will also save the details object
		// because of the CascadeType.ALL
		System.out.println("Information saved!");
		app.save(ins);
	}

	// find instructor
	private void findInstructor(AppDao app) {
		int id = 1;
		System.out.println("Find id: " + id);

		Instructor ins = app.findInstructorById(id);

		System.out.println("Instructor: " + ins);
		System.out.println("Details: " + ins.getInstructorDetails());
	}

	// delete instructor
	private void deleteInstructor(AppDao app) {
		int id = 1;
		System.out.println("Delete id: " + id);
		
		app.deleteInstructorWithCoursesById(id);
		System.out.println("Deleted successfully!");
	}

	// find instructor details
	private void findInstructorDetail(AppDao app) {
		// get the instructor detail object
		int id = 5;
		InstructorDetail insDetails = app.findInstructorDetailById(id);

		// print the instructor detail
		System.out.println("Details: " + insDetails);

		// print the associated instructor
		System.out.println("Instructor: " + insDetails.getInstructor());
	}

	// delete instructor detail
	private void deleteInstructorDetail(AppDao app) {
		int id = 8;
		System.out.println("Delete id: " + id);

		app.deleteInstructorDetail(id);
		System.out.println("Deleted sucessfully");
	}

	// create instructor with courses
	private void createInstructorWithCourses(AppDao app) {
		// create the instructor and details
		Instructor ins = new Instructor("Strawberry",
										"Jam",
										"july.nine@example.com");	
		InstructorDetail instructorDetails = 
						new InstructorDetail("Math", "Equation of you + me");

		// associate the objects
		ins.setInstructorDetails(instructorDetails);

		// create courses
		Course course1 = new Course("Scientology");
		Course course2 = new Course("History");

		// add courses to instructor
		ins.add(course1);
		ins.add(course2);

		// save the instructor
		System.out.println("Courses saved: " + ins.getCourses());
		app.save(ins);
	}

	// find instructor with courses
	private void findInstructorwithCourses(AppDao app) {
		int id = 9;
		System.out.println("Find instructor id: " + id);

		Instructor ins = app.findInstructorById(id);

		System.out.println("Instructor: " + ins);
		System.out.println("Courses: " + ins.getCourses());
	}

	// find courses for instructor
	private void findCoursesForInstructor(AppDao app) {
		int id = 9;

		// find the instructor
		System.out.println("Find instructor id: " + id);
		Instructor ins = app.findInstructorById(id);

		System.out.println("Instructor: " + ins);

		// find courses for instructor
		System.out.println("Courses from id: " + id);
		List<Course> courses = app.findCoursesByInstructorId(id);

		// associate the objects
		ins.setCourses(courses);

		// get the courses
		System.out.println(ins.getCourses());
	}

	// find instructor with courses via JOIN FETCH
	private void findInstructorWithCoursesJoinFetch(AppDao app) {
		int id = 9;
		System.out.println("Find instructor id: " + id);

		Instructor ins = app.findInstructorByIdJoinFetch(id);

		System.out.println("Instructor: " + ins);
		System.out.println("Course: " + ins.getCourses());
	}

	// update instructor
	private void updateInstructor(AppDao app) {
		int id = 9;
		System.out.println("Find instructor id: " + id);

		Instructor ins = app.findInstructorById(id);

		// change instructor's data
		ins.setFirstName("Anshin");
		ins.setLastName("Suru");

		// update the data
		System.out.println("Updated!");
		app.update(ins);
	}

	// delete instructor
	private void deleteInstructorOnly(AppDao app) {
		int id = 10;
		System.out.println("Delete instructor: " + id);

		app.deleteInstructorById(id);
		System.out.println("Deleted successfully!");

	}

	// create course with reviews
	private void createCourseAndReviews(AppDao app) {
		// create a course
		Course course = new Course("How to be Good at Nihongo");

		// add some reviews
		course.addReview(new Review("正しい文法と文章のパターンを理解する"));
		course.addReview(new Review("会話で話す練習をする"));
		course.addReview(new Review("毎日日本語を勉強する"));
	
		// save the course
		System.out.println("Course saved!");
		app.save(course);
	}

	// retrieve course with reviews
	private void retrieveCourseAndReviews(AppDao app) {
		int id = 5;
		System.out.println("Get course and reviews from id: " + id);

		// get the course and reviews
		Course course = app.findCourseAndReviewsByCourseId(id);
	
		// print the course
		System.out.println(course);

		// print the reviews
		System.out.println(course.getReview());
	}

	// create course with students
	private void createCourseAndStudents(AppDao app) {
		// create a course
		Course course = new Course("How be to Good at not being Bad");

		// create the students
		Student student1 = new Student("Anshin", "Suru", "suru.anshin@example.com");
		Student student2 = new Student("Strawberry", "Jam", "strawberry.jam@example.com");

		// add students to the course
		course.addStudent(student1);
		course.addStudent(student2);

		// save the course and associated students
		app.save(course);
		System.out.println("Successfully enrolled!");
	}

	// find course with students
	private void findCourseAndStudents(AppDao app) {
		int id = 7;
		Course course = app.findCourseAndStudentByCourseId(id);

		System.out.println("Courses: " + course);
		System.out.println("Students: " + course.getStudents());
	}

	// find student with courses
	private void findStudentAndCourse(AppDao app) {
		int id = 11;
		Student student = app.findStudentAndCoursesByStudentId(id);

		System.out.println("Student: " + student);
		System.out.println("Courses: " + student.getCourses());
	}

	// add more courses for student
	private void addMoreCoursesForStudent(AppDao app) {
		int id = 12;
		Student student = app.findStudentAndCoursesByStudentId(id);

		Course course1 = new Course("How to be the One");
		Course course2 = new Course("How it feels to be the Second Option");
		Course course3 = new Course("How to get a Third Chance in Love");

		student.addCourse(course1);
		student.addCourse(course2);
		student.addCourse(course3);

		System.out.println("Successfully enrolled to the new courses!");

		app.update(student);
	}

	// delete course by ID
	private void deleteCourseById(AppDao app) {
		int id = 6;
		System.out.println("Delete course: " + id);

		app.deleteCourseById(id);
		System.out.println("Deleted successfully!");
	}

	// delete student by ID
	private void deleteStudentById(AppDao app) {
		int id = 2;
		System.out.println("Delete student: ");

		app.deleteStudentByID(id);
		System.out.println("Deleted successfully");
	}
}
