package pipeNfilter.Middle;

import pipeNfilter.Framework.CommonFilterImpl;

import java.io.IOException;

public class CheckStudentNumberFilter extends CommonFilterImpl {

    public CheckStudentNumberFilter( String studentNumber){

    }
    @Override
    public boolean specificComputationForFilter() throws IOException {
        return false;
    }
}
