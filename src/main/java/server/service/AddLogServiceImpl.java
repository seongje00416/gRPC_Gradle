package server.service;
import com.example.grpc.AddLogServiceGrpc;
import com.example.grpc.LogMessage;
import com.google.type.DateTime;
import io.grpc.stub.StreamObserver;
import server.entity.Log;
import server.repository.LogRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class AddLogServiceImpl extends AddLogServiceGrpc.AddLogServiceImplBase {
    @Override
    public void addLog( LogMessage.AddLogRequest request, StreamObserver<LogMessage.AddLogResponse> responseObserver ){
        LogRepository repository = new LogRepository();
        Log log = new Log();
        log.setUserID( request.getLog().getUserID() );
        log.setCommand( request.getLog().getCommand() );
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        log.setTimestamp( LocalDateTime.now().format(formatter).toString() );
        int result = repository.addLog( log );
        LogMessage.AddLogResponse.Builder response = LogMessage.AddLogResponse.newBuilder();
        if( result != -1 ){response.setLogID( log.getLogID() );}
        else {response.setLogID( -1 );}
        LogMessage.AddLogResponse responseBuild = response.build();
        responseObserver.onNext( responseBuild );
        responseObserver.onCompleted();
    }
}
