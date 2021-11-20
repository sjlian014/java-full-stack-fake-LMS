package com.github.sjlian014.jlms.model;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

// @Entity
// @Table
public class Student {

    public enum Major {
        MAJOR1;
    }

    public enum Minor {
        Minor1;
    }

    public enum EnrollmentStatus {
        ENROLLED, NOT_ENROLLED, WITHDRAWN;
    }

    // @Id
    // @GeneratedValue
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private Date dob;
    private Date doa; // date of acceptance
    private MailingAddress mailingAddress;
    private ArrayList<EmailAddress> emailAddresses;
    private ArrayList<PhoneNumber> phoneNumbers;
    private Semester startSemester;
    private Major major;
    private Minor minor;
    private EnrollmentStatus currentStatus;

    public Student(String firstName, String middleName, String lastName, Date dob) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.dob = dob;
    }

    public Student(Long id, String firstName, String middleName, String lastName, Date dob) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.dob = dob;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Date getDoa() {
        return doa;
    }

    public void setDoa(Date doa) {
        this.doa = doa;
    }

    public MailingAddress getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(MailingAddress mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    public ArrayList<EmailAddress> getEmailAddresses() {
        return emailAddresses;
    }

    public void setEmailAddresses(ArrayList<EmailAddress> emailAddresses) {
        this.emailAddresses = emailAddresses;
    }

    public ArrayList<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(ArrayList<PhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public Semester getStartSemester() {
        return startSemester;
    }

    public void setStartSemester(Semester startSemester) {
        this.startSemester = startSemester;
    }

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    public Minor getMinor() {
        return minor;
    }

    public void setMinor(Minor minor) {
        this.minor = minor;
    }

    public EnrollmentStatus getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(EnrollmentStatus currentStatus) {
        this.currentStatus = currentStatus;
    }

    @Override
    public String toString() {
        return "Student [dob=" + dob + ", firstName=" + firstName + ", id=" + id + ", lastName=" + lastName
                + ", middleName=" + middleName + "]";
    }

}
