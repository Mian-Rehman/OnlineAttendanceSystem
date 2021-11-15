package com.example.onlineattendencesystem.Model;

public class StudentAttendaceClass {

    String currentMonth;
    String currentTime;
    String studentId;
    String currentDate;
    String studentPresent;
    String date;

    public StudentAttendaceClass(String currentMonth, String currentTime, String studentId, String currentDate, String studentPresent, String date) {
        this.currentMonth = currentMonth;
        this.currentTime = currentTime;
        this.studentId = studentId;
        this.currentDate = currentDate;
        this.studentPresent = studentPresent;
        this.date = date;
    }

    public StudentAttendaceClass() {
    }

    public String getCurrentMonth() {
        return currentMonth;
    }

    public void setCurrentMonth(String currentMonth) {
        this.currentMonth = currentMonth;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getStudentPresent() {
        return studentPresent;
    }

    public void setStudentPresent(String studentPresent) {
        this.studentPresent = studentPresent;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
