package pipeNfilter.Middle.AddFilters;

import pipeNfilter.Framework.CommonFilterImpl;

import java.io.IOException;

public class AddCourseWhenUnRegistedFilter extends CommonFilterImpl {
    protected char[] course;
    public AddCourseWhenUnRegistedFilter( String course ) {
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
                        byte_read == ' ' &&
                        buffer[idx-6] == this.course[0] &&
                        buffer[idx-5] == this.course[1] &&
                        buffer[idx-4] == this.course[2] &&
                        buffer[idx-3] == this.course[3] &&
                        buffer[idx-2] == this.course[4]
                ) isCourse = true;
            }
            for(int i = 0; i<idx-2; i++) {
                out.write((char) buffer[i]);
            }
            if( !isCourse && idx != 0 ){
                out.write( 32 );
                for( char c : course ) out.write( c );
            }
            if( idx != 0 ){
                out.write( (char) buffer[idx-2] );
                out.write( (char) buffer[idx-1] );
            }
            isCourse = false;
            if (byte_read == -1) return true;
            idx = 0;
            numOfBlank = 0;
            byte_read = '\0';
        }
    }
}
