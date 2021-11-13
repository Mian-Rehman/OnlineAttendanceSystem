package com.example.onlineattendencesystem.Model;

public class StudentImage {

    String studentId;
    String newImage;

    public StudentImage(String studentId, String newImage) {
        this.studentId = studentId;
        this.newImage = newImage;
    }

    public StudentImage() {
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getNewImage() {
        return newImage;
    }

    public void setNewImage(String newImage) {
        this.newImage = newImage;
    }
}
