import junit.framework.TestCase;

public class MainTest extends TestCase {

    public void testStartMainController() {
        Main main=new Main();
        main.startMainController("funTest.txt");
    }
}