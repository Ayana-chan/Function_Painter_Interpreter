import junit.framework.TestCase;

public class MainTest{

    public void testStartMainController() {
        Main main=new Main();
        main.startMainController("funTest.txt");
    }
}