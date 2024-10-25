package client.log;

import client.common.TUIView;
import com.example.grpc.AddLogServiceGrpc;
import com.example.grpc.GetAllLogServiceGrpc;
import com.example.grpc.LogMessage;
import exception.GRPCClientException;
import io.grpc.ManagedChannel;

public class ClientLog {
    private final ManagedChannel channel;
    private final TUIView view;
    private int studentToken;
    public ClientLog( ManagedChannel channel, int studentToken ){
        this.channel = channel;
        this.view = new TUIView();
        this.studentToken = studentToken;
    }
    public void refreshToken( int token ) { this.studentToken = token; };
    public void getAllLog(){
        GetAllLogServiceGrpc.GetAllLogServiceBlockingStub getAllLogStub = GetAllLogServiceGrpc.newBlockingStub( this.channel );
        try{
            LogMessage.GetAllLogRequest getAllLogRequest = LogMessage.GetAllLogRequest.newBuilder().setUserID( this.studentToken ).build();
            LogMessage.GetAllLogResponse getAllLogResponse = getAllLogStub.getAllLog( getAllLogRequest );
            this.view.listViewStart();
            System.out.println( "       Time          |     User      |       Command         " );
            for( LogMessage.Log log : getAllLogResponse.getLogsList() ) System.out.println( log.getTimestamp() + "  |   " + log.getUserID() + "    |    " + log.getCommand() );
            this.view.listViewEnd();
        } catch( Exception e ){throw new GRPCClientException(GRPCClientException.ErrorType.RPC_ERROR, "Failed to load Logs", e);}
    }
    public void addLog( String command ) {
        AddLogServiceGrpc.AddLogServiceBlockingStub addLogStub = AddLogServiceGrpc.newBlockingStub(this.channel);
        LogMessage.Log newLog = LogMessage.Log.newBuilder().setCommand(command).setUserID( this.studentToken ).build();
        try {
            LogMessage.AddLogRequest addLogRequest = LogMessage.AddLogRequest.newBuilder().setUserID(this.studentToken).setLog(newLog).build();
            LogMessage.AddLogResponse addLogResponse = addLogStub.addLog(addLogRequest);
            if( addLogResponse.getLogID() == -1 ) System.out.println( "Add Log Failed Error" );
        } catch (Exception e) {
            System.out.println("Add Log Failed: Error");
        }
    }
}
