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
 * Provides DAO implementation for managing Instructor, Course, 
 * Review, and Student entities using JPA EntityManager.
 */
@Repository 
public class AppDaoImplement implements AppDao {

    private EntityManager em;

    /**
     * Constructs the DAO implementation with injected EntityManager.
     * @param em the JPA entity manager instance
     */
    public AppDaoImplement(EntityManager em) {
        this.em = em;
    }

    /**
     * Persists a new instructor along with cascade-enabled relationships.
     * @param theInstructor the instructor entity to save
     */
    @Override 
    @Transactional 
    public void save(Instructor theInstructor) {
        em.persist(theInstructor);
    }

    /**
     * Retrieves an instructor by primary key ID.
     * @param theId the instructor ID
     * @return the matching {@link Instructor} or null if not found
     */
    @Override 
    public Instructor findInstructorById(int theId) {
        return em.find(Instructor.class, theId);
    }

    /**
     * Deletes an instructor along with their 
     * associated courses via cascade remove.
     * @param theId the instructor ID to delete
     */
    @Override 
    @Transactional 
    public void deleteInstructorWithCoursesById(int theId) {
        Instructor in = em.find(Instructor.class, theId);
        em.remove(in);
    }

   /**
     * Retrieves instructor details by primary key ID.
     * @param theId the instructor detail ID
     * @return the matching {@link InstructorDetail} entity
     */
    @Override
    public InstructorDetail findInstructorDetailById(int theId) {
        return em.find(InstructorDetail.class, theId);
    }

    // @Override
    // @Transactional 
    // public void deleteInstructorDetail(int theId) {
    //     // retrieve instructor detail
    //     InstructorDetail instructorDetails 
    //          = em.find(InstructorDetail.class, theId);

    //     // delete instructor detail
    //     em.remove(instructorDetails);
    // }

    /**
     * Deletes instructor details without deleting the 
     * associated instructor (non-cascading).
     * @param theId the instructor detail ID to remove
     */
    @Override
    @Transactional 
    public void deleteInstructorDetail(int theId) {
        InstructorDetail instructorDetails = em.find(InstructorDetail.class, theId);
        instructorDetails.getInstructor().setInstructorDetails(null);
        em.remove(instructorDetails);
    }

    /**
     * Retrieves all courses taught by a specific instructor ID.
     * @param theId the instructor ID
     * @return list of courses linked to the given instructor
     */
    @Override 
    public List<Course> findCoursesByInstructorId(int theId) {
        TypedQuery<Course> query = em.createQuery(
                            "FROM Course WHERE instructor.id=data", Course.class);
        query.setParameter("data", theId);

        List<Course> courses = query.getResultList();
        return courses;
    }
    
    /**
     * Fetches an instructor along with courses and details 
     * in a single SQL query using JOIN FETCH.
     * @param theId the instructor ID
     * @return the populated {@link Instructor} object
     */
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

    /**
     * Updates an existing instructor's record in the database.
     * @param in the instructor entity with updated properties
     */
    @Override 
    @Transactional 
    public void update(Instructor in) {
        em.merge(in);
    }

    /**
     * Deletes an instructor while preserving their associated 
     * courses (unlinks the instructor).
     * @param theId the instructor ID to delete
     */
    @Override 
    @Transactional 
    public void deleteInstructorById(int theId) {
        Instructor in = em.find(Instructor.class, theId);

        List<Course> courses = in.getCourses();
        for(Course c: courses) {
            c.setInstructor(null);
        }
        em.remove(in);
    }

    /**
     * Persists a course entity.
     * @param theCourse the course to save
     */
    @Override 
    @Transactional 
    public void save(Course theCourse) {
        em.persist(theCourse);
    }

    /**
     * Fetches a course along with its associated reviews using JOIN FETCH.
     * @param theId the course ID
     * @return the {@link Course} containing initialized reviews
     */
    @Override 
    public Course findCourseAndReviewsByCourseId(int theId) {
        TypedQuery<Course> query = em.createQuery(
                "SELECT c FROM Course c "
                + "JOIN FETCH c.review "
                + "WHERE c.id=:data", Course.class);
        query.setParameter("data", theId);

        Course course = query.getSingleResult();
        return course;
    }

    /**
     * Fetches a course along with enrolled students using JOIN FETCH.
     * @param theId the course ID
     * @return the {@link Course} containing initialized student list
     */
    @Override 
    public Course findCourseAndStudentByCourseId(int theId) {
        TypedQuery<Course> query = em.createQuery(
                "SELECT c FROM Course c "
                + "JOIN FETCH c.students "
                + "WHERE c.id=:data", Course.class);
        query.setParameter("data", theId);

        Course course = query.getSingleResult();
        return course;
    }

    /**
     * Fetches a student along with enrolled courses using JOIN FETCH.
     * @param theId the student ID
     * @return the {@link Student} containing initialized course list
     */
    @Override 
    public Student findStudentAndCoursesByStudentId(int theId) {
        TypedQuery<Student> query = em.createQuery(
                "SELECT s FROM Student s "
                + "JOIN FETCH s.courses "
                + "WHERE s.id=:data", Student.class);
        query.setParameter("data", theId);

        Student student = query.getSingleResult();
        return student;
    }

    /**
     * Updates an existing student entity.
     * @param student the student entity to update
     */
    @Override 
    @Transactional 
    public void update(Student student) {
        em.merge(student);
    }

    /**
     * Deletes a course by its primary key ID.
     * @param theId the course ID to remove
     */
    @Override 
    @Transactional 
    public void deleteCourseById(int theId) {
        Course c = em.find(Course.class, theId);
        em.remove(c);
    }

    /**
     * Deletes a student by ID while unlinking them from enrolled courses 
     * to preserve course data.
     * @param theId the student ID to remove
     */
    @Override 
    @Transactional 
    public void deleteStudentByID(int theId) {
        Student student = em.find(Student.class, theId);
    
        if (student != null) {
            List<Course> courses = student.getCourses();

            for(Course c: courses) {
                c.getStudents().remove(student);
            }
        }
        em.remove(student);
    }

}
