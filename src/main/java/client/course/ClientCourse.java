package client.course;

import client.common.TUIView;
import com.example.grpc.CourseMessage;
import com.example.grpc.LoadCourseServiceGrpc;
import exception.GRPCClientException;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;

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
}
