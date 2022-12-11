package parser;

import lexer.Token;
import lexer.TokenTypeEnum;

public class SyntaxErrorException extends RuntimeException{
    public SyntaxErrorException(Token token, TokenTypeEnum aimTokenType){
        super("ERROR: Parse Failed.\n" +
                "Error String: "+token.lexeme+"\n" +
                "Error Token Type: "+token.type+"\n" +
                "Supposed Token Type: "+aimTokenType);
    }
}
