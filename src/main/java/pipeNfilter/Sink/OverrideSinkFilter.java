package pipeNfilter.Sink;

import pipeNfilter.Framework.CommonFilterImpl;

import java.io.FileWriter;
import java.io.IOException;

public class OverrideSinkFilter extends CommonFilterImpl {
    private String sinkFile;

    public OverrideSinkFilter(String outputFile) { this.sinkFile = outputFile; }
    @Override
    public boolean specificComputationForFilter() throws IOException {
        int byte_read;
        FileWriter fw = new FileWriter(this.sinkFile, true);
        while (true) {
            byte_read = in.read();
            if (byte_read == -1) {
                fw.close();
                System.out.print("::Filtering is finished; Output file is created.");
                return true;
            }
            fw.write((char) byte_read);
        }
    }
}
