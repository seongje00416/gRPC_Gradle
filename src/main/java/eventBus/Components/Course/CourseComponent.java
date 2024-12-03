/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in Myungji University
 */
package eventBus.Components.Course;

import java.io.*;
import java.util.ArrayList;

public class CourseComponent {
    protected ArrayList<Course> vCourse;
    private String courseFileName;

    public CourseComponent(String sCourseFileName) throws FileNotFoundException, IOException {
        this.courseFileName = sCourseFileName;

        BufferedReader bufferedReader  = new BufferedReader(new FileReader(sCourseFileName));       
        this.vCourse  = new ArrayList<Course>();
        while (bufferedReader.ready()) {
            String courseInfo = bufferedReader.readLine();
            if(!courseInfo.equals("")) this.vCourse.add(new Course(courseInfo));
        }    
        bufferedReader.close();
    }
    public ArrayList<Course> getCourseList() {
        return this.vCourse;
    }
    public void refreshCourseFile() throws IOException {
        FileWriter fileWriter = new FileWriter( this.courseFileName );
        String line = "";
        for( Course course : vCourse ){
            line += course.getCourseId() + " " + course.instructor + " " + course.getName();
            if( course.getPrerequisiteCoursesList().size() != 0 ){
                for( String preCourse : course.getPrerequisiteCoursesList() ) {
                    line += " " + preCourse;
                }
            }
            line += '\n';
        }
        fileWriter.write( line );
        fileWriter.flush();
        fileWriter.close();
    }
    public boolean isRegisteredCourse(String courseId) {
        for (int i = 0; i < this.vCourse.size(); i++) {
            if(((Course) this.vCourse.get(i)).match(courseId)) return true;
        }
        return false;
    }
}
