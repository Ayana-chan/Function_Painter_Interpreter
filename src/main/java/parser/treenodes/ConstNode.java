package parser.treenodes;

import lexer.TokenTypeEnum;

public class ConstNode extends ASTNode{
    double value;

    public ConstNode(TokenTypeEnum tokenType, double value) {
        super(tokenType);
        this.value = value;
    }
}
