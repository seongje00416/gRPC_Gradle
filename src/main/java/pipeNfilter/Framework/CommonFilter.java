/**
 * Copyright(c) 2019 All rights reserved by JU Consulting
 */
package Framework;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public interface CommonFilter extends Runnable{
    public void connectOutputTo(CommonFilter filter) throws IOException;
    public void connectInputTo(CommonFilter filter) throws IOException;
    public PipedInputStream getPipedInputStream();
    public PipedOutputStream getPipedOutputStream();
}
