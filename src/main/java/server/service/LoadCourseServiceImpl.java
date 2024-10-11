package server.service;

import com.example.grpc.LoadCourseServiceGrpc;
import com.example.grpc.CourseMessage;
import io.grpc.stub.StreamObserver;
import server.entity.Course;
import server.repository.CourseRepository;

import java.util.Vector;

public class LoadCourseServiceImpl extends LoadCourseServiceGrpc.LoadCourseServiceImplBase {

    @Override
    public void loadCourse(CourseMessage.LoadCourseRequest request, StreamObserver<CourseMessage.LoadCourseResponse> responseObserver) {
        Vector<CourseMessage.Course> courses = fetchCourseList();
        CourseMessage.LoadCourseResponse.Builder response = CourseMessage.LoadCourseResponse.newBuilder();
        for( CourseMessage.Course course : courses ) {response.addCourses( course );}
        CourseMessage.LoadCourseResponse responseBuild = response.build();
        responseObserver.onNext( responseBuild );
        responseObserver.onCompleted();
    }

    public Vector<CourseMessage.Course> fetchCourseList() {
        CourseRepository repository = new CourseRepository();
        Vector<CourseMessage.Course> courses = new Vector<CourseMessage.Course>();
        for(Course course : repository.getAllCourse() ){
            CourseMessage.Course.Builder temp = CourseMessage.Course.newBuilder()
                    .setCourseID( course.getCourseID() )
                    .setProfessor( course.getProfessor() )
                    .setCourseName( course.getCourseName() );
            for( Integer prerequisiteCourse : course.getPrerequisiteCourses() ){temp.addPrerequisiteCourse( prerequisiteCourse );}
            courses.add( temp.build() );
        }
        return courses;
    }
}
