package parser.exceptions.runtime;

public class ToLessThanFromException extends MyRuntimeException{
    public ToLessThanFromException(){
        super("ERROR: \"TO\"'s Value is Less Than \"FROM\"'s");
    }
}
