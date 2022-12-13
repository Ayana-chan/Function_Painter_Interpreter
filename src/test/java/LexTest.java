import lexer.Lexer;
import lexer.LexerFactory;
import lexer.Token;
import lexer.TokenTypeEnum;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

public class LexTest {
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
        Token token;
        do{
            try {
                token=lexer.getToken();
                System.out.println(token.type+" "+token.lexeme+" "+token.value);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }while (token.type!= TokenTypeEnum.NONTOKEN);
    }
}
