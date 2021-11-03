package com.example.onlineattendencesystem.Model;

public class TeacherClassData {

    String teacherName;
    String teacherId;
    String teacherEmail;
    String teacherDateOfBirth;
    String teacherQualification;
    String teacherPassword;

    public TeacherClassData() {
    }

    public TeacherClassData(String teacherName, String teacherId, String teacherEmail, String teacherDateOfBirth, String teacherQualification, String teacherPassword) {
        this.teacherName = teacherName;
        this.teacherId = teacherId;
        this.teacherEmail = teacherEmail;
        this.teacherDateOfBirth = teacherDateOfBirth;
        this.teacherQualification = teacherQualification;
        this.teacherPassword = teacherPassword;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherEmail() {
        return teacherEmail;
    }

    public void setTeacherEmail(String teacherEmail) {
        this.teacherEmail = teacherEmail;
    }

    public String getTeacherDateOfBirth() {
        return teacherDateOfBirth;
    }

    public void setTeacherDateOfBirth(String teacherDateOfBirth) {
        this.teacherDateOfBirth = teacherDateOfBirth;
    }

    public String getTeacherQualification() {
        return teacherQualification;
    }

    public void setTeacherQualification(String teacherQualification) {
        this.teacherQualification = teacherQualification;
    }

    public String getTeacherPassword() {
        return teacherPassword;
    }

    public void setTeacherPassword(String teacherPassword) {
        this.teacherPassword = teacherPassword;
    }
}
