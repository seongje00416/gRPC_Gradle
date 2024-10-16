package server.service;
import com.example.grpc.LogInMessage;
import com.example.grpc.LogInServiceGrpc;
import io.grpc.stub.StreamObserver;
import server.repository.UserRepository;

public class LoginServiceImpl extends LogInServiceGrpc.LogInServiceImplBase {
    @Override
    public void logIn(LogInMessage.LogInRequest request, StreamObserver<LogInMessage.LogInResponse> responseObserver ){
        UserRepository repository = new UserRepository();
        LogInMessage.LogInResponse.Builder response = LogInMessage.LogInResponse.newBuilder();
        response.setStudentID( repository.logInStudent(request.getStudentID(), request.getPassword() ) );
        LogInMessage.LogInResponse responseBuild = response.build();
        responseObserver.onNext( responseBuild );
        responseObserver.onCompleted();
    }
}
