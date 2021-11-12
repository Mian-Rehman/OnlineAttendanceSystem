package com.example.onlineattendencesystem.Model;

public class TeacherImage {

    String teacherID;
    String newImage;

    public TeacherImage(String teacherID, String newImage) {
        this.teacherID = teacherID;
        this.newImage = newImage;
    }

    public TeacherImage() {
    }

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }

    public String getNewImage() {
        return newImage;
    }

    public void setNewImage(String newImage) {
        this.newImage = newImage;
    }
}
