package parser.exceptions.runtime;

public class DivZeroException extends MyRuntimeException{
    public DivZeroException(){
        super("ERROR: Divided by Zero.");
    }
}
