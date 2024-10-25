package server.repository;

import server.entity.Student;
import server.util.DatabaseConnection;
import java.sql.ResultSet;
import java.util.Vector;

public class StudentRepository {
    public Vector<Student> getAllStudent(){
        DatabaseConnection conn = new DatabaseConnection();
        String query = "SELECT * FROM students";
        ResultSet rs = conn.getResult(query);
        Vector<Student> students = new Vector<Student>();
        try{
            while( true ){
                Student student = new Student();
                student.setStudentID( rs.getInt("student_id") );
                student.setFirstName( rs.getString("first_name") );
                student.setLastName( rs.getString("last_name") );
                student.setDepartment( rs.getString("department") );
                String clearCourseText = rs.getString("courses");
                String[] clearCourseSplit = clearCourseText.split(" ");
                Vector<Integer> clearCourseList = new Vector<Integer>();
                for( String courseID : clearCourseSplit){clearCourseList.add( Integer.parseInt( courseID ) );}
                student.setClearCourses( clearCourseList );
                students.add( student );
                if( !rs.next() ) break;
            }
        } catch( Exception e ){System.out.println( "Code 703: 문제가 발생했습니다." );}
        return students;
    }
    public int deleteStudent( Student student ){
        DatabaseConnection conn = new DatabaseConnection();
        String query = "DELETE FROM students WHERE student_id=" + student.getStudentID();
        return conn.isSuccess( query );
    }
}
