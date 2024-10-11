package server.service;

import com.example.grpc.LoadStudentServiceGrpc;
import com.example.grpc.StudentMessage;
import com.example.grpc.StudentMessage;
import io.grpc.stub.StreamObserver;
import server.entity.Student;
import server.repository.StudentRepository;

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
        Vector<StudentMessage.Student> students = new Vector<StudentMessage.Student>();
        for( Student student : repository.getAllStudent() ){
            StudentMessage.Student.Builder temp = StudentMessage.Student.newBuilder()
                    .setStudentID( student.getStudentID() )
                    .setLastName( student.getLastName() )
                    .setFirstName( student.getFirstName() )
                    .setDepartment( student.getDepartment() );
            for( Integer clearCourse : student.getClearCourses() ){temp.addClearCourse( clearCourse );}
            students.add( temp.build() );
        }
        return students;
    }


}
