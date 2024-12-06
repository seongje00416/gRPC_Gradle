package clientNserver.client.common;

public class ClientConstants {
    // About Server
    public static final String SEVER_URL = "localhost";
    public static final int SERVER_PORT = 8080;
    // About Encryption
    public static final String ENCRYPT_ALGORITHM = "AES";
    public static final String KEYGEN_ALGORITHM = "RSA";
    public static final int KEY_SIZE = 256;
    // About Error
    public static final String USER_NOT_FOUND_MESSAGE = "User is not Existed";
    // About Log Command
    public static final String LOG_COMMAND_LOGIN = "Login";
    public static final String LOG_COMMAND_RETRIEVE_STUDENTS = "Load Students";
    public static final String LOG_COMMAND_RETRIEVE_COURSES = "Load Courses";
    public static final String LOG_COMMAND_RETRIEVE_LOGS = "Load Logs";
    public static final String LOG_COMMAND_DELETE_STUDENT = "Remove Student";
    public static final String LOG_COMMAND_DELETE_COURSE = "Remove Course";
    public static final String LOG_COMMAND_MAKE_RESERVATION = "Make Reservations";
}
