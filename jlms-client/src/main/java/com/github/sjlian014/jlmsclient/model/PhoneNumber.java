package com.github.sjlian014.jlmsclient.model;

public class PhoneNumber {

    public enum PhoneNumberType {
        HOME, MOBILE;
    }

    private long phoneNum;
    private PhoneNumberType phoneNumType;

    public PhoneNumber() {}

    public PhoneNumber(long phoneNum, PhoneNumberType type) {
        this.phoneNum = phoneNum;
        this.phoneNumType = type;
    }

    public long getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(long phoneNum) {
        this.phoneNum = phoneNum;
    }

    public PhoneNumberType getType() {
        return phoneNumType;
    }

    public void setType(PhoneNumberType type) {
        this.phoneNumType = type;
    }

}
