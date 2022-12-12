package parser;

import lexer.Lexer;
import lexer.LexerFactory;
import lexer.Token;
import lexer.TokenTypeEnum;
import parser.treenodes.*;

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
        //获取lexer
        lexer = LexerFactory.getLexer(fileName);
        if(lexer==null){
            throw new RuntimeException("ERROR: Lexer Production Error.");
        }

        fetchToken();
        parseProgram();




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

    private void parseProgram(){
        while(currToken.type!=TokenTypeEnum.NONTOKEN){
            //匹配一句
            parseStatement();
            matchToken(TokenTypeEnum.SEMICO);
        }
    }

    private void parseStatement(){
        System.out.println(currToken.type);
        switch (currToken.type){
            case ORIGIN:
                parseOriginStatement();
                break;
            case SCALE:
                parseScaleStatement();
                break;
            case ROT:
                parseRotStatement();
                break;
            case FOR:
                parseForStatement();
                break;
            default:
                throw new SyntaxErrorException(currToken);
        }
    }

    private void parseOriginStatement(){
        matchToken(TokenTypeEnum.ORIGIN);
        matchToken(TokenTypeEnum.IS);
        matchToken(TokenTypeEnum.L_BRACKET);
        ASTNode e1= parseExpression();
        matchToken(TokenTypeEnum.COMMA);
        ASTNode e2= parseExpression();
        matchToken(TokenTypeEnum.R_BRACKET);


    }

    private void parseScaleStatement(){
        matchToken(TokenTypeEnum.SCALE);
        matchToken(TokenTypeEnum.IS);
        matchToken(TokenTypeEnum.L_BRACKET);
        ASTNode e1= parseExpression();
        matchToken(TokenTypeEnum.COMMA);
        ASTNode e2= parseExpression();
        matchToken(TokenTypeEnum.R_BRACKET);


    }

    private void parseRotStatement(){
        matchToken(TokenTypeEnum.ROT);
        matchToken(TokenTypeEnum.IS);
        ASTNode e=parseExpression();


    }

    private void parseForStatement(){
        matchToken(TokenTypeEnum.FOR);
        matchToken(TokenTypeEnum.T);
        matchToken(TokenTypeEnum.FROM);
        ASTNode from=parseExpression();
        matchToken(TokenTypeEnum.TO);
        ASTNode to=parseExpression();
        matchToken(TokenTypeEnum.STEP);
        ASTNode step=parseExpression();
        matchToken(TokenTypeEnum.DRAW);
        matchToken(TokenTypeEnum.L_BRACKET);
        ASTNode x=parseExpression();
        matchToken(TokenTypeEnum.COMMA);
        ASTNode y=parseExpression();
        matchToken(TokenTypeEnum.R_BRACKET);


    }

    //分析数学表达式
    //加减
    private ASTNode parseExpression(){
        ASTNode left,right;
        TokenTypeEnum localType;
        //左结合然后变成左子树
        left=parseTerm();
        while (currToken.type==TokenTypeEnum.PLUS || currToken.type==TokenTypeEnum.MINUS){
            localType=currToken.type;
            matchToken(currToken.type);
            right = parseTerm();
            left=new BinaryNode(localType,left,right);
        }
        printTree(left);//打印
        return left;
    }

    //乘除
    private ASTNode parseTerm(){
        ASTNode left,right;
        TokenTypeEnum localType;
        left=parseFactor();
        while (currToken.type==TokenTypeEnum.MUL || currToken.type==TokenTypeEnum.DIV){
            localType=currToken.type;
            matchToken(currToken.type);
            right = parseFactor();
            left=new BinaryNode(localType,left,right);
        }
        return left;
    }

    //一元正负
    private ASTNode parseFactor(){
        ASTNode right;
        int negativeCnt=0;//记有几个负号
        while(true){
            if(currToken.type==TokenTypeEnum.PLUS){
                matchToken(currToken.type);
            }else if(currToken.type==TokenTypeEnum.MINUS){
                negativeCnt++;
                matchToken(currToken.type);
            }else{
                break;
            }
        }
        right=parseComponent();
        if(negativeCnt%2==1){
            return new BinaryNode(TokenTypeEnum.MINUS,new ConstNode(TokenTypeEnum.CONST_ID,0),right);
        }
        return right;
    }

    //乘方
    private ASTNode parseComponent(){
        ASTNode left,right;
        left= parseAtom();
        if(currToken.type==TokenTypeEnum.POWER){
            matchToken(currToken.type);
            right=parseComponent();
            return new BinaryNode(TokenTypeEnum.POWER,left,right);
        }
        return left;
    }

    //常量、参数、括号、函数
    private ASTNode parseAtom(){
        switch (currToken.type){
            case CONST_ID:
                matchToken(currToken.type);
                return new ConstNode(TokenTypeEnum.CONST_ID, currToken.value);
            case T:
                matchToken(currToken.type);
                return new TNode(TokenTypeEnum.T);
            case L_BRACKET:
                matchToken(currToken.type);
                ASTNode ans = parseExpression();
                matchToken(TokenTypeEnum.R_BRACKET);
                return ans;
            case FUNC:
                String funName=currToken.lexeme;
                matchToken(currToken.type);
                matchToken(TokenTypeEnum.L_BRACKET);
                ASTNode child=parseExpression();
                matchToken(TokenTypeEnum.R_BRACKET);
                return new FunNode(TokenTypeEnum.FUNC,child,funName);
            default:
                throw new SyntaxErrorException(currToken);
        }
    }

    //辅助程序
    //打印语法树
    private void printTree(ASTNode root){
        System.out.println("Expression Tree:");
        printTree(root,1);
    }

    private void printTree(ASTNode root,int depth){
        for(int i=0;i<depth;i++){
            System.out.print("\t");
        }
        if(root instanceof BinaryNode){
            System.out.println(root.tokenType);
            printTree(((BinaryNode) root).left,depth+1);
            printTree(((BinaryNode) root).right,depth+1);
        }else if(root instanceof FunNode){
            System.out.println(root.tokenType);
            printTree(((FunNode) root).child,depth +1);
        } else if (root instanceof ConstNode) {
            System.out.println(((ConstNode) root).value);
        }else{
            System.out.println("T");
        }
    }
}
