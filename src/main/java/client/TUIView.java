package client;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TUIView {
    public void mainView() {
        System.out.println("Select Operation.");
        System.out.println("1. Load Students");
        System.out.println("2. Load Courses");
        System.out.println("3. Load Logs");
        System.out.println("0. Exit");
    }
    public String[] loginView() {
        String id;
        String password;
        BufferedReader bf = new BufferedReader( new InputStreamReader( System.in ) );
        try{
            System.out.println( "############## Log In ##############");
            System.out.print( "ID: " );
            id = bf.readLine();
            System.out.println();
            System.out.print( "PW: " );
            password = bf.readLine();
            String[] result = { id, password };
            return result;
        } catch( Exception e ){System.out.println( "Code 801: IO Problem");}return null;}
    public void listViewStart() {System.out.println( "===================== List =====================");}
    public void listViewEnd() {System.out.println("==================================================");}
    public void lineView(){System.out.println( "-----------------------------------------------------------" );}
}
