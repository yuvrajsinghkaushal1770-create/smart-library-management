package com.library.model;

public class Librarian extends User {

    private String employeeCode;

    public Librarian() {}

    public Librarian(int id, String name, String email, String password, String employeeCode) {
        super(id, name, email, password, "LIBRARIAN");
        this.employeeCode = employeeCode;
    }

    public String getEmployeeCode() { return employeeCode; }
    public void setEmployeeCode(String employeeCode) { this.employeeCode = employeeCode; }

    @Override
    public String toString() {
        return super.toString() + " | EmpCode: " + employeeCode;
    }
}