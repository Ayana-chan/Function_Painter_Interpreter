package lexer;

import java.io.IOException;
import java.io.InputStreamReader;

public class TxtReader {
    /**
     * 65535, means the end of the txt
     */
    public static char EOF=(char) -1;

    /**
     * 存储接下来要处理的字符。
     * 生成时自动读取到第一个字符。
     * 应当在处理完当前字符后进行一次读取以给下一次使用。
     */
    public char currChar;

    //记录当前字符（currChar）所在行列
    private int line=1;
    private int col=1;

    private InputStreamReader inputStreamReader;

    public TxtReader(InputStreamReader inputStreamReader) throws IOException {
        this.inputStreamReader=inputStreamReader;
        readChar();//生成对象时就读取第一个字符
    }

    public void readChar() throws IOException {
        //行列变化
        if(currChar=='\n'){ //注：/n总是在/r后，且不会在这种换行符处报错
            line++;
            col=1;
        }else{
            col++;
        }
        //读取
        int in=inputStreamReader.read();
        //转化为char
        currChar = Character.toUpperCase((char) in);
    }

    public void closeReader(){
        if(inputStreamReader!=null) {
            try {
                inputStreamReader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    //get

    public char getCurrChar() {
        return currChar;
    }

    public int getLine() {
        return line;
    }

    public int getCol() {
        return col;
    }
}
