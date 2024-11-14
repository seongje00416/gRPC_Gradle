package pipeNfilter.Middle.CheckFilter.Courses;

import pipeNfilter.Framework.CommonFilterImpl;

import java.io.IOException;

public class CheckNeedPreCoursesFilter extends CommonFilterImpl {

    @Override
    public boolean specificComputationForFilter() throws IOException {
        int numOfBlank = 0;
        int idx = 0;
        byte[] buffer = new byte[64];
        int byte_read = 0;
        boolean isExisted = false;
        while( true ){
            while( byte_read != '\n' && byte_read != -1 ){
                byte_read = in.read();
                if(byte_read == ' ') numOfBlank++;
                if(byte_read != -1 && numOfBlank != 1 && numOfBlank != 2 ) buffer[idx++] = (byte)byte_read;
                if( numOfBlank > 2 ) isExisted = true;
            }
            if( isExisted ) for(int i = 0; i<idx; i++) out.write((char)buffer[i]);
            if (byte_read == -1) return true;
            idx = 0;
            isExisted = false;
            numOfBlank = 0;
            byte_read = '\0';
        }
    }
}
