package com.github.sjlian014.jlms.model;

public class MailingAddress {

    public enum AddrType {
        PERMANENT, LOCAL;
    }

    private String street;
    private String city;
    private String state;
    private int zip;
    private AddrType type;

    public MailingAddress(String street, String city, String state, int zip, AddrType type) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.type = type;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public AddrType getType() {
        return type;
    }

    public void setType(AddrType type) {
        this.type = type;
    }

}
