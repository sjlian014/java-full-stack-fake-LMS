package com.github.sjlian014.jlms.model;

public class EmailAddress {

    public enum EmailAddressType {
        UNIVERSITY, PERSONAL;
    }

    private String eAddr;
    private EmailAddressType type;

    public EmailAddress(String eAddr, EmailAddressType type) {
        this.eAddr = eAddr;
        this.type = type;
    }

    public String geteAddr() {
        return eAddr;
    }

    public void seteAddr(String eAddr) {
        this.eAddr = eAddr;
    }

    public EmailAddressType getType() {
        return type;
    }

    public void setType(EmailAddressType type) {
        this.type = type;
    }

}
