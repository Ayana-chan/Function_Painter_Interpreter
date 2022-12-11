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
        //根据开头字符，分为三种情况进行拼接
        //数字
        if(isDigit(txtReader.currChar)){
            StringBuilder s= new StringBuilder();
            boolean isDotExisted=false;//是否读出小数点
            while(isDigit(txtReader.currChar) || txtReader.currChar=='.'){
                if(txtReader.currChar=='.'){
                    //俩小数点，返回错误
                    if(isDotExisted){
                        return new Token(TokenTypeEnum.ERRTOKEN,String.valueOf(s),0);
                    }
                    isDotExisted=true;
                }
                s.append(txtReader.currChar);
                txtReader.readChar();
            }
            //建立Token时要计算其数值
            return new Token(TokenTypeEnum.CONST_ID,String.valueOf(s),Double.parseDouble(String.valueOf(s)));
        }
        //字母开头，目前只有纯字母才符合（保留字、函数名、参数、常数）
        else if(isAlpha(txtReader.currChar)){
            StringBuilder s= new StringBuilder();
            while(isAlpha(txtReader.currChar)){
                s.append(txtReader.currChar);
                txtReader.readChar();
            }
            return TokenTypeEnum.stringTransToken(String.valueOf(s));
        }
        //运算符、分隔符。除乘法以外都是单字符成Token
        else{
            Token token=TokenTypeEnum.stringTransToken(""+txtReader.currChar);
            txtReader.readChar();
            if(token.type==TokenTypeEnum.MUL && txtReader.currChar=='*'){
                token=TokenTypeEnum.stringTransToken("**");
            }
            return token;
        }
    }


    private static boolean isSpace(char c){
        return " \t\n\r\f".contains(""+c);
    }

    private static boolean isDigit(char c){
        return c>='0' && c<='9';
    }

    private static boolean isAlpha(char c){
        return c>='A' && c<='Z';
    }


    //get

    //for test
    public TxtReader getTxtReader() {
        return txtReader;
    }
}
