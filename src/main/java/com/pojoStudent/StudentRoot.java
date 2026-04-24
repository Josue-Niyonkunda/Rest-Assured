package pojo.student;

public class StudentRoot {
    public StudentRoot(){}
    public StudentRoot(StudentPojo studentPojo) {
        this.studentPojo = studentPojo;
    }

    public StudentPojo getStudentPojo() {
        return studentPojo;
    }

    public void setStudentPojo(StudentPojo studentPojo) {
        this.studentPojo = studentPojo;
    }

    StudentPojo studentPojo;
}
