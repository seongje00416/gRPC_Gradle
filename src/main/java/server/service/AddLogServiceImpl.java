package server.service;

import com.example.grpc.AddLogServiceGrpc;
import com.example.grpc.LogMessage;
import io.grpc.stub.StreamObserver;
import server.entity.Log;
import server.repository.LogRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddLogServiceImpl extends AddLogServiceGrpc.AddLogServiceImplBase {
    @Override
    public void addLog( LogMessage.AddLogRequest request, StreamObserver<LogMessage.AddLogResponse> responseObserver ){
        LogRepository repository = new LogRepository();
        Log log = new Log();
        log.setLogID( request.getLog().getLogID() );
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss" );
        try{
            Date timestamp = formatter.parse( request.getLog().getTimestamp() );
            log.setTimestamp( timestamp );
        } catch( ParseException e ){
            System.out.println( "시간 형식이 잘못되었습니다" );
        }
        log.setUserID( request.getLog().getUserID() );
        log.setCommand( request.getLog().getCommand() );

        int result = repository.addLog( log );
        LogMessage.AddLogResponse.Builder response = LogMessage.AddLogResponse.newBuilder();
        if( result != -1 ){
            response.setLogID( log.getLogID() );
        } else {
            response.setLogID( -1 );
        }
        LogMessage.AddLogResponse responseBuild = response.build();
        responseObserver.onNext( responseBuild );
        responseObserver.onCompleted();
    }
}
