package server.service;

import com.example.grpc.MakeReservationServiceGrpc;
import com.example.grpc.StudentMessage;
import io.grpc.stub.StreamObserver;
import server.entity.Student;
import server.repository.StudentRepository;

public class MakeReservationServiceImpl extends MakeReservationServiceGrpc.MakeReservationServiceImplBase {
    @Override
    public void makeReservation(StudentMessage.MakeReservationRequest request, StreamObserver<StudentMessage.MakeReservationResponse> responseObserver) {
        StudentRepository repository = new StudentRepository();
        Student student = repository.getStudentByID( request.getStudentID() );
        student.getClearCourses().add( request.getCourseID() );
        int result = repository.updateStudent( student );
        StudentMessage.MakeReservationResponse.Builder response = StudentMessage.MakeReservationResponse.newBuilder();
        if( result != -1 ) response.setStudentID( student.getStudentID() );
        else response.setStudentID( -1 );
        StudentMessage.MakeReservationResponse responseBuilder = response.build();
        responseObserver.onNext( responseBuilder );
        responseObserver.onCompleted();
    }
}
