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
 * Represents an instructor and maps to the {@code instructor} table.
 * An instructor has associated instructor details and may be associated
 * with multiple courses.
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

    // Associates a course with this instructor and updates the course's instructor.
    public void add(Course tCourse) {
        // initializes list if it's null
        if(this.courses == null) {
            this.courses = new ArrayList<>();
        }

        // add the course to instructor's list
        this.courses.add(tCourse);
        
        // tell course who instructor is
        // 'this' refers to the current Instructor object
        tCourse.setInstructor(this);
    }
    
    public Instructor() {
    }

    public Instructor(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
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

    @Override
    public String toString() {
        return "Instructor [id=" + id + ", firstName=" + firstName 
                        + ", lastName=" + lastName + ", email=" + email 
                        + ", instructorDetails=" + instructorDetails + "]";
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }


}
