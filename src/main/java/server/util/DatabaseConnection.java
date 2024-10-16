package server.util;
import server.common.DatabaseConstants;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseConnection {
    private final String JDBC_DRIVER = DatabaseConstants.DATABASE_DRIVER;
    private final String DB_URL = DatabaseConstants.DATABASE_URL;
    private final String USER = DatabaseConstants.DATABASE_USER;
    private final String PASS = DatabaseConstants.DATABASE_PASSWORD;
    private Connection conn = null;
    private Statement stmt = null;
    public DatabaseConnection() {
        try{
            Class.forName( this.JDBC_DRIVER );
            this.conn = DriverManager.getConnection( this.DB_URL, this.USER, this.PASS );
        } catch( Exception e ) {e.printStackTrace();}
    }
    public ResultSet getResult(String sql) {
        try{
            this.stmt = this.conn.createStatement();
            ResultSet tmp = this.stmt.executeQuery(sql);
            System.out.println( "Result: " + tmp.next() );
            return tmp;
        }
        catch ( Exception e ) {System.out.println( "Code 701: 문제가 발생했습니다." );}
        return null;
    }
    public int isSuccess( String sql ){
        try{
            this.stmt = this.conn.createStatement();
            int result = this.stmt.executeUpdate( sql );
            return result;
        } catch( Exception e ){
            System.out.println( "Code 702: 문제가 발생했습니다." );
            return -1;
        }
    }
    public void close(){
        try{
            this.stmt.close();
            this.conn.close();
        } catch( Exception e ) {System.out.println( "문제가 발생했습니다." );}
    }
}
