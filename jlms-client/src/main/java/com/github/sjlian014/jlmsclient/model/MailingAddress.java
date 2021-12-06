package com.github.sjlian014.jlmsclient.model;

import com.github.sjlian014.jlmsclient.exception.ValidationException;

public class MailingAddress {

    public enum AddrType {
        PERMANENT, LOCAL;
    }

    private String street;
    private String city;
    private String state;
    private Integer zip;
    private AddrType addressType;

    public MailingAddress() {}

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
        if(street.isBlank()) throw new ValidationException("street cannot be blank");
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        if(city.isBlank()) throw new ValidationException("city cannot be blank");
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        if(state.isBlank()) throw new ValidationException("city cannot be blank");
        this.state = state;
    }

    public Integer getZip() {
        return zip;
    }

    public void setZip(Integer zip) {
        int length = String.valueOf(zip).length();
        if(length != 5)
            throw new ValidationException("zip has to be a 5-digit number");
        this.zip = zip;
    }

    public AddrType getType() {
        return addressType;
    }

    public void setType(AddrType type) {
        this.addressType = type;
    }

    @Override
    public String toString() {
        return "%s, %s, %s %d [%s]".formatted(street, city, state, zip, addressType.name());
    }
}
