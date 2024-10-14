package server.repository;

import server.entity.Log;
import server.util.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class LogRepository {

    public Log getLogByID( int logID ) {
        DatabaseConnection conn = new DatabaseConnection();
        String query = "SELECT * FROM logs WHERE log_id=" + logID + ";";
        ResultSet rs = conn.getResult(query);

        Log log = new Log();
        try {
            log.setLogID(rs.getInt("log_id"));
            log.setUserID(rs.getInt("user_id"));
            log.setCommand(rs.getString("command"));
            log.setTimestamp(rs.getTimestamp("timestamp"));
        } catch (SQLException e) {
            log.setLogID(-1);
        }
        return log;
    }

    public Vector<Log> getAllLog() {
        DatabaseConnection conn = new DatabaseConnection();
        String query = "SELECT * FROM logs";
        ResultSet rs = conn.getResult( query );
        Vector<Log> logs = new Vector<Log>();
        try{
            while( true ){
                Log log = new Log();
                log.setLogID( rs.getInt( "log_id" ) );
                log.setCommand( rs.getString( "command" ) );
                log.setTimestamp( rs.getTimestamp( "timestamp" ) );
                log.setUserID( rs.getInt( "user_id" ) );
                logs.add( log );
                if( !rs.next() ) break;
            }
        } catch( SQLException e ){
            System.out.println( "로그 조회에 실패했습니다" );
        }
        return logs;
    }

    public int addLog( Log log ){
        DatabaseConnection conn = new DatabaseConnection();
        String query = "INSERT INTO logs ( command, user_id ) VALUES ("
                + "'" + log.getCommand() + "', " + log.getUserID() +
                ");";
        int result = conn.isSuccess( query );
        if( result != -1 ) return log.getLogID();
        else return -1;
    }
}
