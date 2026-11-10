package com.spring.hibernate_demo.dao;

import org.springframework.stereotype.Repository;
import com.spring.hibernate_demo.entity.Instructor;
import com.spring.hibernate_demo.entity.InstructorDetail;
import com.spring.hibernate_demo.entity.Student;
import com.spring.hibernate_demo.entity.Course;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.List;

/**
 * Provides database operations for the application's instructor, course,
 * and student entities.
 */
@Repository 
public class AppDaoImplement implements AppDao {

    private EntityManager em;

    public AppDaoImplement(EntityManager em) {
        this.em = em;
    }

    // implement save instructor method
    @Override 
    @Transactional 
    public void save(Instructor theInstructor) {
        em.persist(theInstructor);
    }

    // implement findInstructorById method
    @Override 
    public Instructor findInstructorById(int theId) {
        return em.find(Instructor.class, theId);
    }

    // implement deleteInstructorId method
    @Override 
    @Transactional 
    public void deleteInstructorWithCoursesById(int theId) {
        // retrive the instructor
        Instructor in = em.find(Instructor.class, theId);

        // delete the instructor
        em.remove(in);
    }

    // implement findInstructorDetailById method
    @Override
    public InstructorDetail findInstructorDetailById(int theId) {
        return em.find(InstructorDetail.class, theId);
    }

    // // implement deleteInstructorDetail method
    // @Override
    // @Transactional 
    // public void deleteInstructorDetail(int theId) {
    //     // retrieve instructor detail
    //     InstructorDetail instructorDetails = em.find(InstructorDetail.class, theId);

    //     // delete instructor detail
    //     em.remove(instructorDetails);
    // }

    // implement deleteInstructorDetail method -NOT CASCADING
    @Override
    @Transactional 
    public void deleteInstructorDetail(int theId) {
        // retrieve instructor detail
        InstructorDetail instructorDetails = em.find(InstructorDetail.class, theId);

        // remove the associated object reference
        instructorDetails.getInstructor().setInstructorDetails(null);

        // delete instructor detail
        em.remove(instructorDetails);
    }

    // implement findCoursesByInstructorId method
    @Override 
    public List<Course> findCoursesByInstructorId(int theId) {
        // create query
        TypedQuery<Course> query = em.createQuery(
                            "FROM Course WHERE instructor.id=data", Course.class);
        query.setParameter("data", theId);

        List<Course> courses = query.getResultList();
        return courses;
    }
    
    // implement findInstructorByIdJoinFetch method
    @Override 
    public Instructor findInstructorByIdJoinFetch(int theId) {
        // create query
        TypedQuery<Instructor> query = em.createQuery(
                    "SELECT i from Instructor i " 
                    + "JOIN FETCH i.courses "
                    + "JOIN FETCH i.instructorDetails "
                    + "WHERE i.id=:data",
                    Instructor.class);
        query.setParameter("data", theId);

        Instructor in = query.getSingleResult();
        return in;
    }

    // implement update method
    @Override 
    @Transactional 
    public void update(Instructor in) {
        em.merge(in);
    }

    // implement deleteInstructorById method
    @Override 
    @Transactional 
    public void deleteInstructorById(int theId) {
        // retrieve instructor
        Instructor in = em.find(Instructor.class, theId);

        // break the associations of courses for instructor
        List<Course> courses = in.getCourses();
        for(Course c: courses) {
            // remove the instructor from the courses
            c.setInstructor(null);
        }

        // delete only the instructor
        // does not include courses associated
        em.remove(in);
    }

    // implement save Course method
    @Override 
    @Transactional 
    public void save(Course theCourse) {
        em.persist(theCourse);
    }

    // implement findCourseAndReviewsByCourseId method
    @Override 
    public Course findCourseAndReviewsByCourseId(int theId) {
        // create query
        TypedQuery<Course> query = em.createQuery(
                "SELECT c FROM Course c "
                + "JOIN FETCH c.review "
                + "WHERE c.id=:data", Course.class);
        query.setParameter("data", theId);

        // execute query
        Course course = query.getSingleResult();

        return course;
    }

    // implement findCourseAndStudentByCourseId method
    @Override 
    public Course findCourseAndStudentByCourseId(int theId) {
        // create query
        TypedQuery<Course> query = em.createQuery(
                "SELECT c FROM Course c "
                + "JOIN FETCH c.students "
                + "WHERE c.id=:data", Course.class);
        query.setParameter("data", theId);

        // execute query
        Course course = query.getSingleResult();

        return course;
    }

    // implement findStudentAndCoursesByStudent method
    @Override 
    public Student findStudentAndCoursesByStudentId(int theId) {
        // create query
        TypedQuery<Student> query = em.createQuery(
                "SELECT s FROM Student s "
                + "JOIN FETCH s.courses "
                + "WHERE s.id=:data", Student.class);
        query.setParameter("data", theId);

        // execute query
        Student student = query.getSingleResult();

        return student;
    }

    // implement update student method
    @Override 
    @Transactional 
    public void update(Student student) {
        em.merge(student);
    }

    // implement deleteCourseById method
    @Override 
    @Transactional 
    public void deleteCourseById(int theId) {
        // Retrieve the course
        Course c = em.find(Course.class, theId);

        // Delete the course
        em.remove(c);
    }

    // implement deleteStudentById method
    @Override 
    @Transactional 
    public void deleteStudentByID(int theId) {
        // retrieve student
        Student student = em.find(Student.class, theId);
    
        if (student != null) {
            // get the courses
            List<Course> courses = student.getCourses();

            // break the associations of courses for student
            for(Course c: courses) {
                c.getStudents().remove(student);
            }
        }

        // delete only the student
        // does not include
        em.remove(student);
    }

}
