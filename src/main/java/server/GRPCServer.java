package server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import com.example.grpc.SaveServiceGrpc;
import com.example.grpc.ReadServiceGrpc;
import com.example.grpc.SaveMessage;
import com.example.grpc.ReadMessage;

import java.util.Vector;

public class GRPCServer {

    static String savedName;

    public static void main( String[] args ) throws Exception {

        Server server = ServerBuilder.forPort( 8080 )
                .addService( new SaveServiceImpl() )
                .addService( new ReadServiceImpl() )
                .build()
                .start();

        server.awaitTermination();
    }

    static class SaveServiceImpl extends SaveServiceGrpc.SaveServiceImplBase {
        @Override
        public void saveName(SaveMessage.SaveRequest request, StreamObserver<SaveMessage.SaveResponse> responseObserver) {
            SaveMessage.SaveResponse response = SaveMessage.SaveResponse.newBuilder()
                    .setName( request.getName() )
                    .setNameID( 0 )
                    .build();
            savedName = request.getName();
            responseObserver.onNext( response );
            responseObserver.onCompleted();
        }
    }

    static class ReadServiceImpl extends ReadServiceGrpc.ReadServiceImplBase {
        @Override
        public void readName( ReadMessage.ReadRequest request, StreamObserver<ReadMessage.ReadResponse> responseObserver) {
            ReadMessage.ReadResponse response = ReadMessage.ReadResponse.newBuilder()
                    .setNameID( 0 )
                    .setName( savedName )
                    .build();
            responseObserver.onNext( response );
            responseObserver.onCompleted();
        }
    }
}
