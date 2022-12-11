package lexer;

import java.io.*;

public class LexerFactory {
    /**
     *
     * @param fileName
     * @return return null if failed
     * @throws FileNotFoundException
     */
    public static Lexer getLexer(String fileName) throws FileNotFoundException {
        Lexer lexer=null;
        FileInputStream fileInputStream=new FileInputStream(fileName);//throws FileNotFoundException
        InputStreamReader inputStreamReader=null;
        try {
            inputStreamReader = new InputStreamReader(fileInputStream);
            //生产所需的Lexer。TxtReader会立刻把第一个字符读进currChar。
            TxtReader txtReader=new TxtReader(inputStreamReader);
            return new Lexer(txtReader);
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(inputStreamReader!=null){
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }
}
