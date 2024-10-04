package server.service;

import com.example.grpc.LoadStudentServiceGrpc;
import com.example.grpc.LoadStudentMessage;
import io.grpc.stub.StreamObserver;
import server.entity.Student;
import server.repository.StudentRepository;

import java.util.Vector;

public class LoadStudentServiceImpl extends LoadStudentServiceGrpc.LoadStudentServiceImplBase {

    @Override
    public void loadStudent( LoadStudentMessage.LoadStudentRequest request, StreamObserver<LoadStudentMessage.LoadStudentResponse> responseObserver) {
        Vector< LoadStudentMessage.Student > students = fetchStudentList();
        LoadStudentMessage.LoadStudentResponse.Builder response = LoadStudentMessage.LoadStudentResponse.newBuilder();
        for( LoadStudentMessage.Student student : students ) {
            response.addStudents(student);
        }
        LoadStudentMessage.LoadStudentResponse responseBuild = response.build();
        responseObserver.onNext(responseBuild);
        responseObserver.onCompleted();
    }

    public Vector<LoadStudentMessage.Student> fetchStudentList() {
        StudentRepository repository = new StudentRepository();
        Vector<LoadStudentMessage.Student> students = new Vector<LoadStudentMessage.Student>();
        for( Student student : repository.getAllStudent() ){
            LoadStudentMessage.Student.Builder temp = LoadStudentMessage.Student.newBuilder()
                    .setStudentID( student.getStudentID() )
                    .setLastName( student.getLastName() )
                    .setFirstName( student.getFirstName() )
                    .setDepartment( student.getDepartment() );
            for( Integer clearCourse : student.getClearCourses() ){
                temp.addClearCourse( clearCourse );
            }
            students.add( temp.build() );
        }
        return students;
    }


}
