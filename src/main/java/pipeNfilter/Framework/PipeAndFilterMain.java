package pipeNfilter.Framework;

import pipeNfilter.Middle.AddCourseFilter;
import pipeNfilter.Middle.CheckDepartmentFilter;
import pipeNfilter.Middle.CheckUnRegisterCourseFilter;
import pipeNfilter.Middle.MiddleFilter;
import pipeNfilter.Sink.OverrideSinkFilter;
import pipeNfilter.Sink.SinkFilter;
import pipeNfilter.Source.SourceFilter;

public class PipeAndFilterMain {

    public static void main(String[] args) {
        try {
            String studentFile = "src/main/java/resources/Students.txt";
            String courseFile = "src/main/java/resources/Courses.txt";
            // Homework A-01
            String outputA01 = "src/main/java/resources/OutputA01.txt";
            CommonFilter sourceFilterA01 = new SourceFilter( studentFile );
            CommonFilter checkCSStudentFilter = new CheckDepartmentFilter( "CS" );
            CommonFilter checkUnRegisterFilter01 = new CheckUnRegisterCourseFilter("12345");
            CommonFilter checkUnRegisterFilter02 = new CheckUnRegisterCourseFilter("23456");
            CommonFilter addCourseFilter01 = new AddCourseFilter( "12345" );
            CommonFilter addCourseFilter02 = new AddCourseFilter( "23456" );
            CommonFilter sinkFilterA01 = new OverrideSinkFilter( outputA01 );

            sourceFilterA01.connectOutputTo( checkCSStudentFilter );
            checkCSStudentFilter.connectOutputTo( checkUnRegisterFilter01 );
            checkUnRegisterFilter01.connectOutputTo( addCourseFilter01 );
            checkCSStudentFilter.connectOutputTo( checkUnRegisterFilter02 );
            checkUnRegisterFilter02.connectOutputTo( addCourseFilter02 );
            addCourseFilter01.connectOutputTo( sinkFilterA01 );
            addCourseFilter02.connectOutputTo( sinkFilterA01 );

            Thread sourceThreadA01 = new Thread(sourceFilterA01);
            Thread checkCSStudentThread = new Thread(checkCSStudentFilter);
            Thread checkUnRegisterThread01 = new Thread(checkUnRegisterFilter01);
            Thread checkUnRegisterThread02 = new Thread(checkUnRegisterFilter02);
            Thread addCourseThread01 = new Thread(addCourseFilter01);
            Thread addCourseThread02 = new Thread(addCourseFilter02);
            Thread sinkThreadA01 = new Thread(sinkFilterA01);

            sourceThreadA01.start();
            checkCSStudentThread.start();
            checkUnRegisterThread01.start();
            checkUnRegisterThread02.start();
            addCourseThread01.start();
            addCourseThread02.start();
            sinkThreadA01.start();

            // Homework A-02
            String outputA02 = "src/main/java/resources/OutputA02.txt";

            // Homework A-03
            String outputA03 = "src/main/java/resources/OutputA03.txt";

            // Homework B-01
            String outputB01 = "src/main/java/resources/OutputB01.txt";

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
