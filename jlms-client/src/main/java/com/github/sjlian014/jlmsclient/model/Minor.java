package com.github.sjlian014.jlmsclient.model;

import java.util.List;


public class Minor {

    private Long id;
    private String name;
    private Category category;

    public enum Category {
        AGRICULTURE, ARTS, BIOLOGY, BUSINESS, COMMUNICATIONS, COMPUTERS, EDUCATION, ENGINEERING, HEALTH, HUMANITIES,
        INDUSTRIAL_ARTS, INTERDISCIPLINARY, LAW, PHYSICAL_SCIENCES, PSYCHOLOGY, SOCIAL_SCIENCE
    }

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

    @Override
    public String toString() {
        return "%s [%s]".formatted(name, category);
    }
}
