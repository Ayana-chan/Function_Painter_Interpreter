package drawer;

import javafx.util.Pair;
import parser.PointManager;

import java.util.HashSet;
import java.util.Set;

public class Drawer {
    public void draw(PointManager pointManager){
        Set<Pair<Double,Double>> points=pointManager.getPoints();
        //转换成整数坐标
        Set<Pair<Integer,Integer>> intPoints=new HashSet<>();
        for(Pair<Double,Double> p:points){
            intPoints.add(new Pair<>(p.getKey().intValue(),p.getValue().intValue()));
//            System.out.println(p.getKey().intValue()+" , "+p.getValue().intValue());
        }
        GUIPainter.paintGUI(intPoints);
    }
}
