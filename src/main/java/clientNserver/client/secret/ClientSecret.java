package clientNserver.client.secret;

import clientNserver.client.common.ClientConstants;
import com.example.grpc.GetPublicKeyServiceGrpc;
import com.example.grpc.KeyMessage;
import clientNserver.exception.GRPCClientException;
import io.grpc.ManagedChannel;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class ClientSecret {
    private final ManagedChannel channel;
    public ClientSecret( ManagedChannel channel ){
        this.channel = channel;
    }
    public PublicKey setPublicKey() throws GRPCClientException {
        GetPublicKeyServiceGrpc.GetPublicKeyServiceBlockingStub stub = GetPublicKeyServiceGrpc.newBlockingStub( this.channel );
        try{
            KeyMessage.GetPublicKeyRequest request = KeyMessage.GetPublicKeyRequest.newBuilder().setAccessID(1).build();
            KeyMessage.GetPublicKeyResponse response = stub.getPublicKey( request );
            byte[] keyBytes = Base64.getDecoder().decode(response.getPublicKey());
            KeyFactory keyFactory = KeyFactory.getInstance(ClientConstants.KEYGEN_ALGORITHM);
            return keyFactory.generatePublic(new X509EncodedKeySpec(keyBytes));
        } catch( Exception e ){ throw new GRPCClientException(GRPCClientException.ErrorType.RPC_ERROR, "Invalid Access", e); }
    }
}
