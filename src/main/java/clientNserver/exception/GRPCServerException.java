package clientNserver.exception;

public class GRPCServerException extends RuntimeException {

    public enum ErrorType {
        IO_ERROR( "입출력 오류" ),
        PORT_ERROR( "포트 바인딩 오류" ),
        SERVICE_START_ERROR( "서비스 시작 오류" ),
        UNKNOWN_ERROR( "알 수 없는 오류" );

        private final String description;

        ErrorType( String description ){
            this.description = description;
        }

        public String getDescription(){
            return description;
        }
    }

    public final ErrorType errorType;

    public GRPCServerException( ErrorType errorType, String message ){
        super(errorType.getDescription() + ": " + message );
        this.errorType = errorType;
    }

    public GRPCServerException(ErrorType errorType, String message, Throwable cause) {
        super(errorType.getDescription() + ": " + message, cause);
        this.errorType = errorType;
        System.out.println( errorType.getDescription() );
    }

    public ErrorType getErrorType() {return errorType;}

}
