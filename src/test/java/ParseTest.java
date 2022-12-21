import javafx.util.Pair;
import org.junit.Test;
import parser.ParserMain;

import java.io.FileNotFoundException;

public class ParseTest {
    @Test
    public void testParse(){
        ParserMain parserMain=new ParserMain();
        try {
//            parserMain.parse("funTest.txt");
//            parserMain.parse("lexTest.txt");
            parserMain.parse("parseTest.txt");
            for(Pair<Double,Double> p:parserMain.getPointManager().getPoints()){
                System.out.println("("+p.getKey()+","+p.getValue()+")");
            }
        }catch (FileNotFoundException e){
            System.err.println(e.getMessage());
        }
    }
}
