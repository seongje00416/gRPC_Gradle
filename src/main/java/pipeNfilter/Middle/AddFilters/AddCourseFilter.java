package pipeNfilter.Middle.AddFilters;

import pipeNfilter.Framework.CommonFilterImpl;

import java.io.IOException;

public class AddCourseFilter extends CommonFilterImpl {
    protected char[] course;

    public AddCourseFilter(String course) {
        this.course = course.toCharArray();
    }

    @Override
    public boolean specificComputationForFilter() throws IOException {
        int idx = 0;
        byte[] buffer = new byte[64];
        int byte_read = 0;
        while(true) {
            while(byte_read != '\n' && byte_read != -1) {
                byte_read = in.read();
                if(byte_read != -1 && byte_read != 13 ) buffer[idx++] = (byte)byte_read;
                if(byte_read != -1 && byte_read == 13 ){
                    buffer[idx++] = 32;
                    for( char c : course ){
                        buffer[idx++] = (byte)c;
                    }
                    buffer[idx++] = 13;
                }
            }
            for( byte b : buffer ){
                System.out.print( b + " | " + (char)b + "\n" );
            }
            for(int i = 0; i<idx; i++){
                out.write((char)buffer[i]);
            }
            if (byte_read == -1) return true;
            idx = 0;
            byte_read = '\0';
        }
    }
}
