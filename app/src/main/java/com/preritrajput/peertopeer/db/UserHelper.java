package com.preritrajput.peertopeer.db;

public class UserHelper {
    String fName, lName, phone, gender;
    int age;

    public UserHelper() {
    }

    public UserHelper(String fName, String lName, String phone, String gender, int age) {
        this.fName = fName;
        this.lName = lName;
        this.phone = phone;
        this.gender = gender;
        this.age = age;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
