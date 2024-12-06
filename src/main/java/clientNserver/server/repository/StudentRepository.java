package clientNserver.server.repository;

import clientNserver.server.entity.Student;
import clientNserver.server.util.DatabaseConnection;
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
    public Student getStudentByID( int studentID ) {
        DatabaseConnection conn = new DatabaseConnection();
        String query = "SELECT * FROM students WHERE student_id=" + studentID;
        ResultSet rs = conn.getResult( query );
        Student student = new Student();
        try{
            student.setStudentID( rs.getInt( "student_id" ) );
            student.setFirstName( rs.getString("first_name") );
            student.setLastName( rs.getString("last_name") );
            student.setDepartment( rs.getString("department") );
            Vector<Integer> clearCourses = new Vector<Integer>();
            String[] clearCourse = rs.getString("courses").split(" ");
            for( String courseID : clearCourse) clearCourses.add( Integer.parseInt( courseID ) );
            student.setClearCourses( clearCourses );
        } catch( Exception e ){
            System.out.println( "Error 704: Load Student By ID Error" );
            student = null;
        }
        return student;
    }
    public int updateStudent( Student student ){
        DatabaseConnection conn = new DatabaseConnection();
        String clearCourses = "";
        for( Integer course : student.getClearCourses() ) clearCourses += course + " ";
        String query = "UPDATE students SET "
                + "first_name='" + student.getFirstName()
                + "', last_name='" + student.getLastName()
                + "', department='" + student.getDepartment()
                + "', courses='" + clearCourses.trim()
                + "' WHERE student_id=" + student.getStudentID();
        int result = conn.isSuccess( query );
        if( result == -1 ) return -1;
        else return student.getStudentID();
    }
}
