package lexer;

import java.io.IOException;

public class Lexer {
    private TxtReader txtReader;

    public Lexer(TxtReader txtReader){
        this.txtReader=txtReader;
    }

    /**
     * 需要紧急切断分析的只有两种：结束符和注释符。
     * 由于它们既不是数字也不是字母，所以不会在前两种情况内被误吃。
     * @return
     * @throws IOException
     */
    public Token getToken() throws IOException {
        //结束符立即关闭流并返回（Lexer至此结束）
        if(txtReader.currChar==TxtReader.EOF){
            txtReader.closeReader();
            return TokenTypeEnum.stringTransToken(""+TxtReader.EOF);
        }
        //跳过空白
        while(isSpace(txtReader.currChar)){
            txtReader.readChar();
        }
        //根据开头字符，分为三种情况进行拼接
        //1.数字
        if(isDigit(txtReader.currChar)){
            StringBuilder s= new StringBuilder();
            boolean isDotExisted=false;//是否读出小数点
            while(isDigit(txtReader.currChar) || txtReader.currChar=='.'){
                if(txtReader.currChar=='.'){
                    //俩小数点，返回错误
                    if(isDotExisted){
                        //把连在一起的数字和字母吃掉
                        while(isDigit(txtReader.currChar) || txtReader.currChar=='.' || isAlpha(txtReader.currChar)){
                            s.append(txtReader.currChar);
                            txtReader.readChar();
                        }
                        return new Token(TokenTypeEnum.ERRTOKEN,String.valueOf(s),0);
                    }
                    isDotExisted=true;
                }
                s.append(txtReader.currChar);
                txtReader.readChar();
            }
            //如果数字后面紧接着字母，也是错误
            if(isAlpha(txtReader.currChar)){
                //把连在一起的数字和字母吃掉
                while(isDigit(txtReader.currChar) || txtReader.currChar=='.' || isAlpha(txtReader.currChar)){
                    s.append(txtReader.currChar);
                    txtReader.readChar();
                }
                return new Token(TokenTypeEnum.ERRTOKEN,String.valueOf(s),0);
            }
            //建立Token时要计算其数值
            return new Token(TokenTypeEnum.CONST_ID,String.valueOf(s),Double.parseDouble(String.valueOf(s)));
        }
        //2.字母开头。目前只有纯字母才符合（保留字、函数名、参数、常数），但把数字也吃了可以方便报错
        else if(isAlpha(txtReader.currChar)){
            StringBuilder s= new StringBuilder();
            while(isAlpha(txtReader.currChar) || isDigit(txtReader.currChar)){
                s.append(txtReader.currChar);
                txtReader.readChar();
            }
            return TokenTypeEnum.stringTransToken(String.valueOf(s));
        }
        //3.运算符、分隔符。除乘法以外都是单字符成Token
        else{
            Token token=TokenTypeEnum.stringTransToken(""+txtReader.currChar);
            txtReader.readChar();
            //指数
            if(token.type==TokenTypeEnum.MUL && txtReader.currChar=='*'){
                token=TokenTypeEnum.stringTransToken("**");
                txtReader.readChar();
            }
            //注释
            else if(token.type==TokenTypeEnum.DIV && txtReader.currChar=='/' || token.type==TokenTypeEnum.MINUS && txtReader.currChar=='-'){
                token=TokenTypeEnum.stringTransToken(txtReader.currChar+""+txtReader.currChar);
                StringBuilder s= new StringBuilder();
                s.append(txtReader.currChar);
                //读到行末或结束（不检测结束的话会导致进入死循环，一直读取EOF但不停止）
                while(txtReader.currChar!='\n' && txtReader.currChar!='\r' && txtReader.currChar!=TxtReader.EOF){
                    s.append(txtReader.currChar);
                    txtReader.readChar();
                }
                token.lexeme= String.valueOf(s);
                return getToken();//找下一个Token来返回（如果语法分析不嫌弃COMMENT可以不需要这句）
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

    public TxtReader getTxtReader() {
        return txtReader;
    }
}
