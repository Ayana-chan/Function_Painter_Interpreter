import lexer.Lexer;
import lexer.LexerFactory;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

public class MainTest {
    @Test
    public void testLexerReading(){
        Lexer lexer=null;
        try {
            lexer = LexerFactory.getLexer("test1.txt");
        }catch (FileNotFoundException e){
            System.out.println("ERROR: File Not Found.");
            return;
        }
        if(lexer==null){
            System.out.println("ERROR: Lexer Production Error.");
            return;
        }
        for(int i=0;i<20;i++){
            try {
                char c=lexer.getTxtReader().currChar;
                if(c=='#'){
                    return;
                }
                System.out.println(c);
                lexer.getTxtReader().readChar();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
