package server.service;

import com.example.grpc.DeleteStudentServiceGrpc;
import com.example.grpc.StudentMessage;
import io.grpc.stub.StreamObserver;
import server.repository.StudentRepository;
import server.util.MessageConverter;

public class DeleteStudentServiceImpl extends DeleteStudentServiceGrpc.DeleteStudentServiceImplBase {
    @Override
    public void deleteStudent(StudentMessage.DeleteStudentRequest request, StreamObserver<StudentMessage.DeleteStudentResponse> streamObserver ){
        StudentRepository repository = new StudentRepository();
        MessageConverter converter = new MessageConverter();
        int result = repository.deleteStudent(converter.messageToEntity(request.getStudent() ) );
        StudentMessage.DeleteStudentResponse.Builder response = StudentMessage.DeleteStudentResponse.newBuilder();
        response.setStudentID( result );
        streamObserver.onNext( response.build() );
        streamObserver.onCompleted();
    }
}
