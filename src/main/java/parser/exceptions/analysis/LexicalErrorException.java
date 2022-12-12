package parser.exceptions.analysis;

import lexer.Token;
import lexer.TokenTypeEnum;

public class LexicalErrorException extends MyAnalysisException{
    public LexicalErrorException(Token token){
        super("ERROR: Lex Failed.\n" +
                "Error String: "+token.lexeme);
    }
}
