package server.repository;

import server.entity.Course;
import server.util.DatabaseConnection;

import java.sql.ResultSet;
import java.util.Vector;

public class CourseRepository {

    public Vector<Course> getAllCourse() {
        DatabaseConnection conn = new DatabaseConnection();
        String query = "SELECT * FROM course";
        ResultSet rs = conn.getResult(query);

        Vector<Course> courses = new Vector<Course>();
        try {
            while (rs.next()) {
                Course course = new Course();
                course.setCourseID(rs.getInt("courseID"));
                course.setProfessor(rs.getString("professor"));
                course.setCourseName(rs.getString("courseName"));

                String prerequisiteCoursesText = rs.getString("prerequisiteCoursesText");
                String[] prerequisiteCoursesSplit = prerequisiteCoursesText.split("/");
                Vector<Integer> prerequisiteCourseList = new Vector<Integer>();
                for (String prerequisiteCourse : prerequisiteCoursesSplit) {
                    prerequisiteCourseList.add(Integer.parseInt(prerequisiteCourse));
                }
                course.setPrerequisiteCourses(prerequisiteCourseList);
                courses.add(course);
            }
            return courses;
        } catch (Exception e) {
            System.out.println("문제가 발생했습니다.");
        }
        return null;
    }
}
