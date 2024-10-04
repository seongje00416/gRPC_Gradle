package server.repository;

import server.entity.Student;
import server.util.DatabaseConnection;

import java.sql.ResultSet;
import java.util.Vector;

public class StudentRepository {

    public Vector<Student> getAllStudent(){
        DatabaseConnection conn = new DatabaseConnection();
        String query = "SELECT * FROM student";
        ResultSet rs = conn.getResult(query);

        Vector<Student> students = new Vector<Student>();
        try{
            while( rs.next()){
                Student student = new Student();
                student.setStudentID( rs.getInt("studentID") );
                student.setFirstName( rs.getString("firstName") );
                student.setLastName( rs.getString("lastName") );
                student.setDepartment( rs.getString("department") );

                String clearCourseText = rs.getString("clearCourseText");
                String[] clearCourseSplit = clearCourseText.split("/");
                Vector<Integer> clearCourseList = new Vector<Integer>();
                for( String courseID : clearCourseSplit){
                    clearCourseList.add( Integer.parseInt( courseID ) );
                }
                student.setClearCourses( clearCourseList );
                students.add( student );
            }
            return students;
        } catch( Exception e ){
            System.out.println( "문제가 발생했습니다." );
        }
        return null;
    }

}
