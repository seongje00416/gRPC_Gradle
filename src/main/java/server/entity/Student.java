package server.entity;

import java.util.Vector;

public class Student {
    private int studentID;
    private String lastName;
    private String firstName;
    private String department;
    private Vector<Integer> clearCourses;

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }
    public int getStudentID() {
        return this.studentID;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getLastName() {
        return this.lastName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getFirstName() {
        return this.firstName;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    public String getDepartment() {
        return this.department;
    }
    public void setClearCourses(Vector<Integer> clearCourses) {
        this.clearCourses = clearCourses;
    }
    public Vector<Integer> getClearCourses() {
        return this.clearCourses;
    }
}
