package com.androidstrike.quizease.Model;

public class Courses {

//    model class to replicate the content of the Course table in the firebase

    private String Credit, CourseCode, Image, Lecturer, Title, Id;



    public Courses() {
    }

    public Courses(String credit,String code, String image, String lecturer, String title, String id) {
        Credit = credit;
        CourseCode = code;
        Image = image;
        Lecturer = lecturer;
        Title = title;
        Id = id;
    }

    public String getCredit() {
        return Credit;
    }

    public void setCredit(String credit) {
        Credit = credit;
    }

    public String getCourseCode() {
        return CourseCode;
    }

    public void setCourseCode(String courseCode) {
        CourseCode = courseCode;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getLecturer() {
        return Lecturer;
    }

    public void setLecturer(String lecturer) {
        Lecturer = lecturer;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}
