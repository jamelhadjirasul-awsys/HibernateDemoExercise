package com.spring.hibernate_demo.dao;

import org.springframework.stereotype.Repository;
import com.spring.hibernate_demo.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 * StudentDaoImplement class implements the StudentDao interface and provides 
 * the implementation for CRUD operations on Student entities.
 */
@Repository 
public class StudentDaoImplement implements StudentDao {

    // define field for entity manager
    private EntityManager em;

    // inject entity manager using constructor injection
    public StudentDaoImplement(EntityManager em) {
        this.em = em;
    }

    // implement save method
    @Override 
    @Transactional
    public void save(Student theStudent) {
        em.persist(theStudent);
    }

    // implement findById method
    @Override
    public Student findById(Integer id) {
        return em.find(Student.class, id);
    }

    // implement findAll method
    @Override
    public List<Student> findAll() {
        // create query
        TypedQuery<Student> query = em.createQuery("FROM Student order by lastName", Student.class);

        // return query results
        return query.getResultList();
    }

    // implement findByLastName method
    @Override
    public List<Student> findByLastName(String lastname) {
        // create query
        TypedQuery<Student> query = 
            em.createQuery("FROM Student WHERE lastName=:data", Student.class);

        // set query parameter
        query.setParameter("data", lastname);
        // return query results
        return query.getResultList();
    }

    // implement update method
    @Override
    @Transactional
    public void update(Student theStudent) {
        em.merge(theStudent);
    }

    // implement delete method
    @Override
    @Transactional
    public void delete(Integer id) {
        // retrieve student based on the id
        Student student = em.find(Student.class, id);
        
        // delete the student
        em.remove(student);
    }

    // implement deleteAll method
    @Override
    @Transactional
    public int deleteAll() {
        // create query
        int numRows = em.createQuery("DELETE FROM Student").executeUpdate();
        return numRows;
    }
}
