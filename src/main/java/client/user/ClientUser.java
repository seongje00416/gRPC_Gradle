package client.user;

import client.common.TUIView;
import com.example.grpc.*;
import exception.GRPCClientException;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ClientUser {
    private final ManagedChannel channel;
    private final TUIView view;
    private int studentToken;
    public ClientUser( ManagedChannel channel, int studentToken ){
        this.channel = channel;
        this.view = new TUIView();
        this.studentToken = studentToken;
    }
    public void refreshToken( int token ) { this.studentToken = token; };
    public void loadStudent() throws GRPCClientException {
        LoadStudentServiceGrpc.LoadStudentServiceBlockingStub loadStudentStub = LoadStudentServiceGrpc.newBlockingStub(this.channel);
        try{
            StudentMessage.LoadStudentRequest loadStudentRequest = StudentMessage.LoadStudentRequest.newBuilder().build();
            StudentMessage.LoadStudentResponse loadStudentResponse = loadStudentStub.loadStudent(loadStudentRequest);
            this.view.listViewStart();
            for(StudentMessage.Student student : loadStudentResponse.getStudentsList() ){
                this.view.lineView();
                System.out.println( "Student Number: " + student.getStudentID() );
                System.out.println( "Student Name: " + student.getLastName() + " " + student.getFirstName() );
                System.out.println( "Student Department: " + student.getDepartment() );
                System.out.print( "Success Register Course: ");
                for( Integer courseNumber : student.getClearCourseList() ) System.out.print( courseNumber + " " );
                System.out.print('\n');
                this.view.lineView();
            }
            this.view.listViewEnd();
        } catch ( StatusRuntimeException e) {throw new GRPCClientException(GRPCClientException.ErrorType.RPC_ERROR, "Failed to load student", e);}
    }
    public void deleteStudent() {
        LoadStudentServiceGrpc.LoadStudentServiceBlockingStub loadStudentStub = LoadStudentServiceGrpc.newBlockingStub(this.channel);
        try {
            StudentMessage.LoadStudentRequest loadStudentRequest = StudentMessage.LoadStudentRequest.newBuilder().build();
            StudentMessage.LoadStudentResponse loadStudentResponse = loadStudentStub.loadStudent(loadStudentRequest);
            this.view.listViewStart();
            for (int index = 0; index < loadStudentResponse.getStudentsCount(); index++) {
                System.out.println(index + ": " + loadStudentResponse.getStudents(index).getStudentID() + " " + loadStudentResponse.getStudents(index).getFirstName());
            }
            this.view.listViewEnd();
            System.out.print("삭제할 학생의 번호를 입력하세요: ");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            int index = Integer.parseInt(br.readLine());
            if ( index >= loadStudentResponse.getStudentsCount() ) System.out.println( "범위 내 숫자를 입력해주세요" );
            else {
                StudentMessage.Student selectedStudent = loadStudentResponse.getStudents(index);
                DeleteStudentServiceGrpc.DeleteStudentServiceBlockingStub deleteStudentStub = DeleteStudentServiceGrpc.newBlockingStub(this.channel);
                StudentMessage.DeleteStudentRequest deleteStudentRequest = StudentMessage.DeleteStudentRequest.newBuilder().setStudent(selectedStudent).setUserID(this.studentToken).build();
                StudentMessage.DeleteStudentResponse deleteStudentResponse = deleteStudentStub.deleteStudent(deleteStudentRequest);
                if( deleteStudentResponse.getStudentID() == -1 ) System.out.println( "학생 삭제에 실패했습니다" );
                else System.out.println( "학생 삭제에 성공했습니다" );
            }
        } catch (StatusRuntimeException e) {
            //throw new GRPCClientException(GRPCClientException.ErrorType.RPC_ERROR, "Failed to load student", e);
            e.printStackTrace();
        } catch( Exception e ){
            System.out.println( "삭제 오류가 발생했습니다." );
        }
    }
    public int login( String id, String pw ){
        LogInServiceGrpc.LogInServiceBlockingStub loginStub = LogInServiceGrpc.newBlockingStub( this.channel );
        try{
            LogInMessage.LogInRequest loginRequest = LogInMessage.LogInRequest.newBuilder().setStudentID( id ).setPassword( pw ).build();
            LogInMessage.LogInResponse loginResponse = loginStub.logIn( loginRequest );
            return loginResponse.getStudentID();
        } catch ( Exception e ) {System.out.println( "Fail to Login" );}
        return -1;
    }
}
