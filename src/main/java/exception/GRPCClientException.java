package exception;

public class GRPCClientException extends RuntimeException {

    public enum ErrorType {
        CONNECTION_ERROR("연결 오류"),
        RPC_ERROR("RPC 호출 오류"),
        INPUT_ERROR("입력 오류"),
        SHUTDOWN_ERROR("종료 오류"),
        UNKNOWN_ERROR("알 수 없는 오류");

        private final String description;

        ErrorType(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
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

    public ErrorType getErrorType() {
        return errorType;
    }
}