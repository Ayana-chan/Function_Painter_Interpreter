import org.junit.Test;
import parser.ParserMain;

import java.io.FileNotFoundException;

public class ParseTest {
    @Test
    public void testParse(){
        ParserMain parserMain=new ParserMain();
        try {
            parserMain.parse("test2.txt");
        }catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }

    }
}
