package com.github.sjlian014.jlms.model;

public class PhoneNumber {

    public enum PhoneNumberType {
        HOME, MOBILE;
    }

    private long phoneNum;
    private PhoneNumberType type;

    public PhoneNumber(long phoneNum, PhoneNumberType type) {
        this.phoneNum = phoneNum;
        this.type = type;
    }

    public long getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(long phoneNum) {
        this.phoneNum = phoneNum;
    }

    public PhoneNumberType getType() {
        return type;
    }

    public void setType(PhoneNumberType type) {
        this.type = type;
    }

}
