package client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import com.example.grpc.SaveServiceGrpc;
import com.example.grpc.ReadServiceGrpc;
import com.example.grpc.SaveMessage;
import com.example.grpc.ReadMessage;

import java.util.Scanner;

public class GRPCClient {

    public static void main(String[] args) {
        Scanner sc = new Scanner( System.in );

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080).usePlaintext().build();
        while( true ){
            System.out.println( "Select Operation." );
            System.out.println( "1. Save Name " );
            System.out.println( "2. Load Name " );
            System.out.println( "0. Exit " );
            int flag = sc.nextInt();

            if( flag == 1 ){
                SaveServiceGrpc.SaveServiceBlockingStub saveStub = SaveServiceGrpc.newBlockingStub(channel);
                System.out.print( "Input your Name: " );
                String name = sc.next();
                SaveMessage.SaveRequest saveRequest = SaveMessage.SaveRequest.newBuilder().setNameID(0).setName( name ).build();
                SaveMessage.SaveResponse saveResponse = saveStub.saveName( saveRequest );
                System.out.println( "Execute Result: " + Integer.toString( saveResponse.getNameID() ) + " " + saveResponse.getName() );
            }
            else if ( flag == 2 ){
                ReadServiceGrpc.ReadServiceBlockingStub readStub = ReadServiceGrpc.newBlockingStub(channel);
                ReadMessage.ReadRequest readRequest = ReadMessage.ReadRequest.newBuilder().setNameID(0).build();
                ReadMessage.ReadResponse readResponse = readStub.readName( readRequest );
                System.out.println( "Saved Name: " + readResponse.getName() );
            }
            else if ( flag == 0 ){
                break;
            }
        }
        sc.close();
        channel.shutdown();
    }
}
