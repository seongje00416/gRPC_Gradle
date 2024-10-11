package server.service;

import com.example.grpc.SignInMessage;
import com.example.grpc.SignInServiceGrpc;
import io.grpc.stub.StreamObserver;

public class SigninServiceImpl extends SignInServiceGrpc.SignInServiceImplBase {

    @Override
    public void signIn(SignInMessage.SignInRequest request, StreamObserver<SignInMessage.SignInResponse> responseObserver ){
        SignInMessage.SignInResponse.Builder response = SignInMessage.SignInResponse.newBuilder();

    }
}
