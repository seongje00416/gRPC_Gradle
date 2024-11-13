package pipeNfilter.Framework;

import pipeNfilter.Middle.AddFilters.AddCourseWhenUnRegistedFilter;
import pipeNfilter.Middle.CheckFilter.Courses.CheckNeedPreCoursesFilter;
import pipeNfilter.Middle.CheckFilter.Courses.CheckRegisterPreCoursesFilter;
import pipeNfilter.Middle.CheckFilter.Students.CheckAdmissionYearFilter;
import pipeNfilter.Middle.CheckFilter.Students.CheckDepartmentFilter;
import pipeNfilter.Middle.CheckFilter.Students.CheckNoneDepartmentFilter;
import pipeNfilter.Middle.DeleteFilter.DeleteCourseWhenWrongRegisteredFilter;
import pipeNfilter.Sink.SinkFilter;
import pipeNfilter.Source.SourceFilter;

import java.io.IOException;

public class PipeAndFilterMain {

    static void homeworkA01() {
        try{
            String studentFile = "src/main/java/resources/Students.txt";
            String outputA01 = "src/main/java/resources/OutputA01.txt";

            CommonFilter sourceFilterA01 = new SourceFilter( studentFile );
            CommonFilter checkCSStudentFilter = new CheckDepartmentFilter( "CS" );
            CommonFilter add12345CourseFilter = new AddCourseWhenUnRegistedFilter( "12345" );
            CommonFilter add23456CourseFilter = new AddCourseWhenUnRegistedFilter( "23456" );
            CommonFilter sinkFilterA01 = new SinkFilter( outputA01 );

            sourceFilterA01.connectOutputTo( checkCSStudentFilter );
            checkCSStudentFilter.connectOutputTo( add12345CourseFilter );
            add12345CourseFilter.connectOutputTo( add23456CourseFilter );
            add23456CourseFilter.connectOutputTo( sinkFilterA01 );

            Thread sourceThreadA01 = new Thread( sourceFilterA01 );
            Thread checkCSThreadA01 = new Thread( checkCSStudentFilter );
            Thread add12345ThreadA01 = new Thread( add12345CourseFilter );
            Thread add23456ThreadA01 = new Thread( add23456CourseFilter );
            Thread sinkThreadA01 = new Thread( sinkFilterA01 );

            sourceThreadA01.start();
            checkCSThreadA01.start();
            add12345ThreadA01.start();
            add23456ThreadA01.start();
            sinkThreadA01.start();
        } catch( IOException e ){
            //System.out.println( "ERROR" );
            e.printStackTrace();
        }


    }
    static void homeworkA02(){
        try {
            String studentFile = "src/main/java/resources/Students.txt";
            String outputA02 = "src/main/java/resources/OutputA02.txt";

            CommonFilter sourceFilterA02 = new SourceFilter( studentFile );
            CommonFilter checkEEStudentFilter = new CheckDepartmentFilter( "EE" );
            CommonFilter checkAndAdd23456CourseFilter = new AddCourseWhenUnRegistedFilter( "23456" );
            CommonFilter sinkFilterA02 = new SinkFilter( outputA02 );

            sourceFilterA02.connectOutputTo( checkEEStudentFilter );
            checkEEStudentFilter.connectOutputTo( checkAndAdd23456CourseFilter );
            checkAndAdd23456CourseFilter.connectOutputTo( sinkFilterA02 );

            Thread sourceThreadA02 = new Thread( sourceFilterA02 );
            Thread checkEEStudentThread = new Thread( checkEEStudentFilter );
            Thread checkAndAdd23456Thread = new Thread( checkAndAdd23456CourseFilter );
            Thread sinkThreadA02 = new Thread( sinkFilterA02 );

            sourceThreadA02.start();
            checkEEStudentThread.start();
            checkAndAdd23456Thread.start();
            sinkThreadA02.start();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    static void homeworkA03(){
        try{
            String studentFile = "src/main/java/resources/Students.txt";
            String outputA03 = "src/main/java/resources/OutputA03.txt";

            CommonFilter sourceFilterA03 = new SourceFilter( studentFile );
            CommonFilter check2013YearStudentFilter = new CheckAdmissionYearFilter( "2013" );
            CommonFilter checkNoCSStudentFilter = new CheckNoneDepartmentFilter( "CS" );
            CommonFilter checkAndDelete17651Filter = new DeleteCourseWhenWrongRegisteredFilter( "17651" );
            CommonFilter checkAndDelete17652Filter = new DeleteCourseWhenWrongRegisteredFilter( "17652" );
            CommonFilter sinkFilterA03 = new SinkFilter( outputA03 );

            sourceFilterA03.connectOutputTo( check2013YearStudentFilter );
            check2013YearStudentFilter.connectOutputTo( checkNoCSStudentFilter );
            checkNoCSStudentFilter.connectOutputTo( checkAndDelete17651Filter );
            checkAndDelete17651Filter.connectOutputTo( checkAndDelete17652Filter );
            checkAndDelete17652Filter.connectOutputTo( sinkFilterA03 );

            Thread sourceThreadA03 = new Thread( sourceFilterA03 );
            Thread check2013yearThread = new Thread( check2013YearStudentFilter );
            Thread checkNoCSStudentThread = new Thread( checkNoCSStudentFilter );
            Thread checkAndDelete17651Thread = new Thread( checkAndDelete17651Filter );
            Thread checkAndDelete17652Thread = new Thread( checkAndDelete17652Filter );
            Thread sinkThreadA03 = new Thread( sinkFilterA03 );

            sourceThreadA03.start();
            check2013yearThread.start();
            checkNoCSStudentThread.start();
            checkAndDelete17651Thread.start();
            checkAndDelete17652Thread.start();
            sinkThreadA03.start();
        } catch( IOException e ){
            System.out.println( "ERROR" );
        }
    }
    static void homeworkB01(){
        try{
            String courseFile = "src/main/java/resources/Courses.txt";
            String studentFile = "src/main/java/resources/Students.txt";
            String outputB01 = "src/main/java/resources/Output-1.txt";
            String outputB02 = "src/main/java/resources/Output-2.txt";

            CommonFilter courseSourceFilter = new SourceFilter( courseFile );
            CommonFilter checkNeedPreCourseFilter = new CheckNeedPreCoursesFilter();

            CommonFilter studentSourceFilter = new SourceFilter( studentFile );
            CommonFilter checkRegisterPreCoursesFilter = new CheckRegisterPreCoursesFilter();

            CommonFilter fittedSinkFilter = new SinkFilter( outputB01 );
            CommonFilter noneFittedSinkFilter = new SinkFilter( outputB02 );

            // --------------------Filter 사이 Pipe들을 연결하는 부분----------------------------------------
            courseSourceFilter.connectOutputTo( checkNeedPreCourseFilter );
            checkNeedPreCourseFilter.connectOutputTo( checkRegisterPreCoursesFilter );
            checkRegisterPreCoursesFilter.connectOutputTo( fittedSinkFilter );
            // ------------------------------------------------------------------------------------------

            Thread courseSourceThread = new Thread( courseSourceFilter );
            Thread checkNeedPreCourseThread = new Thread( checkNeedPreCourseFilter );
            Thread checkRegisterPreCoursesThread = new Thread( checkRegisterPreCoursesFilter );
            Thread fittedSinkThread = new Thread( fittedSinkFilter );

            courseSourceThread.start();
            checkNeedPreCourseThread.start();
            checkRegisterPreCoursesThread.start();
            fittedSinkThread.start();
        } catch( IOException e ){
            //System.out.println( "ERROR" );
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //homeworkA01();
        //homeworkA02();
        //homeworkA03();
        homeworkB01();
    }
}
