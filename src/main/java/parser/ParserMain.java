package parser;

import lexer.Lexer;
import lexer.LexerFactory;
import lexer.Token;
import lexer.TokenTypeEnum;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ParserMain {
    private Token currToken;

    private Lexer lexer;

    public ParserMain(){}//let lexer assigned in function 'parse'
//    public ParserMain(String fileName) throws FileNotFoundException {
//        lexer = LexerFactory.getLexer(fileName);
//        if(lexer==null){
//            throw new RuntimeException("ERROR: Lexer Production Error.");
//        }
//
//    }
//    public ParserMain(FileInputStream fileInputStream){
//        lexer = LexerFactory.getLexer(fileInputStream);
//        if(lexer==null){
//            throw new RuntimeException("ERROR: Lexer Production Error.");
//        }
//    }
//    public ParserMain(Lexer lexer){
//        this.lexer=lexer;
//    }

    public void parse(String fileName) throws FileNotFoundException,LexicalErrorException,SyntaxErrorException{
        lexer = LexerFactory.getLexer(fileName);
        if(lexer==null){
            throw new RuntimeException("ERROR: Lexer Production Error.");
        }




    }

    /**
     * 获取一个Token
     */
    private void fetchToken() throws LexicalErrorException{
        try {
            currToken=lexer.getToken();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(currToken.type==TokenTypeEnum.ERRTOKEN){
            throw new LexicalErrorException(currToken);
        }
    }

    /**
     * 将当前token类型与目标类型进行匹配，若成功则自动获取下一个token
     * @param aimType 目标类型
     */
    private void matchToken(TokenTypeEnum aimType) throws SyntaxErrorException{
        if(currToken.type!=aimType){
            throw new SyntaxErrorException(currToken,aimType);
        }
        fetchToken();
    }
}
