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
        boolean isExisted = false;
        for( Integer courseID : student.getClearCourses() ){
            if( courseID == request.getCourseID() ) isExisted = true;
        }
        StudentMessage.MakeReservationResponse.Builder response = StudentMessage.MakeReservationResponse.newBuilder();
        if( !isExisted ){
            student.getClearCourses().add( request.getCourseID() );
            int result = repository.updateStudent( student );
            if( result != -1 ) response.setStudentID( student.getStudentID() );
            else response.setStudentID( -1 );
        } else {
            response.setStudentID( -2 );
        }
        StudentMessage.MakeReservationResponse responseBuilder = response.build();
        responseObserver.onNext( responseBuilder );
        responseObserver.onCompleted();
    }
}
