package parser.treenodes;

import lexer.TokenTypeEnum;

public abstract class ASTNode {
    public TokenTypeEnum tokenType;

    public ASTNode(TokenTypeEnum tokenType) {
        this.tokenType = tokenType;
    }
}
