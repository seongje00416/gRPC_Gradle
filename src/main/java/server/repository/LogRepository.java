package server.repository;

import server.entity.Log;

import java.io.*;
import java.util.StringTokenizer;
import java.util.Vector;

import static server.common.DatabaseConstants.LOG_PATH;
import static server.common.MessageConstants.FILE_NOT_FOUND_MESSAGE;
import static server.common.MessageConstants.FILE_NOT_READ_MESSAGE;

public class LogRepository {

    public Log getLogByID( int logID ) {
        Log log = new Log();
        return log;
    }

    public Vector<Log> getAllLog() {
        try {
            BufferedReader br = new BufferedReader( new FileReader( LOG_PATH ) );
            br.readLine();
            String line;
            int lineIndex = 1;
            Vector<Log> logs = new Vector<Log>();
            while( ( line = br.readLine() ) != null ) {
                StringTokenizer tokenizer = new StringTokenizer( line, "|" );
                Log log = new Log();
                log.setLogID( lineIndex );
                lineIndex++;
                log.setTimestamp( tokenizer.nextToken().trim() );
                log.setUserID( Integer.parseInt( tokenizer.nextToken().trim() ) );
                log.setCommand( tokenizer.nextToken().trim() );
                logs.add( log );
            }
            return logs;
        } catch (FileNotFoundException e) {
            System.out.println( FILE_NOT_FOUND_MESSAGE );
            return null;
        } catch ( IOException e ){
            System.out.println( FILE_NOT_READ_MESSAGE );
            return null;
        }
    }

    public int addLog( Log log ){
        try {
            FileWriter fw = new FileWriter( LOG_PATH, true );
            fw.write( log.getTimestamp() + "  |   " + log.getUserID() + "    |    " + log.getCommand() + '\n' );
            fw.flush();
            return log.getLogID();
        } catch (IOException e) {
            return -1;
        }
    }
}
