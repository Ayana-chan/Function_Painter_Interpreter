package parser.exceptions.runtime;

public class MyRuntimeException extends RuntimeException{
    public MyRuntimeException(){
        super("ERROR: Runtime Exception.");
    }
    public MyRuntimeException(String s){
        super(s);
    }
}
