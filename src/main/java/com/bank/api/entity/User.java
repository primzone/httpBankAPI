package com.bank.api.entity;

public class User {
    private long id;
    private String name;
    private String lastname;

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User(String name) {
        this.name = name;
    }

    public User() {
    }

    public User(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
