package com.github.sjlian014.jlmsclient.model;

import java.util.List;
import java.util.Date;

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

    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private Date dob;
    private Date doa; // date of acceptance
    private MailingAddress mailingAddress;
    private List<EmailAddress> emailAddresses;
    private List<PhoneNumber> phoneNumbers;
    private Semester startSemester;
    private Major major;
    private Minor minor;
    private EnrollmentStatus currentStatus;

    public Student() { }

    public Student(String firstName, String middleName, String lastName, Date dob) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.dob = dob;
    }

    public Student(Long id, String firstName, String middleName, String lastName, Date dob, Date doa,
            MailingAddress mailingAddress, List<EmailAddress> emailAddresses, List<PhoneNumber> phoneNumbers,
            Semester startSemester, Major major, Minor minor, EnrollmentStatus currentStatus) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.dob = dob;
        this.doa = doa;
        this.mailingAddress = mailingAddress;
        this.emailAddresses = emailAddresses;
        this.phoneNumbers = phoneNumbers;
        this.startSemester = startSemester;
        this.major = major;
        this.minor = minor;
        this.currentStatus = currentStatus;
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

    public List<EmailAddress> getEmailAddresses() {
        return emailAddresses;
    }

    public void setEmailAddresses(List<EmailAddress> emailAddresses) {
        this.emailAddresses = emailAddresses;
    }

    public List<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<PhoneNumber> phoneNumbers) {
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
        return "Student [currentStatus=" + currentStatus + ", doa=" + doa + ", dob=" + dob + ", emailAddresses="
                + emailAddresses + ", firstName=" + firstName + ", id=" + id + ", lastName=" + lastName
                + ", mailingAddress=" + mailingAddress + ", major=" + major + ", middleName=" + middleName + ", minor="
                + minor + ", phoneNumbers=" + phoneNumbers + ", startSemester=" + startSemester + "]";
    }

}
