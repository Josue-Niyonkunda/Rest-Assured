package com.studentCollection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude()
public class StudentsRoot {
    Students students;
    public StudentsRoot(){}
    public StudentsRoot(Students students) {
        this.students = students;
    }

    public Students getStudents() {
        return students;
    }

    public void setStudents(Students students) {
        this.students = students;
    }


}
