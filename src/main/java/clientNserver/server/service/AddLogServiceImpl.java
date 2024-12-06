package clientNserver.server.service;
import com.example.grpc.AddLogServiceGrpc;
import com.example.grpc.LogMessage;
import io.grpc.stub.StreamObserver;
import clientNserver.server.entity.Log;
import clientNserver.server.repository.LogRepository;
import clientNserver.server.util.MessageConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddLogServiceImpl extends AddLogServiceGrpc.AddLogServiceImplBase {
    @Override
    public void addLog( LogMessage.AddLogRequest request, StreamObserver<LogMessage.AddLogResponse> responseObserver ){
        LogRepository repository = new LogRepository();
        MessageConverter converter = new MessageConverter();
        Log log = converter.messageToEntity( request.getLog() );
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        log.setTimestamp( LocalDateTime.now().format(formatter).toString() );
        int result = repository.addLog( log );
        LogMessage.AddLogResponse.Builder response = LogMessage.AddLogResponse.newBuilder();
        if( result != -1 )response.setLogID( log.getLogID() );
        else response.setLogID( -1 );
        LogMessage.AddLogResponse responseBuild = response.build();
        responseObserver.onNext( responseBuild );
        responseObserver.onCompleted();
    }
}
