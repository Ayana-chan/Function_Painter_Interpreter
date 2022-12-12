package parser.exceptions.runtime;

public class FunctionArgumentOutOfBoundException extends MyRuntimeException{
    public FunctionArgumentOutOfBoundException(String funName,double argument){
        super("WARNING: "+funName+"("+argument+") is Not Legal.");
    }
}
