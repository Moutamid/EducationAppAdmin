package com.moutamid.educationappadmin.model;

public class SubjectModel {
    String ID, name, className;
    int count;

    public SubjectModel() {
    }

    public SubjectModel(String ID, String name, String className, int count) {
        this.ID = ID;
        this.name = name;
        this.className = className;
        this.count = count;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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
