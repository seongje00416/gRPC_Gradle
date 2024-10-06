package server.service;

import com.example.grpc.LoadCourseServiceGrpc;
import com.example.grpc.LoadCourseMessage;
import io.grpc.stub.StreamObserver;
import server.entity.Course;
import server.repository.CourseRepository;

import java.util.Vector;

public class LoadCourseServiceImpl extends LoadCourseServiceGrpc.LoadCourseServiceImplBase {

    @Override
    public void loadCourse(LoadCourseMessage.LoadCourseRequest request, StreamObserver<LoadCourseMessage.LoadCourseResponse> responseObserver) {
        Vector<LoadCourseMessage.Course> courses = fetchCourseList();
        LoadCourseMessage.LoadCourseResponse.Builder response = LoadCourseMessage.LoadCourseResponse.newBuilder();
        for( LoadCourseMessage.Course course : courses ) {response.addCourses( course );}
        LoadCourseMessage.LoadCourseResponse responseBuild = response.build();
        responseObserver.onNext( responseBuild );
        responseObserver.onCompleted();
    }

    public Vector<LoadCourseMessage.Course> fetchCourseList() {
        CourseRepository repository = new CourseRepository();
        Vector<LoadCourseMessage.Course> courses = new Vector<LoadCourseMessage.Course>();
        for(Course course : repository.getAllCourse() ){
            LoadCourseMessage.Course.Builder temp = LoadCourseMessage.Course.newBuilder()
                    .setCourseID( course.getCourseID() )
                    .setProfessor( course.getProfessor() )
                    .setCourseName( course.getCourseName() );
            for( Integer prerequisiteCourse : course.getPrerequisiteCourses() ){temp.addPrerequisiteCourse( prerequisiteCourse );}
            courses.add( temp.build() );
        }
        return courses;
    }
}
