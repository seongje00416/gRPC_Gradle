package server.service;

import com.example.grpc.CourseMessage;
import com.example.grpc.DeleteCourseServiceGrpc;
import io.grpc.stub.StreamObserver;
import server.repository.CourseRepository;
import server.util.MessageConverter;

public class DeleteCourseServiceImpl extends DeleteCourseServiceGrpc.DeleteCourseServiceImplBase {
    @Override
    public void deleteCourse(CourseMessage.DeleteCourseRequest request, StreamObserver<CourseMessage.DeleteCourseResponse> responseStreamObserver ){
        CourseRepository repository = new CourseRepository();
        MessageConverter converter = new MessageConverter();
        int result = repository.deleteCourse(converter.messageToEntity(request.getCourse() ) );
        CourseMessage.DeleteCourseResponse.Builder response = CourseMessage.DeleteCourseResponse.newBuilder();
        response.setCourseID( result );
        responseStreamObserver.onNext( response.build() );
        responseStreamObserver.onCompleted();
    }
}
