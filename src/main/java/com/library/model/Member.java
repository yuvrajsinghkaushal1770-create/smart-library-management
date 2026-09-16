package com.library.model;

public class Member extends User {

    private String phone;
    private int maxBooks = 3;

    public Member() {}

    public Member(int id, String name, String email, String password, String phone) {
        super(id, name, email, password, "MEMBER");
        this.phone = phone;
    }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public int getMaxBooks() { return maxBooks; }
    public void setMaxBooks(int maxBooks) { this.maxBooks = maxBooks; }

    @Override
    public String toString() {
        return super.toString() + " | Phone: " + phone;
    }
}