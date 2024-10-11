package server;

import exception.GRPCServerException;
import server.service.LoadStudentServiceImpl;
import server.service.LoadCourseServiceImpl;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import server.service.LoginServiceImpl;

import java.io.IOException;
import java.net.BindException;

public class GRPCServer {
    public static void main( String[] args ) {
        Server server = null;
        try {
            server = ServerBuilder.forPort( 8080 )
                    .addService( new LoadStudentServiceImpl() )
                    .addService( new LoadCourseServiceImpl() )
                    .addService( new LoginServiceImpl() )
                    .build()
                    .start();
            server.awaitTermination();
        } catch (IOException e) {
            GRPCServerException.ErrorType errorType = GRPCServerException.ErrorType.IO_ERROR;
            if( e instanceof BindException )errorType = GRPCServerException.ErrorType.PORT_ERROR;
            throw new GRPCServerException( errorType, e.getMessage(), e );
        } catch ( InterruptedException e ){
            throw new GRPCServerException( GRPCServerException.ErrorType.SERVICE_START_ERROR, e.getMessage(), e );
        } catch ( Exception e ){
            throw new GRPCServerException( GRPCServerException.ErrorType.UNKNOWN_ERROR, e.getMessage(), e );
        } finally {
            if( server != null ) server.shutdown();
        }
    }
}
