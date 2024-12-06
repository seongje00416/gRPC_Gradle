package clientNserver.server.entity;
import java.util.Vector;

public class Course {
    private int courseID;
    private String professor;
    private String courseName;
    private Vector<Integer> prerequisiteCourses;
    public void setCourseID(int courseID) {this.courseID = courseID;}
    public int getCourseID() {return this.courseID;}
    public void setProfessor(String professor) {this.professor = professor;}
    public String getProfessor() {return this.professor;}
    public void setCourseName(String courseName) {this.courseName = courseName;}
    public String getCourseName() {return this.courseName;}
    public void setPrerequisiteCourses(Vector<Integer> prerequisiteCourses) {this.prerequisiteCourses = prerequisiteCourses;}
    public Vector<Integer> getPrerequisiteCourses() {return this.prerequisiteCourses;}
}
