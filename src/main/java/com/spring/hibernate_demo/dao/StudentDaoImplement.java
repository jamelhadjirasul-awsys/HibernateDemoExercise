package com.spring.hibernate_demo.dao;

import org.springframework.stereotype.Repository;
import com.spring.hibernate_demo.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 * Implementation of {@link StudentDao} providing database operations for 
 * {@link Student} entities using JPA {@link EntityManager}.
 */
@Repository 
public class StudentDaoImplement implements StudentDao {

    private EntityManager em;

    /**
     * Constructs the DAO implementation with injected EntityManager.
     * @param em the JPA entity manager instance
     */
    public StudentDaoImplement(EntityManager em) {
        this.em = em;
    }

    /**
     * Persists a new student entity in the database.
     * @param theStudent the student entity to save
     */
    @Override 
    @Transactional
    public void save(Student theStudent) {
        em.persist(theStudent);
    }

    /**
     * Retrieves a student by primary key ID.
     * @param id the student primary key
     * @return the matching {@link Student} entity or null if not found
     */
    @Override
    public Student findById(Integer id) {
        return em.find(Student.class, id);
    }

    /**
     * Fetches all students ordered by last name.
     * @return list of all stored student entities
     */
    @Override
    public List<Student> findAll() {
        TypedQuery<Student> query = 
            em.createQuery("FROM Student order by lastName", Student.class);

        return query.getResultList();
    }

    /**
     * Searches for students with a matching last name.
     * @param lastname the last name to search for
     * @return list of matching student entities
     */
    @Override
    public List<Student> findByLastName(String lastname) {
        TypedQuery<Student> query = 
            em.createQuery("FROM Student WHERE lastName=:data", Student.class);

        query.setParameter("data", lastname);
        return query.getResultList();
    }

    /**
     * Updates an existing student record in the database.
     * @param theStudent the student entity containing updated values
     */
    @Override
    @Transactional
    public void update(Student theStudent) {
        em.merge(theStudent);
    }

    /**
     * Deletes a single student record by primary key ID.
     * @param id the student ID to delete
     */
    @Override
    @Transactional
    public void delete(Integer id) {
        Student student = em.find(Student.class, id);
        em.remove(student);
    }

    /**
     * Deletes all student records from the database table.
     * @return the number of student rows deleted
     */
    @Override
    @Transactional
    public int deleteAll() {
        int numRows = em.createQuery("DELETE FROM Student").executeUpdate();
        return numRows;
    }
}
