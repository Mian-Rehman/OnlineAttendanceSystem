package com.example.onlineattendencesystem.Model;

public class AddClassData {

    String classYear;
    String addClass;

    public AddClassData() {
    }

    public AddClassData(String classYear, String addClass) {
        this.classYear = classYear;
        this.addClass = addClass;
    }

    public String getClassYear() {
        return classYear;
    }

    public void setClassYear(String classYear) {
        this.classYear = classYear;
    }

    public String getAddClass() {
        return addClass;
    }

    public void setAddClass(String addClass) {
        this.addClass = addClass;
    }
}


