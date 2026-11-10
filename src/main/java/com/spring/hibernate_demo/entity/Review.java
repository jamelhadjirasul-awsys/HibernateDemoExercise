package com.spring.hibernate_demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;;

/**
 * Represents a review comment for a course and maps to the {@code review} table.
 */
@Entity 
@Table(name="review")
public class Review {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name="id")
    private int id;

    @Column (name="comments")
    private String comments;

    public Review() {
    }

    public Review(String comments) {
        this.comments = comments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComments() {
        return comments;
    }

    @Override
    public String toString() {
        return "Review [id=" + id + ", comments=" + comments + "]";
    }

    public void setComments(String comments) {
        this.comments = comments;
    }


}
