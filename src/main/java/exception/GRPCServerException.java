package exception;

public class GRPCServerException extends Exception{

    public GRPCServerException(String msg){

    }

    public GRPCServerException(Throwable cause){
        System.out.println( cause.getCause().getMessage() );
    }

}
