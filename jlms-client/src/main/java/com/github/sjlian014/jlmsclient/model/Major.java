package com.github.sjlian014.jlmsclient.model;

public class Major {

    private Long id;
    private String name;
    private String description;
    // TODO add a list of requirements

    public Major() {}

    public Major(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Major(String name, String description) {
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
