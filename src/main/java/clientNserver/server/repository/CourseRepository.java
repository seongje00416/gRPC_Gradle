package clientNserver.server.repository;
import clientNserver.server.entity.Course;
import clientNserver.server.util.DatabaseConnection;

import java.sql.ResultSet;
import java.util.Vector;

public class CourseRepository {
    public Vector<Course> getAllCourse() {
        DatabaseConnection conn = new DatabaseConnection();
        String query = "SELECT * FROM courses";
        ResultSet rs = conn.getResult(query);
        Vector<Course> courses = new Vector<Course>();
        try {
            while ( true ) {
                Course course = new Course();
                course.setCourseID(rs.getInt("course_id"));
                course.setProfessor(rs.getString("professor"));
                course.setCourseName(rs.getString("course_name"));
                String prerequisiteCoursesText = rs.getString("pre_courses");
                String[] prerequisiteCoursesSplit = prerequisiteCoursesText.split(" ");
                Vector<Integer> prerequisiteCourseList = new Vector<Integer>();
                for (String prerequisiteCourse : prerequisiteCoursesSplit) {if( !prerequisiteCourse.equals("")) prerequisiteCourseList.add(Integer.parseInt(prerequisiteCourse));}
                course.setPrerequisiteCourses(prerequisiteCourseList);
                courses.add(course);
                if( !rs.next() ) break;
            }
        } catch (Exception e) {System.out.println("Course Repository Error");}
        return courses;
    }

    public int deleteCourse( Course course ){
        DatabaseConnection conn = new DatabaseConnection();
        String query = "DELETE FROM courses WHERE course_id = " + course.getCourseID();
        return conn.isSuccess( query );
    }
}
