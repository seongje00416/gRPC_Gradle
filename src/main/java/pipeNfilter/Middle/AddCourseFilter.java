package pipeNfilter.Middle;

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
                if(byte_read != -1) buffer[idx++] = (byte)byte_read;
            }
            for(int i = 0; i<idx; i++)
                out.write((char)buffer[i]);
            out.write( ' ' );
            for( char c : this.course ){
                out.write(c);
            }
            if (byte_read == -1) return true;
            idx = 0;
            byte_read = '\0';
        }
    }
}
