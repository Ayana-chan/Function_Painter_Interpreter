package lexer;

import java.io.IOException;

public class Lexer {
    private TxtReader txtReader;

    public Lexer(TxtReader txtReader){
        this.txtReader=txtReader;
    }

    public Token fetchToken() throws IOException {
        //结束符立即返回
        if(txtReader.currChar=='#'){
            return TokenTypeEnum.stringTransToken("#");
        }
        //跳过空白
        while(isSpace(txtReader.currChar)){
            txtReader.readChar();
        }
        StringBuilder s= new StringBuilder();
        //到截止符（空格、结束符之前），拼接字符成串
        while(!isSpace(txtReader.currChar) && txtReader.currChar!='#'){
            s.append(txtReader.currChar);
            txtReader.readChar();
        }
        return TokenTypeEnum.stringTransToken(String.valueOf(s));
    }


    private static boolean isSpace(char c){
        return " \t\n\r\f".contains(""+c);
    }

    private static boolean isDigit(char c){
        return c>='0' && c<='9';
    }


    //get

    //for test
    public TxtReader getTxtReader() {
        return txtReader;
    }
}
