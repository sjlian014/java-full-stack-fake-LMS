package com.github.sjlian014.jlms.model;

import javax.persistence.Embeddable;

@Embeddable
public class PhoneNumber {

    public enum PhoneNumberType {
        HOME, MOBILE;
    }

    private long phoneNum;
    private PhoneNumberType phoneNumType;

    protected PhoneNumber() {}

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
