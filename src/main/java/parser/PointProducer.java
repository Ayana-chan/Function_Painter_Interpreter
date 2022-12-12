package parser;

import javafx.util.Pair;

/**
 * 计算点坐标并存储
 */
public class PointProducer {
    Pair<Double, Double> origin=new Pair<>(0.0,0.0);
    Pair<Double, Double> scale=new Pair<>(1.0,1.0);
    double rot=0.0;



    //set

    public void setOrigin(double x,double y) {
        this.origin = new Pair<>(x,y);
    }

    public void setScale(double x,double y) {
        this.scale = new Pair<>(x,y);
    }

    public void setRot(double rot) {
        this.rot = rot;
    }


    //get

    public Pair<Double, Double> getOrigin() {
        return origin;
    }

    public Pair<Double, Double> getScale() {
        return scale;
    }

    public double getRot() {
        return rot;
    }
}
