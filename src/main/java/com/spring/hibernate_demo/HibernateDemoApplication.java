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
 * Main application class bootstrapping Spring Boot and executing
 * Hibernate/JPA CRUD operations via {@link CommandLineRunner}.
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

	/**
     * Executes database operations sequentially on startup.
     * @param app the DAO executing application persistence operations
     * @return a {@link CommandLineRunner} bean to execute tasks on startup
     */
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

	// --- Student DAO Demos ---

	/** 
	 * Demonstrates persisting a new student. 
	 */
	private void createStudent(StudentDao studentDao){
		System.out.println("Creating student information...");
		Student student = new Student("Anshin", 
									  "Suru", 
									  "anshin.suru@example.com");

		System.out.println("Saving student information...");
		studentDao.save(student);

		System.out.println("Saved! Generated id: " + student.getId());
	}

	/** 
	 * Demonstrates retrieving a single student by primary key. 
	 */
	private void readStudent(StudentDao studentDao){
		System.out.println("Retrieving student id 2...");
		Student myStudent = studentDao.findById(2);
		
		System.out.println("Found the student: " + myStudent);
	}

	/** 
	 * Demonstrates fetching all students. 
	 */
	private void queryStudents(StudentDao studentDao){
		List<Student> students = studentDao.findAll();
		for (Student s: students){
			System.out.println(s);
		}
	}

	/** 
	 * Demonstrates searching students by last name. 
	 */	
	private void queryStudentLastName(StudentDao studentDao){
		List<Student> students = studentDao.findByLastName("Uzumaki");
		for (Student s: students){
			System.out.println(s);
		}
	}

	/** 
	 * Demonstrates updating student details. 
	 */	
	private void updateStudent(StudentDao studentDao){
		int id = 1;
		System.out.println("Student ID: " + id);
		Student student = studentDao.findById(id);
		
		System.out.println("Updating student name...");
		student.setFirstName("Strawberry");
	
		studentDao.update(student);
		System.out.println("Updated student information: " + student);
	}

	/** 
	 * Demonstrates deleting a single student by primary key. 
	 */	
	private void deleteStudent(StudentDao studentDao){
		int id = 2;
		System.out.println("Deleting ID: " + id);
		studentDao.delete(id);
	}

	/** 
	 * Demonstrates truncating/deleting all student records. 
	 */
	private void deleteAllStudents(StudentDao studentDao){
		System.out.println("Deleting all student information...");
		int rowsDeleted = studentDao.deleteAll();
		System.out.println("Number of deleted rows: " + rowsDeleted);
	}

	// --- Instructor & InstructorDetail Demos ---

	/** 
	 * Demonstrates cascading save between Instructor and InstructorDetail. 
	 */
	private void createInstructor(AppDao app){
		Instructor ins = new Instructor("Marie",
									 "Curie", 
									 "marie.curie@example.com");
		InstructorDetail instructorDetails = 
					new InstructorDetail("Science", "Discovering radium");	
		
		ins.setInstructorDetails(instructorDetails);

		System.out.println("Information saved!");
		app.save(ins);
	}

	/** 
	 * Demonstrates retrieving instructor and associated details by ID. 
	 */
	private void findInstructor(AppDao app) {
		int id = 1;
		System.out.println("Find id: " + id);

		Instructor ins = app.findInstructorById(id);

		System.out.println("Instructor: " + ins);
		System.out.println("Details: " + ins.getInstructorDetails());
	}

	/** 
	 * Demonstrates deleting an instructor along with their associated courses. 
	 */
	private void deleteInstructor(AppDao app) {
		int id = 1;
		System.out.println("Delete id: " + id);
		
		app.deleteInstructorWithCoursesById(id);
		System.out.println("Deleted successfully!");
	}

	/** 
	 * Demonstrates bi-directional lookup from InstructorDetail to Instructor.
	 */
	private void findInstructorDetail(AppDao app) {
		int id = 5;
		InstructorDetail insDetails = app.findInstructorDetailById(id);

		System.out.println("Details: " + insDetails);
		System.out.println("Instructor: " + insDetails.getInstructor());
	}

	/** 
	 * Demonstrates deleting instructor details without deleting the instructor 
	 */	
	private void deleteInstructorDetail(AppDao app) {
		int id = 8;
		System.out.println("Delete id: " + id);

		app.deleteInstructorDetail(id);
		System.out.println("Deleted sucessfully");
	}

	// --- One-to-Many Relationship Demos (Instructor <-> Course) ---

	/** 
	 * Demonstrates saving an instructor with multiple attached courses. 
	 */
	private void createInstructorWithCourses(AppDao app) {
		Instructor ins = new Instructor("Strawberry",
										"Jam",
										"july.nine@example.com");	
		InstructorDetail instructorDetails = 
						new InstructorDetail("Math", "Equation of you + me");

		ins.setInstructorDetails(instructorDetails);

		Course course1 = new Course("Scientology");
		Course course2 = new Course("History");

		ins.add(course1);
		ins.add(course2);

		System.out.println("Courses saved: " + ins.getCourses());
		app.save(ins);
	}

	/** 
	 * Demonstrates lazy loading courses for a specific instructor. 
	 */
	private void findInstructorwithCourses(AppDao app) {
		int id = 9;
		System.out.println("Find instructor id: " + id);

		Instructor ins = app.findInstructorById(id);

		System.out.println("Instructor: " + ins);
		System.out.println("Courses: " + ins.getCourses());
	}

	/** 
	 * Demonstrates querying courses explicitly by instructor ID. 
	 */
	private void findCoursesForInstructor(AppDao app) {
		int id = 9;

		System.out.println("Find instructor id: " + id);
		Instructor ins = app.findInstructorById(id);

		System.out.println("Instructor: " + ins);

		System.out.println("Courses from id: " + id);
		List<Course> courses = app.findCoursesByInstructorId(id);

		ins.setCourses(courses);
		System.out.println(ins.getCourses());
	}

	/** 
	 * Demonstrates fetching an instructor and 
	 * courses in one SQL query using JOIN FETCH. 
	 */
	private void findInstructorWithCoursesJoinFetch(AppDao app) {
		int id = 9;
		System.out.println("Find instructor id: " + id);

		Instructor ins = app.findInstructorByIdJoinFetch(id);

		System.out.println("Instructor: " + ins);
		System.out.println("Course: " + ins.getCourses());
	}

	/** 
	 * Demonstrates updating an instructor entity. 
	 */
	private void updateInstructor(AppDao app) {
		int id = 9;
		System.out.println("Find instructor id: " + id);

		Instructor ins = app.findInstructorById(id);

		ins.setFirstName("Anshin");
		ins.setLastName("Suru");

		System.out.println("Updated!");
		app.update(ins);
	}

	/** 
	 * Demonstrates deleting an instructor while unlinking their courses. 
	 */
	private void deleteInstructorOnly(AppDao app) {
		int id = 10;
		System.out.println("Delete instructor: " + id);

		app.deleteInstructorById(id);
		System.out.println("Deleted successfully!");

	}

	// --- One-to-Many Uni-Directional Demos (Course -> Review) ---

    /** 
	 * Demonstrates creating a course with nested review entries. 
	 */
	private void createCourseAndReviews(AppDao app) {
		Course course = new Course("How to be Good at Nihongo");

		course.addReview(new Review("正しい文法と文章のパターンを理解する"));
		course.addReview(new Review("会話で話す練習をする"));
		course.addReview(new Review("毎日日本語を勉強する"));
	
		System.out.println("Course saved!");
		app.save(course);
	}

	/** 
	 * Demonstrates retrieving a course alongside its associated reviews. 
	 */
	private void retrieveCourseAndReviews(AppDao app) {
		int id = 5;
		System.out.println("Get course and reviews from id: " + id);

		Course course = app.findCourseAndReviewsByCourseId(id);
	
		System.out.println(course);
		System.out.println(course.getReview());
	}

	// --- Many-to-Many Demos (Course <-> Student) ---

	/** 
	 * Demonstrates saving a course along with newly enrolled students. 
	 */
	private void createCourseAndStudents(AppDao app) {
		Course course = new Course("How be to Good at not being Bad");

		Student student1 = new Student("Anshin", 
										"Suru", "suru.anshin@example.com");
		Student student2 = new Student("Strawberry", 
										"Jam", "strawberry.jam@example.com");

		course.addStudent(student1);
		course.addStudent(student2);

		app.save(course);
		System.out.println("Successfully enrolled!");
	}

	/** 
	 * Demonstrates fetching a course and its enrolled students via JOIN FETCH. 
	 */
	private void findCourseAndStudents(AppDao app) {
		int id = 7;
		Course course = app.findCourseAndStudentByCourseId(id);

		System.out.println("Courses: " + course);
		System.out.println("Students: " + course.getStudents());
	}

	/** 
	 * Demonstrates fetching a student and their registered courses. 
	 */
	private void findStudentAndCourse(AppDao app) {
		int id = 11;
		Student student = app.findStudentAndCoursesByStudentId(id);

		System.out.println("Student: " + student);
		System.out.println("Courses: " + student.getCourses());
	}

	/** 
	 * Demonstrates adding multiple new courses to an existing student. 
	 */
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

	/** 
	 * Demonstrates deleting a course by ID. 
	 */
	private void deleteCourseById(AppDao app) {
		int id = 6;
		System.out.println("Delete course: " + id);

		app.deleteCourseById(id);
		System.out.println("Deleted successfully!");
	}

	/**
	 * Demonstrates deleting a student by ID. 
	 */
	private void deleteStudentById(AppDao app) {
		int id = 2;
		System.out.println("Delete student: ");

		app.deleteStudentByID(id);
		System.out.println("Deleted successfully");
	}
}
