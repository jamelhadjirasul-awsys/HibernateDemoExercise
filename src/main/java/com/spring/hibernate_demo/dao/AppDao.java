package com.spring.hibernate_demo.dao;

import com.spring.hibernate_demo.entity.Instructor;
import com.spring.hibernate_demo.entity.InstructorDetail;
import com.spring.hibernate_demo.entity.Course;
import com.spring.hibernate_demo.entity.Student;
import java.util.List;

/**
 * Defines persistence operations for instructors, courses, and students.
 * Includes lookups that fetch related entities and operations for managing
 * entity relationships.
 */
public interface AppDao {

    void save(Instructor theInstructor);
    Instructor findInstructorById(int theId);
    void deleteInstructorWithCoursesById(int theId);
    InstructorDetail findInstructorDetailById(int theId);
    void deleteInstructorDetail(int theId);
    List<Course> findCoursesByInstructorId(int theId);
    Instructor findInstructorByIdJoinFetch(int theId);
    void update(Instructor theInstructor);
    void deleteInstructorById(int theId);
    void save(Course theCourse);
    Course findCourseAndReviewsByCourseId(int theId);
    Course findCourseAndStudentByCourseId(int theId);
    Student findStudentAndCoursesByStudentId(int theId);
    void update(Student student);
    void deleteCourseById(int theId);
    void deleteStudentByID(int theId);
}
