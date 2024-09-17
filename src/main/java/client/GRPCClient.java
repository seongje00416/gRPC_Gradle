package client;

import exception.GRPCClientException;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import com.example.grpc.SaveServiceGrpc;
import com.example.grpc.ReadServiceGrpc;
import com.example.grpc.SaveMessage;
import com.example.grpc.ReadMessage;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class GRPCClient {

    private final ManagedChannel channel;
    private final SaveServiceGrpc.SaveServiceBlockingStub saveStub;
    private final ReadServiceGrpc.ReadServiceBlockingStub readStub;

    public GRPCClient(String host, int port) {
        try {
            this.channel = ManagedChannelBuilder.forAddress(host, port)
                    .usePlaintext()
                    .build();
            this.saveStub = SaveServiceGrpc.newBlockingStub(channel);
            this.readStub = ReadServiceGrpc.newBlockingStub(channel);
        } catch (Exception e) {
            throw new GRPCClientException(GRPCClientException.ErrorType.CONNECTION_ERROR, "Failed to initialize gRPC client", e);
        }
    }

    public void shutdown() throws GRPCClientException {
        try {
            channel.shutdownNow();
        } catch (Exception e) {
            throw new GRPCClientException(GRPCClientException.ErrorType.SHUTDOWN_ERROR, "Error shutting down client", e);
        }
    }

    public void saveName(String name) throws GRPCClientException {
        try {
            SaveMessage.SaveRequest saveRequest = SaveMessage.SaveRequest.newBuilder()
                    .setNameID(0)
                    .setName(name)
                    .build();
            SaveMessage.SaveResponse saveResponse = saveStub.saveName(saveRequest);
            System.out.println("Execute Result: " + saveResponse.getNameID() + " " + saveResponse.getName());
        } catch (StatusRuntimeException e) {
            throw new GRPCClientException(GRPCClientException.ErrorType.RPC_ERROR, "Failed to save name: " + e.getStatus(), e);
        }
    }

    public void readName() throws GRPCClientException {
        try {
            ReadMessage.ReadRequest readRequest = ReadMessage.ReadRequest.newBuilder()
                    .setNameID(0)
                    .build();
            ReadMessage.ReadResponse readResponse = readStub.readName(readRequest);
            System.out.println("Saved Name: " + readResponse.getName());
        } catch (StatusRuntimeException e) {
            throw new GRPCClientException(GRPCClientException.ErrorType.RPC_ERROR, "Failed to read name: " + e.getStatus(), e);
        }
    }

    public static void main(String[] args) {
        GRPCClient client = null;
        Scanner sc = new Scanner(System.in);

        try {
            client = new GRPCClient("localhost", 8080);

            while (true) {
                System.out.println("Select Operation.");
                System.out.println("1. Save Name");
                System.out.println("2. Load Name");
                System.out.println("0. Exit");

                int flag;
                try {
                    flag = Integer.parseInt(sc.nextLine());
                } catch (NumberFormatException e) {
                    throw new GRPCClientException(GRPCClientException.ErrorType.INPUT_ERROR, "Invalid input. Please enter a number.", e);
                }

                switch (flag) {
                    case 1:
                        System.out.print("Input your Name: ");
                        String name = sc.nextLine();
                        client.saveName(name);
                        break;
                    case 2:
                        client.readName();
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        } catch (GRPCClientException e) {
            System.err.println("Error: " + e.getMessage());
            System.err.println("Error Type: " + e.getErrorType());
        } finally {
            if (client != null) {
                try {
                    client.shutdown();
                } catch (GRPCClientException e) {
                    System.err.println("Error shutting down client: " + e.getMessage());
                }
            }
            sc.close();
        }
    }
}