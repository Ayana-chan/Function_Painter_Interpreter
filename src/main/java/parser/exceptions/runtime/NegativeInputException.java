package parser.exceptions.runtime;

public class NegativeInputException extends MyRuntimeException{
    public NegativeInputException(String aim){
        super("ERROR: "+ aim+" Can't Be Negative.");
    }
}
