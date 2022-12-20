import drawer.Drawer;
import javafx.util.Pair;
import parser.ParserMain;

import legacy.inter;

import java.io.FileNotFoundException;

public class Main {
    public void startMainController(String fileName){
        //生成parser
        ParserMain parserMain=new ParserMain();
        //parse
        try {
            parserMain.parse(fileName);
        }catch (FileNotFoundException e){
            System.err.println(e.getMessage());
        }

        //画图
        Drawer drawer=new Drawer();
        drawer.draw(parserMain.getPointManager());
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
        inter test=new inter();
    }

}
