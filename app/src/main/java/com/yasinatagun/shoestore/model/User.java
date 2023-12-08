package com.yasinatagun.shoestore.model;

import com.yasinatagun.shoestore.model.enums.Gender;

public class User {
    private String firstName;
    private String lastName;
    private String adress;
    private Gender gender;

    public User(String firstName, String lastName, String adress, Gender gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.adress = adress;
        this.gender = gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
