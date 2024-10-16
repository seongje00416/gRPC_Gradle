package server.service;
import com.example.grpc.GetAllLogServiceGrpc;
import com.example.grpc.LogMessage;
import io.grpc.stub.StreamObserver;
import server.entity.Log;
import server.repository.LogRepository;

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
        Vector<LogMessage.Log> logs = new Vector<LogMessage.Log>();
        for( Log log : repository.getAllLog() ){
            LogMessage.Log.Builder temp = LogMessage.Log.newBuilder()
                    .setLogID( log.getLogID() )
                    .setCommand( log.getCommand() )
                    .setTimestamp( log.getTimestamp().toString() )
                    .setUserID( log.getUserID() );
            logs.add( temp.build() );
        }
        return logs;
    }
}
