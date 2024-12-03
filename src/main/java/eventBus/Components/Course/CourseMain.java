/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in MyungJi University 
 */
package eventBus.Components.Course;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;

import eventBus.Framework.Event;
import eventBus.Framework.EventId;
import eventBus.Framework.EventQueue;
import eventBus.Framework.RMIEventBus;

public class CourseMain {
	public static void main(String[] args) throws FileNotFoundException, IOException, NotBoundException {
		RMIEventBus eventBus = (RMIEventBus) Naming.lookup("EventBus");
		long componentId = eventBus.register();
		System.out.println("CourseMain (ID:" + componentId + ") is successfully registered...");

		CourseComponent coursesList = new CourseComponent("src/main/java/eventBus/Courses.txt");
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
				case ListCourses:
					printLogEvent("Get", event);
					eventBus.sendEvent(new Event(EventId.ClientOutput, makeCourseList(coursesList)));
					break;
				case RegisterCourses:
					printLogEvent("Get", event);
					eventBus.sendEvent(new Event(EventId.ClientOutput, registerCourse(coursesList, event.getMessage())));
					break;
				case DeleteCourses:
					printLogEvent( "Get", event);
					eventBus.sendEvent( new Event( EventId.ClientOutput, deleteCourses( coursesList, event.getMessage())));
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
	private static String registerCourse(CourseComponent coursesList, String message) {
		Course course = new Course(message);
		if (!coursesList.isRegisteredCourse(course.courseId)) {
			coursesList.vCourse.add(course);
			return "This course is successfully added.";
		} else
			return "This course is already registered.";
	}
	private static String makeCourseList(CourseComponent coursesList) {
		String returnString = "";
		for (int j = 0; j < coursesList.vCourse.size(); j++) {
			returnString += coursesList.getCourseList().get(j).getString() + "\n";
		}
		return returnString;
	}
	private static String deleteCourses( CourseComponent courseList, String courseID ) throws IOException {
		int index = -1;
		for( int i = 0; i < courseList.vCourse.size(); i++ ){
			if( courseList.vCourse.get(i).getCourseId().equals( courseID ) ) {
				index = i;
				break;
			}
		}
		if( index == -1 ) return "Course is not Existed";

		courseList.vCourse.remove( index );
		courseList.refreshCourseFile();
		return "Course Deleted!";
	}
	private static void printLogEvent(String comment, Event event) {
		System.out.println(
				"\n** " + comment + " the event(ID:" + event.getEventId() + ") message: " + event.getMessage());
	}
}
