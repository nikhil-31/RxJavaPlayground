package com.example.nikhilb.rxjavaplayground;

import android.support.annotation.NonNull;

class User {

    private String name;
    private String gender;
    private Address address;
    private String email;

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender( String gender ) {
        this.gender = gender;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress( Address address ) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail( String email ) {
        this.email = email;
    }

    @NonNull
    @Override
    public String toString() {
        return "Name " + name + " Gender " + gender + " address " + address + " email " + email;
    }
}

