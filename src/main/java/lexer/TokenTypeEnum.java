package lexer;

import java.util.HashMap;
import java.util.Map;

public enum TokenTypeEnum {
    ID,COMMENT,//参见正规式设计
    ORIGIN,SCALE,ROT,IS,//保留字
    TO,STEP,DRAW,FOR,FROM,//保留字
    T, //参数
    SEMICO,L_BRACKET,R_BRACKET,COMMA,//分隔符
    PLUS,MINUS,MUL,DIV,POWER,//运算符
    FUNC,//函数名（调用）
    CONST_ID, //常数（数值字面量、命名常量）
    NONTOKEN,//源程序结束（#）
    ERRTOKEN//错误单词（非法输入）
    ;

    /**
     * 存储字符-Token转换关系（不包含数字与错误）
     */
    private static Map<String,Token> stringTransTokenMap =new HashMap<>();
    static {
        //保留字
        stringTransTokenMap.put("ORIGIN",new Token(ORIGIN,"ORIGIN",0));
        stringTransTokenMap.put("SCALE",new Token(SCALE,"SCALE",0));
        stringTransTokenMap.put("ROT",new Token(ROT,"ROT",0));
        stringTransTokenMap.put("IS",new Token(IS,"IS",0));
        stringTransTokenMap.put("TO",new Token(TO,"TO",0));
        stringTransTokenMap.put("STEP",new Token(STEP,"STEP",0));
        stringTransTokenMap.put("DRAW",new Token(DRAW,"DRAW",0));
        stringTransTokenMap.put("FOR",new Token(FOR,"FOR",0));
        stringTransTokenMap.put("FROM",new Token(FROM,"FROM",0));
        //分隔符
        stringTransTokenMap.put(";",new Token(SEMICO,";",0));
        stringTransTokenMap.put("(",new Token(L_BRACKET,"(",0));
        stringTransTokenMap.put(")",new Token(R_BRACKET,")",0));
        stringTransTokenMap.put(",",new Token(COMMA,",",0));
        //运算符
        stringTransTokenMap.put("+",new Token(PLUS,"+",0));
        stringTransTokenMap.put("-",new Token(MINUS,"-",0));//"--"前缀
        stringTransTokenMap.put("*",new Token(MUL,"*",0));//"**"前缀
        stringTransTokenMap.put("/",new Token(DIV,"/",0));//"//"前缀
        stringTransTokenMap.put("**",new Token(POWER,"**",0));//
        //函数名
        stringTransTokenMap.put("SIN",new Token(FUNC,"SIN",0));
        stringTransTokenMap.put("COS",new Token(FUNC,"COS",0));
        stringTransTokenMap.put("TAN",new Token(FUNC,"TAN",0));//
        stringTransTokenMap.put("LN",new Token(FUNC,"LN",0));
        stringTransTokenMap.put("EXP",new Token(FUNC,"EXP",0));//
        stringTransTokenMap.put("SQRT",new Token(FUNC,"SQRT",0));
        //参数
        stringTransTokenMap.put("T",new Token(T,"T",0));//"TAN"前缀
        //常数
        stringTransTokenMap.put("PI",new Token(CONST_ID,"PI",3.1415926));
        stringTransTokenMap.put("E",new Token(CONST_ID,"E",2.71828));//"EXP"前缀
        //注释
        stringTransTokenMap.put("//",new Token(COMMENT,"//",0));//
        stringTransTokenMap.put("--",new Token(COMMENT,"--",0));//
        //结束
        stringTransTokenMap.put(""+TxtReader.EOF,new Token(NONTOKEN,"EOF(#)",0));
    }

    /**
     * 将字符转换为Token
     * @param s
     * @return
     */
    public static Token stringTransToken(String s){
        Token ans= stringTransTokenMap.get(s);
        if(ans==null){
            return new Token(ERRTOKEN,s,0);
        }
        return ans;
    }
}

