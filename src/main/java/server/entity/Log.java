package server.entity;

import java.util.Date;

public class Log {
    private int logID;
    private Date timestamp;
    private String command;
    private int userID;


    public void setLogID( int loggingID ){
        this.logID = loggingID;
    }
    public void setTimestamp( Date datetime ){
        this.timestamp = datetime;
    }
    public void setCommand( String command ){
        this.command = command;
    }
    public void setUserID( int userID ){
        this.userID = userID;
    }
    public int getLogID(){
        return this.logID;
    }
    public Date getTimestamp(){
        return this.timestamp;
    }
    public String getCommand(){
        return this.command;
    }
    public int getUserID(){
        return this.userID;
    }

}
