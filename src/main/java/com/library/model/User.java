package com.library.model;

public class User extends Person {

    private String password;
    private String role;

    public User() {}

    public User(int id, String name, String email, String password, String role) {
        super(id, name, email);
        this.password = password;
        this.role = role;
    }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    @Override
public String toString() {
    return super.toString();
}
}