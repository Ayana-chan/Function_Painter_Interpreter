package parser.treenodes;

import lexer.TokenTypeEnum;

public class BinaryNode extends ASTNode{
    public ASTNode left,right;

    public BinaryNode(TokenTypeEnum tokenType, ASTNode left, ASTNode right) {
        super(tokenType);
        this.left = left;
        this.right = right;
    }
}
