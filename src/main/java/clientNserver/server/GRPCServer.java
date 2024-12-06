package clientNserver.server;
import clientNserver.exception.GRPCServerException;
import clientNserver.server.service.*;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import clientNserver.server.util.SecretProtector;

import java.io.IOException;
import java.net.BindException;

public class GRPCServer {
    public static void main( String[] args ) {
        Server server = null;
        SecretProtector protector = new SecretProtector();
        try {
            server = ServerBuilder.forPort( 8080 )
                    .addService( new LoadStudentServiceImpl() )
                    .addService( new LoadCourseServiceImpl() )
                    .addService( new LoginServiceImpl() )
                    .addService( new AddLogServiceImpl() )
                    .addService( new GetAllLogServiceImpl() )
                    .addService( new DeleteStudentServiceImpl() )
                    .addService( new DeleteCourseServiceImpl() )
                    .addService( new MakeReservationServiceImpl() )
                    .addService( new GetPublicKeyServiceImpl( protector.getPublicKey() ) )
                    .build()
                    .start();
            server.awaitTermination();
        } catch (IOException e) {
            GRPCServerException.ErrorType errorType = GRPCServerException.ErrorType.IO_ERROR;
            if( e instanceof BindException )errorType = GRPCServerException.ErrorType.PORT_ERROR;
            throw new GRPCServerException( errorType, e.getMessage(), e );
        }
        catch ( InterruptedException e ){throw new GRPCServerException( GRPCServerException.ErrorType.SERVICE_START_ERROR, e.getMessage(), e );}
        catch ( Exception e ){throw new GRPCServerException( GRPCServerException.ErrorType.UNKNOWN_ERROR, e.getMessage(), e );}
        finally {if( server != null ) server.shutdown();}
    }
}
