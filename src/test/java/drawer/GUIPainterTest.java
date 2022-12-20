package drawer;

import javafx.util.Pair;
import junit.framework.TestCase;

import java.util.HashSet;
import java.util.Set;

public class GUIPainterTest{

    public void testPaintGUI() {
        Set<Pair<Integer,Integer>> points = new HashSet<>();
        points.add(new Pair<>(100,100));
        points.add(new Pair<>(200,400));
        points.add(new Pair<>(120,30));
        GUIPainter painterAPI=new GUIPainter();
        painterAPI.paintGUI(points);
    }
}