package com.moutamid.educationappadmin.model;

public class ClassModel {
    String ID, name;

    public ClassModel() {
    }

    public ClassModel(String ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
