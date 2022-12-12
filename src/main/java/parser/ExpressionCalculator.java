package parser;

import parser.exceptions.runtime.DivZeroException;
import parser.exceptions.runtime.FunctionArgumentOutOfBoundException;
import parser.treenodes.ASTNode;
import parser.treenodes.BinaryNode;
import parser.treenodes.ConstNode;
import parser.treenodes.FunNode;

/**
 * 计算表达式（代入语法树）
 */
public class ExpressionCalculator {
    public static double calExpression(ASTNode root){
        return calExpression(root,0);
    }
    //如果在for语句的最后之外的地方使用了T的话，T=0
    public static double calExpression(ASTNode root,double T){
        //后序遍历
        if(root instanceof BinaryNode){
            double l=calExpression(((BinaryNode) root).left,T);
            double r=calExpression(((BinaryNode) root).right,T);
            switch (root.tokenType){
                case PLUS:
                    return l+r;
                case MINUS:
                    return l-r;
                case MUL:
                    return l*r;
                case DIV:
                    if(r==0){
                        throw new DivZeroException();
                    }
                    return l/r;
            }
        }else if(root instanceof FunNode){
            double c=calExpression(((FunNode) root).child,T);
            return executeFun(((FunNode) root).funName,c);
        } else if (root instanceof ConstNode) {
            return ((ConstNode) root).value;
        }else{
            return T;
        }
        throw new RuntimeException("Unexpected Exception.");
    }

    private static double executeFun(String funName,double argument){
        double ans;
        switch (funName){
            case "SIN":
                return Math.sin(argument);
            case "COS":
                return Math.cos(argument);
            case "TAN":
                ans=Math.tan(argument);
                if(Double.isNaN(ans)){
                    throw new FunctionArgumentOutOfBoundException(funName,argument);
                }
                return ans;
            case "LN":
                ans=Math.log(argument);
                if(Double.isNaN(ans) || argument==0){
                    throw new FunctionArgumentOutOfBoundException(funName, argument);
                }
                return ans;
            case "EXP":
                return Math.exp(argument);
            case "SQRT":
                ans=Math.sqrt(argument);
                if(Double.isNaN(ans)){
                    throw new FunctionArgumentOutOfBoundException(funName,argument);
                }
                return ans;
        }
        throw new RuntimeException("Unexpected Exception.");
    }
}
