package parser.exceptions.analysis;

public class MyAnalysisException extends RuntimeException{
    public MyAnalysisException(){
        super("ERROR: Analysis Exception.");
    }
    public MyAnalysisException(String s){
        super(s);
    }
}
