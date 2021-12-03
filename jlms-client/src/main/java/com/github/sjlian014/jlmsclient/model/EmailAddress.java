package com.github.sjlian014.jlmsclient.model;

public class EmailAddress {

    public enum EmailAddressType {
        UNIVERSITY, PERSONAL;
    }

    private String emailAddress;
    private EmailAddressType emailAddressType;

    protected EmailAddress() {}

    public EmailAddress(String eAddr, EmailAddressType emailAddressType) {
        this.emailAddress = eAddr;
        this.emailAddressType = emailAddressType;
    }

    public String geteAddr() {
        return emailAddress;
    }

    public void seteAddr(String eAddr) {
        this.emailAddress = eAddr;
    }

    public EmailAddressType getType() {
        return emailAddressType;
    }

    public void setType(EmailAddressType type) {
        this.emailAddressType = type;
    }

}
