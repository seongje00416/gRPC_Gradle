package clientNserver.server.service;

import com.example.grpc.GetPublicKeyServiceGrpc;
import com.example.grpc.KeyMessage;
import io.grpc.stub.StreamObserver;

import java.security.PublicKey;
import java.util.Base64;

public class GetPublicKeyServiceImpl extends GetPublicKeyServiceGrpc.GetPublicKeyServiceImplBase {
    private final PublicKey publicKey;
    public GetPublicKeyServiceImpl(PublicKey publicKey) {
        this.publicKey = publicKey;
    }
    @Override
    public void getPublicKey(KeyMessage.GetPublicKeyRequest request, StreamObserver<KeyMessage.GetPublicKeyResponse> responseObserver) {
        if( request.getAccessID() == 1 ){
            String encodedKey = Base64.getEncoder().encodeToString( this.publicKey.getEncoded() );
            KeyMessage.GetPublicKeyResponse response = KeyMessage.GetPublicKeyResponse.newBuilder().setPublicKey(encodedKey).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }
}
