package server.common;

public class TUIView {

    public void mainView() {
        System.out.println("Select Operation.");
        System.out.println("1. Load Students");
        System.out.println("2. Load Courses");
        System.out.println("0. Exit");
    }

    public void loginView() {

    }

    public void listViewStart() {
        System.out.println( "===================== List =====================");
    }

    public void listViewEnd() {
        System.out.println("==================================================");
    }

}
