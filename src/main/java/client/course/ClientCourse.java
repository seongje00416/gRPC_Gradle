package client.course;

import client.common.TUIView;
import com.example.grpc.CourseMessage;
import com.example.grpc.DeleteCourseServiceGrpc;
import com.example.grpc.LoadCourseServiceGrpc;
import exception.GRPCClientException;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ClientCourse {
    private final ManagedChannel channel;
    private final TUIView view;
    private int studentToken;
    public ClientCourse( ManagedChannel channel, int studentToken ){
        this.channel = channel;
        this.view = new TUIView();
        this.studentToken = studentToken;
    }
    public void refreshToken( int token ) { this.studentToken = token; };
    public void loadCourse() throws GRPCClientException {
        LoadCourseServiceGrpc.LoadCourseServiceBlockingStub loadCourseStub = LoadCourseServiceGrpc.newBlockingStub(this.channel);
        try{
            CourseMessage.LoadCourseRequest loadCourseRequest = CourseMessage.LoadCourseRequest.newBuilder().build();
            CourseMessage.LoadCourseResponse loadCourseResponse = loadCourseStub.loadCourse(loadCourseRequest);
            this.view.listViewStart();
            for(CourseMessage.Course course : loadCourseResponse.getCoursesList() ){
                this.view.lineView();
                System.out.println( "Course Number: " + course.getCourseID() );
                System.out.println( "Course Name: " + course.getCourseName() );
                System.out.println( "Professor: " + course.getProfessor() );
                System.out.print( "Pre Requisite Courses: ");
                for( Integer courseNumber : course.getPrerequisiteCourseList() ) System.out.print( courseNumber + " " );
                System.out.print('\n');
                this.view.lineView();
            }
            this.view.listViewEnd();
        } catch( StatusRuntimeException e) {throw new GRPCClientException(GRPCClientException.ErrorType.RPC_ERROR, "Failed to load course", e);}
    }
    public void deleteCourse(){
        LoadCourseServiceGrpc.LoadCourseServiceBlockingStub loadCourseStub = LoadCourseServiceGrpc.newBlockingStub( this.channel );
        try{
            CourseMessage.LoadCourseRequest loadCourseRequest = CourseMessage.LoadCourseRequest.newBuilder().build();
            CourseMessage.LoadCourseResponse loadCourseResponse = loadCourseStub.loadCourse( loadCourseRequest );
            this.view.listViewStart();
            for( int index = 0; index < loadCourseResponse.getCoursesCount(); index++ ){
                System.out.println(index + ": " + loadCourseResponse.getCourses(index).getCourseName() + " " + loadCourseResponse.getCourses(index).getCourseID());
            }
            this.view.listViewEnd();
            System.out.println( "Input Delete Number: " );
            BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );
            int index = Integer.parseInt( br.readLine() );
            if( index >= loadCourseResponse.getCoursesCount() ) System.out.println( "Input Number must in Range" );
            else {
                CourseMessage.Course selectedCourse = loadCourseResponse.getCourses( index );
                DeleteCourseServiceGrpc.DeleteCourseServiceBlockingStub deleteCourseStub = DeleteCourseServiceGrpc.newBlockingStub( this.channel );
                CourseMessage.DeleteCourseRequest deleteCourseRequest = CourseMessage.DeleteCourseRequest.newBuilder().setUserID( this.studentToken ).setCourse( selectedCourse ).build();
                CourseMessage.DeleteCourseResponse deleteCourseResponse = deleteCourseStub.deleteCourse( deleteCourseRequest );
                if( deleteCourseResponse.getCourseID() == -1 ) System.out.println( "Course Delete Failed" );
                else System.out.println( "Course Delete Success.");
            }
        } catch( Exception e ){
            System.out.println( "ERROR" );
        }
    }
}
