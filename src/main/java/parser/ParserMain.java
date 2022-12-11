package parser;

import lexer.Lexer;
import lexer.LexerFactory;
import lexer.Token;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ParserMain {
    private Token currToken;

    private Lexer lexer;

    public ParserMain(String fileName) throws FileNotFoundException {
        lexer = LexerFactory.getLexer(fileName);
        if(lexer==null){
            throw new RuntimeException("ERROR: Lexer Production Error.");
        }
    }

    public ParserMain(FileInputStream fileInputStream){
        lexer = LexerFactory.getLexer(fileInputStream);
        if(lexer==null){
            throw new RuntimeException("ERROR: Lexer Production Error.");
        }
    }

    public ParserMain(Lexer lexer){
        this.lexer=lexer;
    }

    public void parse(){

    }
}
