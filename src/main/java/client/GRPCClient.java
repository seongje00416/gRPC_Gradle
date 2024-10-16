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
    private int studentIDToken;
    private final TUIView view;

    public GRPCClient(String host, int port) {
        this.view = new TUIView();
        try {
            this.channel = ManagedChannelBuilder.forAddress(host, port)
                    .usePlaintext()
                    .build();
        } catch (Exception e) {
            throw new GRPCClientException(GRPCClientException.ErrorType.CONNECTION_ERROR, "Failed to initialize gRPC client", e);
        }
    }
    public void setToken( int id ) {this.studentIDToken = id;}
    public int getToken() { return this.studentIDToken; }
    public void shutdown() throws GRPCClientException {
        try {
            channel.shutdownNow();
        } catch (Exception e) {
            throw new GRPCClientException(GRPCClientException.ErrorType.SHUTDOWN_ERROR, "Error shutting down client", e);
        }
    }
    public void loadStudent() throws GRPCClientException {
        addLog( "load Students" );
        LoadStudentServiceGrpc.LoadStudentServiceBlockingStub loadStudentStub = LoadStudentServiceGrpc.newBlockingStub(this.channel);
        try{
            StudentMessage.LoadStudentRequest loadStudentRequest = StudentMessage.LoadStudentRequest.newBuilder()
                    .build();
            StudentMessage.LoadStudentResponse loadStudentResponse = loadStudentStub.loadStudent(loadStudentRequest);
            this.view.listViewStart();
            for(StudentMessage.Student student : loadStudentResponse.getStudentsList() ){
                this.view.lineView();
                System.out.println( "Student Number: " + student.getStudentID() );
                System.out.println( "Student Name: " + student.getLastName() + " " + student.getFirstName() );
                System.out.println( "Student Department: " + student.getDepartment() );
                System.out.print( "Success Register Course: ");
                for( Integer courseNumber : student.getClearCourseList() ){
                    System.out.print( courseNumber + " " );
                }
                System.out.print('\n');
                this.view.lineView();
            }
            this.view.listViewEnd();
        } catch ( StatusRuntimeException e) {
            throw new GRPCClientException(GRPCClientException.ErrorType.RPC_ERROR, "Failed to load student", e);
        }
    }
    public void loadCourse() throws GRPCClientException {
        addLog( "load Courses" );
        LoadCourseServiceGrpc.LoadCourseServiceBlockingStub loadCourseStub = LoadCourseServiceGrpc.newBlockingStub(this.channel);
        try{
            CourseMessage.LoadCourseRequest loadCourseRequest = CourseMessage.LoadCourseRequest.newBuilder()
                    .build();
            CourseMessage.LoadCourseResponse loadCourseResponse = loadCourseStub.loadCourse(loadCourseRequest);
            this.view.listViewStart();
            for(CourseMessage.Course course : loadCourseResponse.getCoursesList() ){
                this.view.lineView();
                System.out.println( "Course Number: " + course.getCourseID() );
                System.out.println( "Course Name: " + course.getCourseName() );
                System.out.println( "Professor: " + course.getProfessor() );
                System.out.print( "Pre Requisite Courses: ");
                for( Integer courseNumber : course.getPrerequisiteCourseList() ){
                    System.out.print( courseNumber + " " );
                }
                System.out.print('\n');
                this.view.lineView();
            }
            this.view.listViewEnd();
        } catch( StatusRuntimeException e) {
            throw new GRPCClientException(GRPCClientException.ErrorType.RPC_ERROR, "Failed to load course", e);
        }
    }
    public int login( String id, String pw ){
        LogInServiceGrpc.LogInServiceBlockingStub loginStub = LogInServiceGrpc.newBlockingStub( this.channel );
        try{
            LogInMessage.LogInRequest loginRequest = LogInMessage.LogInRequest.newBuilder().setStudentID( id ).setPassword( pw ).build();
            LogInMessage.LogInResponse loginResponse = loginStub.logIn( loginRequest );
            return loginResponse.getStudentID();
        } catch ( Exception e ) {
            System.out.println( "Fail to Login" );
        }
        return -1;
    }
    public void addLog( String command ) {
        AddLogServiceGrpc.AddLogServiceBlockingStub addLogStub = AddLogServiceGrpc.newBlockingStub( this.channel );
        LogMessage.Log newLog = LogMessage.Log.newBuilder()
                .setCommand( command )
                .setUserID( studentIDToken )
                .build();
        try{
            LogMessage.AddLogRequest addLogRequest = LogMessage.AddLogRequest.newBuilder()
                    .setUserID(studentIDToken)
                    .setLog( newLog )
                    .build();
            LogMessage.AddLogResponse addLogResponse = addLogStub.addLog( addLogRequest );
            if( addLogResponse.getLogID() == 0 && !command.equals("login") ) System.out.println( "Add Log Failed" );
        } catch( Exception e ){
            System.out.println( "Add Log Failed: Error" );
        }
    }
    public void getAllLog(){
        addLog( "load Logs" );
        GetAllLogServiceGrpc.GetAllLogServiceBlockingStub getAllLogStub = GetAllLogServiceGrpc.newBlockingStub( this.channel );
        try{
            LogMessage.GetAllLogRequest getAllLogRequest = LogMessage.GetAllLogRequest.newBuilder().setUserID( studentIDToken ).build();
            LogMessage.GetAllLogResponse getAllLogResponse = getAllLogStub.getAllLog( getAllLogRequest );
            this.view.listViewStart();
            System.out.println( "|          Time        |    User    |    Command    |" );
            for( LogMessage.Log log : getAllLogResponse.getLogsList() ){
                System.out.println( " " + log.getTimestamp() + "    " + log.getUserID() + "    " + log.getCommand() );
            }
            this.view.listViewEnd();
        } catch( Exception e ){
            throw new GRPCClientException(GRPCClientException.ErrorType.RPC_ERROR, "Failed to load Logs", e);
        }
    }

    public static void main(String[] args) {
        GRPCClient client = null;
        Scanner sc = new Scanner(System.in);
        TUIView view = new TUIView();

        try {
            client = new GRPCClient("localhost", 8080);
            while( true ){
                String[] userInfo = view.loginView();
                int studentID = client.login( userInfo[0], userInfo[1] );
                if( studentID == 0 ){
                    System.out.println( "User is not Existed" );
                }
                else if ( studentID == -1 ){
                    System.out.println( "Error!!" );
                }
                else {
                    client.setToken( studentID );
                    System.out.println( "Set Token: " + client.getToken() );
                    client.addLog( "login" );
                    break;
                }
            }
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
                    case 3:
                        client.getAllLog();
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