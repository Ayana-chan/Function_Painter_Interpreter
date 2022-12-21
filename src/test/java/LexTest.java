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
            lexer = LexerFactory.getLexer("lexTest.txt");
        }catch (FileNotFoundException e){
            System.err.println("ERROR: File Not Found.");
            return;
        }
        if(lexer==null){
            System.err.println("ERROR: Lexer Production Error.");
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
