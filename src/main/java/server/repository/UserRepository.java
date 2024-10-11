package server.repository;

import server.entity.Student;
import server.util.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {

    public void signInStudent( Student student ) {
        DatabaseConnection conn = new DatabaseConnection();
        String query =
                "INSERT INTO students (" +
                        "" +
                        "VALUES ";
        ResultSet rs = conn.getResult( query );
    }
    public int logInStudent( String id, String password ){
        DatabaseConnection conn = new DatabaseConnection();
        String query = "SELECT * FROM students WHERE student_id='" + id + "' AND password='" + password + "';";
        ResultSet rs = conn.getResult( query );

        try {
            if( rs.next() ) return rs.getInt( "student_id" );
            else return 0;
        } catch ( SQLException e ){
            System.out.println( "회원 정보가 없습니다." );
        }
        return 0;
    }
}
