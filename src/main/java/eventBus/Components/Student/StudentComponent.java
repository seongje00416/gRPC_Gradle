/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in MyungJi University 
 */

package eventBus.Components.Student;

import java.io.*;
import java.util.ArrayList;

public class StudentComponent {
	protected ArrayList<Student> vStudent;
	private String studentFileName;
	
	public StudentComponent(String sStudentFileName) throws FileNotFoundException, IOException {
		this.studentFileName = sStudentFileName;

		BufferedReader bufferedReader = new BufferedReader(new FileReader(sStudentFileName));
		this.vStudent = new ArrayList<Student>();
		while (bufferedReader.ready()) {
			String stuInfo = bufferedReader.readLine();
			if (!stuInfo.equals("")) this.vStudent.add(new Student(stuInfo));
		}
		bufferedReader.close();
	}
	public void refreshStudentFile() throws IOException {
		FileWriter fileWriter = new FileWriter( this.studentFileName );
		String line = "";
		for( Student student : vStudent ){
			line = line + student.getStudentID() + " " + student.getName();
			if( student.getCompletedCourses().size() != 0 ){
				for( String course : student.getCompletedCourses() ){
					line = line + " " + course;
				}
			}
			line = line + '\n';
		}
		fileWriter.write( line );
		fileWriter.flush();
		fileWriter.close();
	}
	public ArrayList<Student> getStudentList() {
		return vStudent;
	}
	public void setvStudent(ArrayList<Student> vStudent) {
		this.vStudent = vStudent;
	}
	public boolean isRegisteredStudent(String sSID) {
		for (int i = 0; i < this.vStudent.size(); i++) {
			if (((Student) this.vStudent.get(i)).match(sSID)) return true;
		}
		return false;
	}
}
