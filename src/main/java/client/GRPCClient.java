package client;

import com.example.grpc.*;
import exception.GRPCClientException;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import server.common.TUIView;

import java.util.Scanner;


public class GRPCClient {

    private final ManagedChannel channel;
    private final LoadStudentServiceGrpc.LoadStudentServiceBlockingStub loadStudentStub;
    private final LoadCourseServiceGrpc.LoadCourseServiceBlockingStub loadCourseStub;

    public GRPCClient(String host, int port) {
        try {
            this.channel = ManagedChannelBuilder.forAddress(host, port)
                    .usePlaintext()
                    .build();
            this.loadStudentStub = LoadStudentServiceGrpc.newBlockingStub(channel);
            this.loadCourseStub = LoadCourseServiceGrpc.newBlockingStub(channel);
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

    public void loadStudent() throws GRPCClientException {
        TUIView view = new TUIView();
        try{
            StudentMessage.LoadStudentRequest loadStudentRequest = StudentMessage.LoadStudentRequest.newBuilder()
                    .build();
            StudentMessage.LoadStudentResponse loadStudentResponse = loadStudentStub.loadStudent(loadStudentRequest);
            view.listViewStart();
            for(StudentMessage.Student student : loadStudentResponse.getStudentsList() ){
                System.out.println( "-----------------------------------------------------------" );
                System.out.println( "Student Number: " + student.getStudentID() );
                System.out.println( "Student Name: " + student.getLastName() + " " + student.getFirstName() );
                System.out.println( "Student Department: " + student.getDepartment() );
                System.out.print( "Success Register Course: ");
                for( Integer courseNumber : student.getClearCourseList() ){
                    System.out.print( courseNumber + " " );
                }
                System.out.print('\n');
                System.out.println( "-----------------------------------------------------------" );
            }
            view.listViewEnd();
        } catch ( StatusRuntimeException e) {
            throw new GRPCClientException(GRPCClientException.ErrorType.RPC_ERROR, "Failed to load student", e);
        }
    }

    public void loadCourse() throws GRPCClientException {
        TUIView view = new TUIView();
        try{
            CourseMessage.LoadCourseRequest loadCourseRequest = CourseMessage.LoadCourseRequest.newBuilder()
                    .build();
            CourseMessage.LoadCourseResponse loadCourseResponse = loadCourseStub.loadCourse(loadCourseRequest);
            view.listViewStart();
            for(CourseMessage.Course course : loadCourseResponse.getCoursesList() ){
                System.out.println( "-----------------------------------------------------------" );
                System.out.println( "Course Number: " + course.getCourseID() );
                System.out.println( "Course Name: " + course.getCourseName() );
                System.out.println( "Professor: " + course.getProfessor() );
                System.out.print( "Pre Requisite Courses: ");
                for( Integer courseNumber : course.getPrerequisiteCourseList() ){
                    System.out.print( courseNumber + " " );
                }
                System.out.print('\n');
                System.out.println( "-----------------------------------------------------------" );
            }
            view.listViewEnd();
        } catch( StatusRuntimeException e) {
            throw new GRPCClientException(GRPCClientException.ErrorType.RPC_ERROR, "Failed to load course", e);
        }
    }

    public static void main(String[] args) {
        GRPCClient client = null;
        Scanner sc = new Scanner(System.in);
        TUIView view = new TUIView();

        try {
            client = new GRPCClient("localhost", 8080);

            while (true) {
                view.mainView();

                int flag;
                try {
                    flag = Integer.parseInt(sc.nextLine());
                } catch (NumberFormatException e) {
                    throw new GRPCClientException(GRPCClientException.ErrorType.INPUT_ERROR, "Invalid input. Please enter a number.", e);
                }

                switch (flag) {
                    case 1:
                        client.loadStudent();
                        break;
                    case 2:
                        client.loadCourse();
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