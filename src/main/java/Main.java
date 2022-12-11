import lexer.Lexer;
import lexer.LexerFactory;
import parser.ParserMain;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public void startMainController(String fileName){
        //生成parser
        ParserMain parserMain=new ParserMain();


    }

    public static void main(String[] args) {
        //获取文件名
        if(args.length<1){
            System.out.println("ERROR: Please Input a File's Name!");
            return;
        }
        System.out.println("Aim: "+args[0]);
        Main main=new Main();
        main.startMainController(args[0]);
    }

}
