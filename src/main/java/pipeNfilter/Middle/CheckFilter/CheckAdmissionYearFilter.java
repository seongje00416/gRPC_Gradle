package pipeNfilter.Middle.CheckFilter;

import pipeNfilter.Framework.CommonFilterImpl;

import java.io.IOException;

public class CheckAdmissionYearFilter extends CommonFilterImpl {
    protected char[] year;

    public CheckAdmissionYearFilter( String year ) {this.year = year.toCharArray();}
    @Override
    public boolean specificComputationForFilter() throws IOException {
        int checkBlank = 1;
        int numOfBlank = 0;
        int idx = 0;
        byte[] buffer = new byte[64];
        boolean isCorrect = false;
        int byte_read = 0;

        // 1사이클 = 1문장
        while(true) {
            while(byte_read != '\n' && byte_read != -1) {
                byte_read = in.read();
                if(byte_read == ' ') numOfBlank++;
                if(byte_read != -1) buffer[idx++] = (byte)byte_read;
                if(numOfBlank == checkBlank &&
                        buffer[idx-9] == this.year[0] &&
                        buffer[idx-8] == this.year[1] &&
                        buffer[idx-7] == this.year[2] &&
                        buffer[idx-6] == this.year[3])
                    isCorrect = true;
            }
            if(isCorrect == true) {
                for(int i = 0; i<idx; i++)
                    out.write((char)buffer[i]);
                isCorrect = false;
            }
            if (byte_read == -1) return true;
            idx = 0;
            numOfBlank = 0;
            byte_read = '\0';
        }
    }
}
