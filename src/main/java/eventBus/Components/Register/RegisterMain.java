/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in MyungJi University 
 */
package eventBus.Components.Register;

import eventBus.Components.Course.Course;
import eventBus.Components.Course.CourseComponent;
import eventBus.Components.Student.Student;
import eventBus.Components.Student.StudentComponent;
import eventBus.Framework.Event;
import eventBus.Framework.EventId;
import eventBus.Framework.EventQueue;
import eventBus.Framework.RMIEventBus;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class RegisterMain {
	public static void main(String[] args) throws FileNotFoundException, IOException, NotBoundException {
		RMIEventBus eventBus = (RMIEventBus) Naming.lookup("EventBus");
		long componentId = eventBus.register();
		System.out.println("RegisterMain (ID:" + componentId + ") is successfully registered...");

		CourseComponent coursesList = new CourseComponent("src/main/java/eventBus/Courses.txt");
		StudentComponent studentList = new StudentComponent("src/main/java/eventBus/Students.txt");
		Event event = null;
		boolean done = false;
		while (!done) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			EventQueue eventQueue = eventBus.getEventQueue(componentId);
			for (int i = 0; i < eventQueue.getSize(); i++) {
				event = eventQueue.getEvent();
				switch (event.getEventId()) {
				case SignUpCourse:
					printLogEvent("Get", event);
					eventBus.sendEvent(new Event(EventId.ClientOutput, signUpCourse( studentList, coursesList, event.getMessage()) ));
					break;
				case QuitTheSystem:
					eventBus.unRegister(componentId);
					done = true;
					break;
				default:
					break;
				}
			}
		}
	}
	private static String signUpCourse( StudentComponent studentList, CourseComponent courseList, String message ) throws IOException {
		StringTokenizer tokenizer = new StringTokenizer( message );
		String studentID = tokenizer.nextToken();
		String courseID = tokenizer.nextToken();
		// Check Student Existed
		Student currentStudent = null;
		for( Student student : studentList.getStudentList() ){
			if( student.getStudentID().equals( studentID ) ) currentStudent = student;
		}
		if( currentStudent == null ) return "Student is not Existed";
		// Check Course Existed
		Course currentCourse = null;
		for( Course course : courseList.getCourseList() ){
			if( course.getCourseId().equals( courseID ) ) currentCourse = course;
		}
		if( currentCourse == null ) return "Course is not Existed";
		// Check if Student didn't complete Pre Courses
		ArrayList<String> preRequiredCourseList = currentCourse.getPrerequisiteCoursesList();
		ArrayList<String> completeCourse = currentStudent.getCompletedCourses();
		boolean isCompletePreCourses = true;
		for( String preCourse : preRequiredCourseList ){
			if( completeCourse.indexOf( preCourse ) < 0 ) isCompletePreCourses = false;
		}
		if( !isCompletePreCourses ) return "Student didn't complete Pre Courses";

		// Sign Up New Course
		studentList.signUpCourse( studentID, courseID );
		return "Sign Up Success!";
	}
	private static void printLogEvent(String comment, Event event) {
		System.out.println(
				"\n** " + comment + " the event(ID:" + event.getEventId() + ") message: " + event.getMessage());
	}
}
