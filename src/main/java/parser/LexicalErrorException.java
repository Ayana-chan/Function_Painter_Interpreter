package parser;

import lexer.Token;
import lexer.TokenTypeEnum;

public class LexicalErrorException extends RuntimeException{
    public LexicalErrorException(Token token){
        super("ERROR: Lex Failed.\n" +
                "Error String: "+token.lexeme);
    }
}
