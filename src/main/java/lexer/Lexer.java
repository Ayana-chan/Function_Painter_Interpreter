package lexer;

public class Lexer {
    private TxtReader txtReader;

    public Lexer(TxtReader txtReader){
        this.txtReader=txtReader;
    }


    //get

    //for test
    public TxtReader getTxtReader() {
        return txtReader;
    }
}
