package server;

import exception.GRPCServerException;
import server.service.LoadStudentServiceImpl;
import server.service.LoadCourseServiceImpl;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.net.BindException;

public class GRPCServer {

    static String savedName;

    public static void main( String[] args ) {
        // forPort() : 해당 서버에 접속할 때 사용할 포트 지정
        // addService() : 만드는 서버에 적용할 서비스 등록
        Server server = null;
            // 서버에 gRPC 서비스를 세팅하고 작동시키는 코드
        try {
            server = ServerBuilder.forPort( 8080 )
                    .addService( new LoadStudentServiceImpl() )
                    .addService( new LoadCourseServiceImpl() )
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
}
