/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in Myungji University.
 */
package Components.Sink;

import java.io.FileWriter;
import java.io.IOException;

import Framework.CommonFilterImpl;

public class SinkFilter extends CommonFilterImpl{
    private String sinkFile;
    
    public SinkFilter(String outputFile) {
        this.sinkFile = outputFile;
    }
    @Override
    public boolean specificComputationForFilter() throws IOException {
        int byte_read;
        FileWriter fw = new FileWriter(this.sinkFile);
        while(true) {
            byte_read = in.read(); 
            if (byte_read == -1) {
            	 fw.close();
                 System.out.print( "::Filtering is finished; Output file is created." );  
                 return true;
            }
            fw.write((char)byte_read);
        }   
    }
}
