package com.preritrajput.peertopeer.db;

public class UserHelper {
    String fName, lName, mobile, email;

    public UserHelper() {
    }

    public UserHelper(String fName, String lName, String mobile, String email) {
        this.fName = fName;
        this.lName = lName;
        this.mobile = mobile;
        this.email = email;

    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
