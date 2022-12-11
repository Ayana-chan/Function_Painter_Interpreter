package lexer;

public class Token {
    public TokenTypeEnum type;
    public String lexeme;//原始串内容
    public double value;//数值

    public Token(TokenTypeEnum type, String lexeme, double value) {
        this.type = type;
        this.lexeme = lexeme;
        this.value = value;
    }
}
