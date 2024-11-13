package pipeNfilter.Framework;

import java.io.EOFException;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public abstract class MultiFilterImpl implements CommonFilter {
    protected PipedInputStream[] ins = new PipedInputStream[2];
    protected PipedOutputStream[] outs = new PipedOutputStream[2];
    protected PipedInputStream currentIn;
    protected PipedOutputStream currentOut;
    protected int indexIn = 0;
    protected int indexOut = 0;
    public void connectOutputTo(CommonFilter nextFilter) throws IOException {
        if( indexOut < 3 ) {
            PipedOutputStream outputStream = new PipedOutputStream();
            outputStream.connect( nextFilter.getPipedInputStream() );
            outs[indexOut] = outputStream;
            indexOut++;
        } else throw new IndexOutOfBoundsException();
    }

    public void connectInputTo(CommonFilter previousFilter) throws IOException {
        if( indexIn < 3 ){
            PipedInputStream inputStream = new PipedInputStream();
            inputStream.connect( previousFilter.getPipedOutputStream() );
            ins[indexIn] = inputStream;
            indexIn++;
        } else throw new IndexOutOfBoundsException();
    }

    public PipedInputStream getPipedInputStream() {
        if( indexIn > 2 ) return null;
        currentIn = new PipedInputStream();
        ins[indexIn] = currentIn;
        indexIn++;
        return ins[indexIn-1];
    }
    public PipedOutputStream getPipedOutputStream() {
        return currentOut;
    }
    abstract public boolean specificComputationForFilter() throws IOException;

    public void run() {
        try {
            specificComputationForFilter();
        } catch( IOException e ) {
            if( e instanceof EOFException ) return;
            else System.out.println( e );
        } finally {
            closePorts();
        }
    }

    private void closePorts() {
        try{
            for( PipedInputStream inputStream : ins ){
                if( inputStream != null ) inputStream.close();
            }
            for( PipedOutputStream outputStream : outs ){
                if( outputStream != null ) outputStream.close();
            }
        } catch( IOException e ){
            e.printStackTrace();
        }
    }
}
