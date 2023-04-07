package com.admin;

public class Employee {
    private String name;
    private int id;
    private  String address;
    private long salary;
    private String email;
    private long phone;

    public String getName() {
        return name;
    }
    public int getId() {
        return id;
    }
    public String getAddress() {
        return address;
    }
    public long getSalary() {
        return salary;
    }
    public String getEmail() {
        return email;
    }
    public long getPhone() {
        return phone;
    }

    public Employee(String name, int id, String address, long salary, String email, long phone) {
        this.name = name;
        this.id = id;
        this.address = address;
        this.salary = salary;
        this.email = email;
        this.phone = phone;
    }
    public Employee(){}

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", address='" + address + '\'' +
                ", salary=" + salary +
                ", email='" + email + '\'' +
                ", phone=" + phone +
                '}';
    }
}
