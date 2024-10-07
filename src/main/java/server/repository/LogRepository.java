package server.repository;

import server.entity.Log;
import server.util.DatabaseConnection;

import java.sql.ResultSet;
import java.util.Vector;

public class LogRepository {

    public void setLogging() {

    }
    public Log getLogging( int loggingID ){
        DatabaseConnection conn = new DatabaseConnection();
        String query = "SELECT * FROM log WHERE loggingID = " + loggingID;
        ResultSet rs = conn.getResult( query );
        Log log = new Log();
        // 가져온 정보로 log 인스턴스를 세팅하는 부분
        return log;
    }
    public Vector<Log> getAllLogging() {
        DatabaseConnection conn = new DatabaseConnection();
        String query = "SELECT * FROM logs";
        ResultSet rs = conn.getResult( query );

        Vector<Log> logs = new Vector<Log>();
        // 가져온 로그를 벡터에 담아서 리턴하는 값

        return logs;
    }
}
