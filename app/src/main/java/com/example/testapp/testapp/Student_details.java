package com.example.testapp.testapp;

/**
 * Created by admin0 on 2/10/2017.
 */
public class Student_details {
    int rollNo;
    String name;
    String address;

    public Student_details(int rollNo, String name, String address) {
        this.rollNo = rollNo;
        this.name = name;
        this.address = address;
    }

    public int getRollNo() {
        return rollNo;
    }

    public void setRollNo(int rollNo) {

        this.rollNo = rollNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
