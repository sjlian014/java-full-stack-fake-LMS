package com.github.sjlian014.jlmsclient.model;

public class MailingAddress {

    public enum AddrType {
        PERMANENT, LOCAL;
    }

    private String street;
    private String city;
    private String state;
    private Integer zip;
    private AddrType addressType;

    protected MailingAddress() {}

    public MailingAddress(String street, String city, String state, Integer zip, AddrType addressType) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.addressType = addressType;
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

    public Integer getZip() {
        return zip;
    }

    public void setZip(Integer zip) {
        this.zip = zip;
    }

    public AddrType getType() {
        return addressType;
    }

    public void setType(AddrType type) {
        this.addressType = type;
    }

}
