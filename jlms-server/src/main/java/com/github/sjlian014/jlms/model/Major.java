package com.github.sjlian014.jlms.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table
public class Major {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    // TODO add a list of requirements

    protected Major() {}

    public Major(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public void copyFrom(Major major2c) {
        this.name = major2c.name;
        this.description = major2c.description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
