package lexer;

import java.io.*;

public class LexerFactory {
    /**
     * produce by fileName
     * @param fileName
     * @return return null if failed
     * @throws FileNotFoundException
     */
    public static Lexer getLexer(String fileName) throws FileNotFoundException {
        Lexer lexer=null;
        //获得文件流
        FileInputStream fileInputStream=new FileInputStream(fileName);//throws FileNotFoundException
        return getLexer(fileInputStream);
    }

    /**
     * produce by fileInputStream
     * @param fileInputStream
     * @return return null if failed
     */
    public static Lexer getLexer(FileInputStream fileInputStream) {
        Lexer lexer=null;
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        try {
            //生产所需的Lexer。TxtReader会立刻把第一个字符读进currChar。
            TxtReader txtReader=new TxtReader(inputStreamReader);
            return new Lexer(txtReader);
        }catch (IOException e){
            e.printStackTrace();
        }
        //失败
        return null;
    }
}
