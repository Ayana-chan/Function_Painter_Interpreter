package parser;

import lexer.Lexer;
import lexer.LexerFactory;
import lexer.Token;
import lexer.TokenTypeEnum;
import parser.exceptions.analysis.LexicalErrorException;
import parser.exceptions.analysis.MyAnalysisException;
import parser.exceptions.analysis.SyntaxErrorException;
import parser.exceptions.runtime.MyRuntimeException;
import parser.treenodes.*;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ParserMain {
    public boolean isTreePrinterOn=true;//控制parser是否打印语法树

    private Token currToken;

    private Lexer lexer;

    private PointProducer pointProducer=new PointProducer();//点生产者，存储了各个参数值
    private PointManager pointManager=new PointManager();//点管理库，保存所有生成的点用于前端显示

    public void parse(String fileName) throws FileNotFoundException, LexicalErrorException, SyntaxErrorException {
        //获取lexer
        lexer = LexerFactory.getLexer(fileName);
        if(lexer==null){
            throw new RuntimeException("ERROR: Lexer Production Error.");
        }
        try {
            fetchToken();//读入第一个token
            //以Program为入口开始语法分析
            parseProgram();
        }catch (MyAnalysisException | MyRuntimeException e){
            System.err.println(e.getMessage());
            System.err.println("Error Position: line:"+lexer.getTxtReader().getLine()+" ,col:"+lexer.getTxtReader().getCol());
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
//        System.out.println("fetch: "+currToken.type);
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

    //----------------------------------------------------
    //parse functions

    /**
     * 分析程序的入口。分析过程中自动执行，将结果点添加到pointManager
     */
    private void parseProgram(){
        while(currToken.type!=TokenTypeEnum.NONTOKEN){
            //匹配一句
            parseStatement();
            matchToken(TokenTypeEnum.SEMICO);
        }
    }

    private void parseStatement(){
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
        ASTNode x= parseExpression();
        matchToken(TokenTypeEnum.COMMA);
        ASTNode y= parseExpression();
        matchToken(TokenTypeEnum.R_BRACKET);

        pointProducer.setOrigin(x,y);
    }

    private void parseScaleStatement(){
        matchToken(TokenTypeEnum.SCALE);
        matchToken(TokenTypeEnum.IS);
        matchToken(TokenTypeEnum.L_BRACKET);
        ASTNode x= parseExpression();
        matchToken(TokenTypeEnum.COMMA);
        ASTNode y= parseExpression();
        matchToken(TokenTypeEnum.R_BRACKET);

        pointProducer.setScale(x,y);
    }

    private void parseRotStatement(){
        matchToken(TokenTypeEnum.ROT);
        matchToken(TokenTypeEnum.IS);
        ASTNode r=parseExpression();

        pointProducer.setRot(r);
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

        pointProducer.createPoint(pointManager,from,to,step,x,y);
    }

    //分析数学表达式----------------------------------------

    /**
     * 分析数学表达式的入口，构建语法树
     * @return
     */
    //加减
    private ASTNode parseExpression(){
        ASTNode ans= parseExpressionWithoutPrint();
        printTree(ans);//打印
        return ans;
    }
    //比parseExpression少了Print功能，防止Atom回到Expression的时候打印树出现意外现象
    private ASTNode parseExpressionWithoutPrint(){
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
        //"--"被记为注释，因此不会出现连续负号，测试时应当注意
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
        ASTNode ans;
        switch (currToken.type){
            case CONST_ID:
                ans=new ConstNode(TokenTypeEnum.CONST_ID, currToken.value);
                matchToken(currToken.type);
                return ans;
            case T:
                matchToken(currToken.type);
                return new TNode(TokenTypeEnum.T);
            case L_BRACKET:
                matchToken(currToken.type);
                ans = parseExpressionWithoutPrint();
                matchToken(TokenTypeEnum.R_BRACKET);
                return ans;
            case FUNC:
                String funName=currToken.lexeme;
                matchToken(currToken.type);
                matchToken(TokenTypeEnum.L_BRACKET);
                ASTNode child=parseExpressionWithoutPrint();
                matchToken(TokenTypeEnum.R_BRACKET);
                return new FunNode(TokenTypeEnum.FUNC,child,funName);
            default:
                throw new SyntaxErrorException(currToken);
        }
    }

    //--------------------------------------------------------
    //辅助程序
    /**
     * 打印语法树
     */
    private void printTree(ASTNode root){
        if(!isTreePrinterOn){
            return;
        }
        System.out.println("\nExpression Tree:");
        printTree(root,1);
        System.out.println();
    }

    private void printTree(ASTNode root,int depth){
        for(int i=0;i<depth;i++){
            System.out.print("\t|");
        }
        if(root instanceof BinaryNode){
            System.out.println(root.tokenType);
            printTree(((BinaryNode) root).left,depth+1);
            printTree(((BinaryNode) root).right,depth+1);
        }else if(root instanceof FunNode){
            System.out.println(((FunNode) root).funName);
            printTree(((FunNode) root).child,depth +1);
        } else if (root instanceof ConstNode) {
            System.out.println(((ConstNode) root).value);
        }else{
            System.out.println("T");
        }
    }


    //get

    public PointProducer getPointProducer() {
        return pointProducer;
    }

    public PointManager getPointManager() {
        return pointManager;
    }

    //set

    public void setTreePrinterOn(boolean treePrinterOn) {
        isTreePrinterOn = treePrinterOn;
    }

    public void setPointProducer(PointProducer pointProducer) {
        this.pointProducer = pointProducer;
    }

    public void setPointManager(PointManager pointManager) {
        this.pointManager = pointManager;
    }
}
