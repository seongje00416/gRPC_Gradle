package server;

import exception.GRPCServerException;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import com.example.grpc.SaveServiceGrpc;
import com.example.grpc.ReadServiceGrpc;
import com.example.grpc.SaveMessage;
import com.example.grpc.ReadMessage;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.BindException;
import java.util.Vector;

public class GRPCServer {

    static String savedName;

    public static void main( String[] args ) {
        // forPort() : 해당 서버에 접속할 때 사용할 포트 지정
        // addService() : 만드는 서버에 적용할 서비스 등록
        Server server = null;
            // 서버에 gRPC 서비스를 세팅하고 작동시키는 코드
        try {
            server = ServerBuilder.forPort( 8080 )
                    .addService( new SaveServiceImpl() )
                    .addService( new ReadServiceImpl() )
                    .build()
                    .start();
            server.awaitTermination();
        } catch (IOException e) {
            GRPCServerException.ErrorType errorType = GRPCServerException.ErrorType.IO_ERROR;
            if( e instanceof BindException ){
                errorType = GRPCServerException.ErrorType.PORT_ERROR;
            }
            throw new GRPCServerException( errorType, e.getMessage(), e );
        } catch ( InterruptedException e ){
            throw new GRPCServerException( GRPCServerException.ErrorType.SERVICE_START_ERROR, e.getMessage(), e );
        } catch ( Exception e ){
            throw new GRPCServerException( GRPCServerException.ErrorType.UNKNOWN_ERROR, e.getMessage(), e );
        } finally {
            if( server != null ){
                server.shutdown();
            }
        }
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
