package com.pojoStudent;

import com.fasterxml.jackson.annotation.JsonInclude;
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class StudentPojo {
   private int i;
    private String name;
    private int age;
    private String email;
    private String course;
    private String isActive;
    private String id;
    public StudentPojo(){}
    public StudentPojo(String name, int age, String email, String course, String isActive, String id) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.course = course;
        this.isActive = isActive;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String isActive() {
        return isActive;
    }

    public void setActive(String active) {
        this.isActive = active;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }




}
