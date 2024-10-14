package server.repository;

import server.entity.Student;
import server.util.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {

    public int signInStudent( Student student ) {
        DatabaseConnection conn = new DatabaseConnection();
        String courseText = "";
        for( int course : student.getClearCourses() ){
            courseText = course + " ";
        }
        String query =
                "INSERT INTO students (" +
                        "student_id, first_name, last_name, department, courses" +
                        ") VALUES (" +
                        student.getStudentID() + "," +
                        student.getFirstName() + "," +
                        student.getLastName() + ", " +
                        student.getDepartment() + ", " +
                        courseText + ");";
        return conn.isSuccess( query );
    }
    public int logInStudent( String id, String password ){
        DatabaseConnection conn = new DatabaseConnection();
        String query = "SELECT * FROM users WHERE id='" + id + "' AND password='" + password + "';";
        ResultSet rs = conn.getResult( query );
        try {
            if( rs != null ) return rs.getInt( "students_id" );
            else return 0;
        } catch ( SQLException e ){
            System.out.println( "No Result" );
        }
        return 0;
    }
}
