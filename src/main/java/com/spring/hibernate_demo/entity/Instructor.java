package com.spring.hibernate_demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents an instructor mapped to the {@code instructor} database table.
 * Manages relationships with instructor details and taught courses.
 */
@Entity 
@Table (name="instructor")
public class Instructor {

    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="email")
    private String email;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="instructor_detail_id")
    private InstructorDetail instructorDetails;

    @OneToMany(mappedBy="instructor", fetch= FetchType.LAZY, 
                                      cascade= {CascadeType.DETACH,
                                                CascadeType.MERGE,
                                                CascadeType.PERSIST,
                                                CascadeType.REFRESH})
    private List<Course> courses;

    /** 
     * Default no-arg constructor required by JPA. 
     */
    public Instructor() {
    }

    /**
     * Constructs a new Instructor with personal details.
     * @param firstName the instructor's first name
     * @param lastName the instructor's last name
     * @param email the instructor's email address
     */
    public Instructor(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    /**
     * Convenience method to associate a course with 
     * this instructor bi-directionally.
     * @param tCourse the course to assign to this instructor
     */
    public void add(Course tCourse) {
        if(this.courses == null) {
            this.courses = new ArrayList<>();
        }
        this.courses.add(tCourse);
        tCourse.setInstructor(this);
    }
    
    @Override
    public String toString() {
        return "Instructor [id=" + id + ", firstName=" + firstName 
                        + ", lastName=" + lastName + ", email=" + email 
                        + ", instructorDetails=" + instructorDetails + "]";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public InstructorDetail getInstructorDetails() {
        return instructorDetails;
    }

    public void setInstructorDetails(InstructorDetail instructorDetails) {
        this.instructorDetails = instructorDetails;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }


}
