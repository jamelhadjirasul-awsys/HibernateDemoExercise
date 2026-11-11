package com.spring.hibernate_demo.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents a course mapped to the {@code course} database table.
 * Manages relationships with an instructor, reviews, and enrolled students.
 */
@Entity
@Table(name="course")
public class Course {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column (name = "id")
    private int id;

    @Column (name = "title")
    private String title;

    @ManyToOne (cascade = {CascadeType.DETACH,
                            CascadeType.MERGE,
                            CascadeType.PERSIST,
                            CascadeType.REFRESH})
    @JoinColumn (name = "instructor_id")
    private Instructor instructor;

    @OneToMany (fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn (name = "course_id")
    private List<Review> review;

    @ManyToMany (fetch = FetchType.LAZY,
                cascade = {CascadeType.DETACH,
                            CascadeType.MERGE,
                            CascadeType.PERSIST,
                            CascadeType.REFRESH})
    @JoinTable (name = "course_student",
                joinColumns = @JoinColumn(name = "course_id"),
                inverseJoinColumns = @JoinColumn(name = "student_id"))
    private List<Student> students;

    /** 
     * Default no-arg constructor required by JPA. 
     */
    public Course() {
    }

    /**
     * Constructs a new Course with the specified title.
     * @param title the course title
     */
    public Course(String title) {
        this.title = title;
    }

    /**
     * Convenience method to enroll a student in this course.
     * @param stud the student to add
     */
    public void addStudent(Student stud) {
        if(students == null) {
            students = new ArrayList<>();
        }
        students.add(stud);
    }

    /**
     * Convenience method to add a review to this course.
     * @param rev the review to add
     */
    public void addReview(Review rev) {
        if(review == null) {
            review = new ArrayList<>();
        }
        review.add(rev);
    }

    @Override
    public String toString() {
        return "Course [id=" + id 
                    + ", title=" 
                    + title + "]";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public List<Review> getReview() {
        return review;
    }

    public void setReview(List<Review> review) {
        this.review = review;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }



    
}
