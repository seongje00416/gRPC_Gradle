package clientNserver.server.service;
import com.example.grpc.GetLogByIdServiceGrpc;
import com.example.grpc.LogMessage;
import io.grpc.stub.StreamObserver;
import clientNserver.server.entity.Log;
import clientNserver.server.repository.LogRepository;

public class GetLogByIDServiceImpl extends GetLogByIdServiceGrpc.GetLogByIdServiceImplBase {
    @Override
    public void getLogById(LogMessage.GetLogByIDRequest request, StreamObserver<LogMessage.Log> responseObserver ){
        LogRepository repository = new LogRepository();
        Log log = repository.getLogByID( request.getLogID() );
        LogMessage.Log.Builder temp = LogMessage.Log.newBuilder()
                .setLogID( log.getLogID() )
                .setCommand( log.getCommand() )
                .setTimestamp( log.getTimestamp().toString() )
                .setUserID( log.getUserID() );
        LogMessage.Log responseBuild = temp.build();
        responseObserver.onNext( responseBuild );
        responseObserver.onCompleted();
    }
}
