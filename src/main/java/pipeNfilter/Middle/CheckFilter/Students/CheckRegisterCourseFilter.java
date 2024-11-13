package pipeNfilter.Middle.CheckFilter.Students;

import pipeNfilter.Framework.CommonFilterImpl;
import java.io.IOException;

public class CheckRegisterCourseFilter extends CommonFilterImpl {
    protected char[] course;
    public CheckRegisterCourseFilter( String course ) {
        this.course = course.toCharArray();
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
                if(numOfBlank > checkBlank &&
                        buffer[idx-6] == this.course[0] &&
                        buffer[idx-5] == this.course[1] &&
                        buffer[idx-4] == this.course[2] &&
                        buffer[idx-3] == this.course[3] &&
                        buffer[idx-2] == this.course[4]
                )
                    isCourse = true;
            }
            if( isCourse ){
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
