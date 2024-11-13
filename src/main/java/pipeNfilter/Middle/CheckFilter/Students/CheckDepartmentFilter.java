package pipeNfilter.Middle.CheckFilter.Students;

import pipeNfilter.Framework.CommonFilterImpl;

import java.io.IOException;

public class CheckDepartmentFilter extends CommonFilterImpl {
    protected char[] findDepartment;
    public CheckDepartmentFilter( String department) { this.findDepartment = department.toCharArray(); }

    @Override
    public boolean specificComputationForFilter() throws IOException {
        int checkBlank = 4;
        int numOfBlank = 0;
        int idx = 0;
        byte[] buffer = new byte[64];
        boolean isDepartment = false;
        int byte_read = 0;
        while( true ){
            while( byte_read != '\n' && byte_read != -1 ){
                byte_read = in.read();
                if(byte_read == ' ') numOfBlank++;
                if(byte_read != -1) buffer[idx++] = (byte)byte_read;
                if(numOfBlank == checkBlank && buffer[idx-3] == this.findDepartment[0] && buffer[idx-2] == this.findDepartment[1])
                    isDepartment = true;
            }
            if( isDepartment ){
                for(int i = 0; i<idx; i++)
                    out.write((char)buffer[i]);
                isDepartment = false;
            }
            if (byte_read == -1) return true;
            idx = 0;
            numOfBlank = 0;
            byte_read = '\0';
        }
    }
}
