package parser.treenodes;

import lexer.TokenTypeEnum;

public class ConstNode extends ASTNode{
    public double value;

    public ConstNode(TokenTypeEnum tokenType, double value) {
        super(tokenType);
        this.value = value;
    }
}
