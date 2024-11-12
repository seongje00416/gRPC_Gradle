/**
 * Copyright(c) 2019 All rights reserved by JU Consulting
 */
package pipeNfilter.Framework;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

// Filter는 각각의 쓰레드로 구현되므로 Runnable 클래스를 상속받아서 만들어짐
public interface CommonFilter extends Runnable{
    public void connectOutputTo(CommonFilter filter) throws IOException;
    public void connectInputTo(CommonFilter filter) throws IOException;
    public PipedInputStream getPipedInputStream();
    public PipedOutputStream getPipedOutputStream();
}
