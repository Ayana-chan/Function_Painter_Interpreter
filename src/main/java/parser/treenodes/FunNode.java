package parser.treenodes;

import lexer.TokenTypeEnum;

public class FunNode extends ASTNode{
    public ASTNode child;
    public String funName;

    public FunNode(TokenTypeEnum tokenType, ASTNode child, String funName) {
        super(tokenType);
        this.child = child;
        this.funName = funName;
    }
}
