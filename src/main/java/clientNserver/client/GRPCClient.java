package clientNserver.client;

import clientNserver.client.common.ClientConstants;
import clientNserver.client.common.SecretProtector;
import clientNserver.client.common.TUIView;
import clientNserver.client.course.ClientCourse;
import clientNserver.client.log.ClientLog;
import clientNserver.client.secret.ClientSecret;
import clientNserver.client.user.ClientUser;
import clientNserver.exception.GRPCClientException;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GRPCClient {
    private final ManagedChannel channel;
    private String token;
    public GRPCClient(String host, int port) {
        try {
            this.channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
        }
        catch (Exception e) {throw new GRPCClientException(GRPCClientException.ErrorType.CONNECTION_ERROR, "Failed to initialize gRPC client", e);}
    }
    public ManagedChannel getChannel() { return this.channel; };
    public void setToken( String encryptedID ) {this.token = encryptedID;}
    public String getToken() { return this.token; }
    public void shutdown() throws GRPCClientException {
        try { channel.shutdownNow(); }
        catch (Exception e) {throw new GRPCClientException(GRPCClientException.ErrorType.SHUTDOWN_ERROR, "Error shutting down client", e);}
    }
    public static void main(String[] args) {
        GRPCClient client = null;
        BufferedReader sc = new BufferedReader( new InputStreamReader( System.in ) );
        TUIView view = new TUIView();
        try {
            client = new GRPCClient(ClientConstants.SEVER_URL, ClientConstants.SERVER_PORT);
            ClientSecret secretService = new ClientSecret( client.getChannel() );
            SecretProtector protector = new SecretProtector( secretService.setPublicKey() );
            ClientUser userService = new ClientUser(client.getChannel(), client.getToken(), protector );
            ClientCourse courseService = new ClientCourse(client.getChannel(), client.getToken(), protector );
            ClientLog logService = new ClientLog(client.getChannel(), client.getToken(), protector );
            while( true ){
                String[] userInfo = view.loginView();
                int studentID = userService.login( userInfo[0], userInfo[1] );
                if( studentID == 0 ) System.out.println( ClientConstants.USER_NOT_FOUND_MESSAGE );
                else if ( studentID == -1 ){System.out.println( "Error!!" );}
                else {
                    client.setToken( Integer.toString( studentID ) );
                    userService.refreshToken(client.getToken());
                    courseService.refreshToken(client.getToken());
                    logService.refreshToken(client.getToken());
                    logService.addLog( ClientConstants.LOG_COMMAND_LOGIN );
                    break;
                }
            }
            while (true) {
                view.mainView();
                int flag;
                try {flag = Integer.parseInt(sc.readLine());}
                catch (NumberFormatException e) {throw new GRPCClientException(GRPCClientException.ErrorType.INPUT_ERROR, "Invalid input. Please enter a number.", e);}
                switch (flag) {
                    case 1:
                        userService.loadStudent();
                        logService.addLog( ClientConstants.LOG_COMMAND_RETRIEVE_STUDENTS );
                        break;
                    case 2:
                        courseService.loadCourse();
                        logService.addLog( ClientConstants.LOG_COMMAND_RETRIEVE_COURSES );
                        break;
                    case 3:
                        logService.getAllLog();
                        logService.addLog( ClientConstants.LOG_COMMAND_RETRIEVE_LOGS );
                        break;
                    case 4:
                        courseService.deleteCourse();
                        logService.addLog( ClientConstants.LOG_COMMAND_DELETE_COURSE );
                        break;
                    case 5:
                        userService.deleteStudent();
                        logService.addLog( ClientConstants.LOG_COMMAND_DELETE_STUDENT );
                        break;
                    case 6:
                        userService.makeReservation();
                        logService.addLog( ClientConstants.LOG_COMMAND_MAKE_RESERVATION );
                        break;
                    case 0:
                        sc.close();
                        System.exit(0);
                        return;
                    case 77:
                        System.out.println( secretService.setPublicKey() );
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        } catch (GRPCClientException e) {
            System.err.println("Error: " + e.getMessage());
            System.err.println("Error Type: " + e.getErrorType());
        } catch ( IOException e ){
            System.out.println( "I/O Error" );
        } catch ( Exception e ){
            System.out.println( "Critical Error" );
        }
        finally {
            if (client != null) {
                try {client.shutdown();}
                catch (GRPCClientException e) {System.err.println("Error shutting down client: " + e.getMessage());}
            }
        }
    }
}