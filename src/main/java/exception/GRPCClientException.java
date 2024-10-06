package exception;

public class GRPCClientException extends RuntimeException {

    public enum ErrorType {
        CONNECTION_ERROR("Connection Error"),
        RPC_ERROR("RPC Call Error"),
        INPUT_ERROR("Input Error"),
        SHUTDOWN_ERROR("Terminate Error"),
        UNKNOWN_ERROR("Unknown Error");

        private final String description;

        ErrorType(String description) {
            this.description = description;
        }
        public String getDescription() {return description;}
    }

    private final ErrorType errorType;

    public GRPCClientException(ErrorType errorType, String message) {
        super(errorType.getDescription() + ": " + message);
        this.errorType = errorType;
    }

    public GRPCClientException(ErrorType errorType, String message, Throwable cause) {
        super(errorType.getDescription() + ": " + message, cause);
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {return errorType;}
}