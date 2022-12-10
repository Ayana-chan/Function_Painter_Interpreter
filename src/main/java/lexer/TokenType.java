package lexer;

public enum TokenType {
    ID,COMMENT,//参见正规式设计
    ORIGIN,SCALE,ROT,IS,//保留字
    TO,STEP,DRAW,FOR,FROM,//保留字
    T, //参数
    SEMICO,L_BRACKET,R_BRACKET,COMMA,//分隔符
    PLUS,MINUS,MUL,DIV,POWER,//运算符
    FUNC,//函数名（调用)
    CONST_ID, //常数（数值字面量、命名常量)
    NONTOKEN,//源程序结束(#)
    ERRTOKEN,//错误单词（非法输入
}

