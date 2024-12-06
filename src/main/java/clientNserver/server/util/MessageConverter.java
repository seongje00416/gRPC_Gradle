package clientNserver.server.util;

import com.example.grpc.CourseMessage;
import com.example.grpc.LogMessage;
import com.example.grpc.StudentMessage;
import clientNserver.server.entity.Course;
import clientNserver.server.entity.Log;
import clientNserver.server.entity.Student;

import java.util.Vector;

public class MessageConverter {
    public Student messageToEntity(StudentMessage.Student message ){
        Student student = new Student();
        student.setStudentID( message.getStudentID() );
        student.setFirstName( message.getFirstName() );
        student.setLastName( message.getLastName() );
        student.setDepartment( message.getDepartment());
        Vector<Integer> clearCourses = new Vector<Integer>();
        for( Integer courseID : message.getClearCourseList() ){
            clearCourses.add( courseID );
        }
        student.setClearCourses( clearCourses );
        return student;
    }
    public StudentMessage.Student entityToMessage( Student entity ){
        StudentMessage.Student.Builder student = StudentMessage.Student.newBuilder();
        student.setStudentID( entity.getStudentID() );
        student.setFirstName(entity.getFirstName() );
        student.setLastName(entity.getLastName() );
        student.setDepartment(entity.getDepartment() );
        for( Integer courseID : entity.getClearCourses() ){
            student.addClearCourse( courseID );
        }
        return student.build();
    }
    public Course messageToEntity(CourseMessage.Course message ){
        Course course = new Course();
        course.setCourseID(message.getCourseID() );
        course.setCourseName(message.getCourseName() );
        course.setProfessor(message.getProfessor());
        Vector<Integer> preCourses = new Vector<Integer>();
        for( Integer courseID : message.getPrerequisiteCourseList() ){
            preCourses.add( courseID );
        }
        course.setPrerequisiteCourses( preCourses );
        return course;
    }
    public CourseMessage.Course entityToMessage( Course entity ){
        CourseMessage.Course.Builder course = CourseMessage.Course.newBuilder();
        course.setCourseID( entity.getCourseID() );
        course.setCourseName(entity.getCourseName() );
        course.setProfessor(entity.getProfessor() );
        for( Integer courseID : entity.getPrerequisiteCourses() ){
            course.addPrerequisiteCourse( courseID );
        }
        return course.build();
    }
    public Log messageToEntity(LogMessage.Log message ){
        Log log = new Log();
        log.setLogID(message.getLogID());
        log.setUserID(message.getUserID());
        log.setCommand(message.getCommand());
        log.setTimestamp(message.getTimestamp());
        return log;
    }
    public LogMessage.Log entityToMessage( Log entity ){
        LogMessage.Log.Builder log = LogMessage.Log.newBuilder();
        log.setLogID( entity.getLogID() );
        log.setUserID( entity.getUserID());
        log.setCommand( entity.getCommand());
        log.setTimestamp(entity.getTimestamp());
        return log.build();
    }
}
