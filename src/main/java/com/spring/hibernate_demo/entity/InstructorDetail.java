package com.spring.hibernate_demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.CascadeType;

/**
 * Represents an instructor's subject and hobby details mapped to the
 * {@code instructor_detail} database table.
 */
@Entity
@Table(name="instructor_detail")
public class InstructorDetail {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="subject")
    private String subject;

    @Column(name="hobby")
    private String hobby;

    //@OneToOne(mappedBy="instructorDetails", cascade= CascadeType.ALL)
    @OneToOne(mappedBy="instructorDetails", cascade= {CascadeType.DETACH,
                                                      CascadeType.MERGE,
                                                      CascadeType.PERSIST,
                                                      CascadeType.REFRESH})
    
    private Instructor instructor;

    /** 
     * Default no-arg constructor required by JPA. 
     */
    public InstructorDetail() {
    }

    /**
     * Constructs instructor details with a specified subject and hobby.
     * @param subject the main subject taught by the instructor
     * @param hobby the instructor's primary hobby
     */
    public InstructorDetail(String subject, String hobby) {
        this.subject = subject;
        this.hobby = hobby;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    /**
     * Sets the instructor associated with these details.
     * @param instructor the instructor object to link
     */
    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    @Override
    public String toString() {
        return "InstructorDetail [id=" + id + ", subject=" 
                                + subject + ", hobby=" + hobby + "]";
    }
}
