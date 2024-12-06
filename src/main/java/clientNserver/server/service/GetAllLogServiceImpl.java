package clientNserver.server.service;
import com.example.grpc.GetAllLogServiceGrpc;
import com.example.grpc.LogMessage;
import io.grpc.stub.StreamObserver;
import clientNserver.server.entity.Log;
import clientNserver.server.repository.LogRepository;
import clientNserver.server.util.MessageConverter;

import java.util.Vector;

public class GetAllLogServiceImpl extends GetAllLogServiceGrpc.GetAllLogServiceImplBase {
    @Override
    public void getAllLog(LogMessage.GetAllLogRequest request, StreamObserver<LogMessage.GetAllLogResponse> responseObserver ){
        Vector<LogMessage.Log> logs = fetchGetAllLog();
        LogMessage.GetAllLogResponse.Builder response = LogMessage.GetAllLogResponse.newBuilder();
        for( LogMessage.Log log : logs ) response.addLogs( log );
        LogMessage.GetAllLogResponse responseBuild = response.build();
        responseObserver.onNext( responseBuild );
        responseObserver.onCompleted();
    }
    public Vector<LogMessage.Log> fetchGetAllLog(){
        LogRepository repository = new LogRepository();
        MessageConverter converter = new MessageConverter();
        Vector<LogMessage.Log> logs = new Vector<LogMessage.Log>();
        for( Log log : repository.getAllLog() ) logs.add( converter.entityToMessage( log ) );
        return logs;
    }
}
