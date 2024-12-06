package clientNserver.server.service;
import com.example.grpc.LoadStudentServiceGrpc;
import com.example.grpc.StudentMessage;
import io.grpc.stub.StreamObserver;
import clientNserver.server.entity.Student;
import clientNserver.server.repository.StudentRepository;
import clientNserver.server.util.MessageConverter;

import java.util.Vector;

public class LoadStudentServiceImpl extends LoadStudentServiceGrpc.LoadStudentServiceImplBase {
    @Override
    public void loadStudent(StudentMessage.LoadStudentRequest request, StreamObserver<StudentMessage.LoadStudentResponse> responseObserver) {
        Vector< StudentMessage.Student > students = fetchStudentList();
        StudentMessage.LoadStudentResponse.Builder response = StudentMessage.LoadStudentResponse.newBuilder();
        for( StudentMessage.Student student : students ) {response.addStudents(student);}
        StudentMessage.LoadStudentResponse responseBuild = response.build();
        responseObserver.onNext(responseBuild);
        responseObserver.onCompleted();
    }
    public Vector<StudentMessage.Student> fetchStudentList() {
        StudentRepository repository = new StudentRepository();
        MessageConverter converter = new MessageConverter();
        Vector<StudentMessage.Student> students = new Vector<StudentMessage.Student>();
        for( Student student : repository.getAllStudent() ) students.add( converter.entityToMessage( student ) );
        return students;
    }
}
