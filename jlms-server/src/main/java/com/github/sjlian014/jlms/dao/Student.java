package com.github.sjlian014.jlms.dao;

import java.util.Date;

public class Student {
    private long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private Date dob;


    public Student(String firstName, String middleName, String lastName, Date dob) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.dob = dob;
    }

    public Student(long id, String firstName, String middleName, String lastName, Date dob) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.dob = dob;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    @Override
    public String toString() {
        return "Student [dob=" + dob + ", firstName=" + firstName + ", id=" + id + ", lastName=" + lastName
                + ", middleName=" + middleName + "]";
    }

}
