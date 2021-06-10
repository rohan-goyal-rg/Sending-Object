package com.example.sendingobjects.models;

import java.io.Serializable;

/**
 * Student class which implements Serializable
 * {@link Serializable}
 */
public class Student implements Serializable {
    private String name, rollNo, phoneNo;
    private int gender;

    /**
     * Constructor to create  student object
     *
     * @param name    name of the student
     * @param rollNo  roll number of the student
     * @param phoneNo mobile number of the student
     * @param gender  gender of the student
     */
    public Student(String name, String rollNo, String phoneNo, int gender) {
        this.name = name;
        this.rollNo = rollNo;
        this.phoneNo = phoneNo;
        this.gender = gender;
    }

    /**
     *
     * @return gender of student
     */
    public int getGender() {
        return gender;
    }

    /**
     *
     * @param gender set gender of student
     */
    public void setGender(int gender) {
        this.gender = gender;
    }

    /**
     *
     * @return name of student
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name set name of student
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return rollNo of student
     */
    public String getRollNo() {
        return rollNo;
    }

    /**
     *
     * @param rollNo set rollNo
     */
    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    /**
     *
     * @return phoneNo of student
     */
    public String getPhoneNo() {
        return phoneNo;
    }

    /**
     *
     * @param phoneNo set phoneNo of student
     */
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
