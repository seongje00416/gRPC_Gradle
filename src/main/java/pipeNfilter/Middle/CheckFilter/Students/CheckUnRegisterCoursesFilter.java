package pipeNfilter.Middle.CheckFilter.Students;

import pipeNfilter.Framework.CommonFilterImpl;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CheckUnRegisterCoursesFilter extends CommonFilterImpl {
    protected List<char[]> courses;
    public CheckUnRegisterCoursesFilter( String[] courses ) {
        this.courses = new ArrayList<char[]>();
        for( String course : courses ){
            char[] thisCourse = course.toCharArray();
            this.courses.add( thisCourse );
        }
    }

    @Override
    public boolean specificComputationForFilter() throws IOException {
        int checkBlank = 4;
        int numOfBlank = 0;
        int idx = 0;
        byte[] buffer = new byte[64];
        boolean isCourse = false;
        int byte_read = 0;
        while( true ){
            while( byte_read != '\n' && byte_read != -1 ){
                byte_read = in.read();
                if(byte_read == ' ') numOfBlank++;
                if(byte_read != -1) buffer[idx++] = (byte)byte_read;
                if(numOfBlank > checkBlank ){
                    for( char[] course : this.courses ){
                        if(
                            buffer[idx-6] == course[0] &&
                            buffer[idx-5] == course[1] &&
                            buffer[idx-4] == course[2] &&
                            buffer[idx-3] == course[3] &&
                            buffer[idx-2] == course[4]
                        ) isCourse = true;
                    }
                }
            }
            if( !isCourse ){
                for(int i = 0; i<idx; i++)
                    out.write((char)buffer[i]);
                isCourse = false;
            }
            if (byte_read == -1) return true;
            idx = 0;
            numOfBlank = 0;
            byte_read = '\0';
        }
    }
}
