package com.github.sjlian014.jlms.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;


@Entity
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "students"})
public class Minor {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Category category;

    public enum Category {
        AGRICULTURE, ARTS, BIOLOGY, BUSINESS, COMMUNICATIONS, COMPUTERS, EDUCATION, ENGINEERING, HEALTH, HUMANITIES,
        INDUSTRIAL_ARTS, INTERDISCIPLINARY, LAW, PHYSICAL_SCIENCES, PSYCHOLOGY, SOCIAL_SCIENCE
    }

    @OneToMany(mappedBy = "minor")
    private List<Student> students = new java.util.ArrayList<>();

    public List<Student> getStudents() {
        return students;
    }
    // TODO add a list of requirements

    public Minor(Long id, String name, Category category) {
        this.id = id;
        this.name = name;
        this.category = category;
        //this.students = students;
    }

    public Minor() {}

    public Minor(String name, Category category) {
        this.name = name;
        this.category = category;
    }

    public void copyFrom(Minor minor2c) {
        this.name = minor2c.name;
        this.category = minor2c.category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
