package pipeNfilter.Middle.CheckFilter.Courses;

import pipeNfilter.Framework.CommonFilterImpl;

import java.io.IOException;

public class CheckNeedPreCoursesFilter extends CommonFilterImpl {

    @Override
    public boolean specificComputationForFilter() throws IOException {
        int checkBlank = 4;
        int numOfBlank = 0;
        int idx = 0;
        byte[] buffer = new byte[64];
        int byte_read = 0;
        while( true ){
            while( byte_read != '\n' && byte_read != -1 ){
                byte_read = in.read();
                if(byte_read == ' ') numOfBlank++;
                System.out.println( numOfBlank );
                if(byte_read != -1) buffer[idx++] = (byte)byte_read;
            }
            for(int i = 0; i<idx; i++){
                System.out.print( (char)buffer[i] );
                out.write((char)buffer[i]);
            }
            if (byte_read == -1) return true;
            idx = 0;
            numOfBlank = 0;
            byte_read = '\0';
        }
    }
}
