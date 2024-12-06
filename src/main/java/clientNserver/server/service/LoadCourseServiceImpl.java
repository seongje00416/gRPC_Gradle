package clientNserver.server.service;
import com.example.grpc.LoadCourseServiceGrpc;
import com.example.grpc.CourseMessage;
import io.grpc.stub.StreamObserver;
import clientNserver.server.entity.Course;
import clientNserver.server.repository.CourseRepository;
import clientNserver.server.util.MessageConverter;

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
        MessageConverter converter = new MessageConverter();
        Vector<CourseMessage.Course> courses = new Vector<CourseMessage.Course>();
        for(Course course : repository.getAllCourse() ) courses.add( converter.entityToMessage( course ) );
        return courses;
    }
}
