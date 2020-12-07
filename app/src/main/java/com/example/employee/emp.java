package com.example.employee;

import java.io.Serializable;

public class emp implements Serializable {

    private int id;
    private String name;
    private String gender;
    private float salary;
    private float sales;
    private float rate;

    public emp() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public float getSales() {
        return sales;
    }

    public void setSales(float sales) {
        this.sales = sales;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public emp(int id, String name, String gender, float salary, float sales, float rate) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.salary = salary;
        this.sales = sales;
        this.rate = rate;
    }
}
