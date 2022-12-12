package parser;

import drawer.PointManager;
import javafx.util.Pair;
import parser.exceptions.runtime.FunctionArgumentOutOfBoundException;
import parser.exceptions.runtime.MyRuntimeException;
import parser.exceptions.runtime.ToLessThanFromException;
import parser.treenodes.ASTNode;

/**
 * 计算点坐标并存储
 */
public class PointProducer {
    Pair<Double, Double> origin=new Pair<>(0.0,0.0);
    Pair<Double, Double> scale=new Pair<>(1.0,1.0);
    double rot=0.0;

    /**
     * for语句，指定一个pointManager加入所有点。
     * 注意，如果函数参数超出作用域，则会提示warning并忽略此点
     * @param pointManager
     * @param expressionFrom
     * @param expressionTo
     * @param expressionStep
     * @param expressionX
     * @param expressionY
     * @throws MyRuntimeException
     */
    public void createPoint(PointManager pointManager,ASTNode expressionFrom, ASTNode expressionTo, ASTNode expressionStep, ASTNode expressionX,ASTNode expressionY) throws MyRuntimeException {
        double from,to,step,x,y;
        try{
            from = ExpressionCalculator.calExpression(expressionFrom);
            to = ExpressionCalculator.calExpression(expressionTo);
            step = ExpressionCalculator.calExpression(expressionStep);
        }catch (FunctionArgumentOutOfBoundException e){
            System.out.println(e.getMessage()); //忽略越界
            System.out.println("\"FOR\" Has been Ignored.");
            return;
        }


        if(from>to){
            throw new ToLessThanFromException();
        }
        int cnt=(int)((to-from)/step);
        double T=from;
        for(int i=0;i<=cnt;i++){
            try {
                x = ExpressionCalculator.calExpression(expressionX, T);
                y = ExpressionCalculator.calExpression(expressionY, T);
                pointManager.addPoint(x,y);
            }catch (FunctionArgumentOutOfBoundException e){
                System.out.println(e.getMessage()); //忽略越界
                System.out.println("A Point Has been Ignored.");
            }
            T+=step;
        }
    }

    //set

    /**
     * 注意，如果函数参数超出作用域，则会提示warning并忽略此次操作
     * @param expressionX
     * @param expressionY
     */
    public void setOrigin(ASTNode expressionX,ASTNode expressionY) {
        try {
            double x = ExpressionCalculator.calExpression(expressionX);
            double y = ExpressionCalculator.calExpression(expressionY);
            this.origin = new Pair<>(x,y);
        }catch (FunctionArgumentOutOfBoundException e){
            System.out.println(e.getMessage()); //忽略越界
            System.out.println("\"ORIGIN\" Has been Ignored.");
        }
    }

    public void setScale(ASTNode expressionX,ASTNode expressionY) {
        try {
            double x = ExpressionCalculator.calExpression(expressionX);
            double y = ExpressionCalculator.calExpression(expressionY);
            this.scale = new Pair<>(x,y);
        }catch (FunctionArgumentOutOfBoundException e){
            System.out.println(e.getMessage()); //忽略越界
            System.out.println("\"SCALE\" Has been Ignored.");
        }
    }

    public void setRot(ASTNode expressionR) {
        try {
            double r = ExpressionCalculator.calExpression(expressionR);
            this.rot = r;
        }catch (FunctionArgumentOutOfBoundException e){
            System.out.println(e.getMessage()); //忽略越界
            System.out.println("\"ROT\" Has been Ignored.");
        }
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
