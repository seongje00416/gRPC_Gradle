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
        // Set Log Instance
        return log;
    }
    public Vector<Log> getAllLogging() {
        DatabaseConnection conn = new DatabaseConnection();
        String query = "SELECT * FROM logs";
        ResultSet rs = conn.getResult( query );

        Vector<Log> logs = new Vector<Log>();
        // Return the logs in Vector

        return logs;
    }
}
