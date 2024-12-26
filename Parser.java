import java.util.List;

public class Parser {
    private final List<Token> TOKENS;
    private int current;

    // Tokens must be non-null
    public Parser(List<Token> tokens) {
        this.TOKENS = tokens;
        this.current = 0;
    }

    public ExprNode parse() {
        try {
            return expression();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean match(Token.TokenType type) {
        return match(List.of(type));
    }

    private boolean match(List<Token.TokenType> types) {
        for(var type : types) {
            if(check(type)) {
                currToken();
                return true;
            }
        }

        return false;
    }

    private Token peek() {
        return TOKENS.get(current);
    }

    private Token currToken() {
        if(!isAtEnd()) {
            current++;
        }

        return prevToken();
    }

    private Token prevToken() {
        return TOKENS.get(current - 1);
    }

    private boolean isAtEnd() {
        return peek().TYPE.equals(Token.TokenType.EOF);
    }

    private boolean check(Token.TokenType type) {
        if (isAtEnd()) {
            return false;
        }
        return peek().TYPE.equals(type);
    }

    public ExprNode expression() {
        return equality();
    }

    public ExprNode equality() {
        ExprNode root = arithmetic();
        ExprNode curr;
        Token operator;
        while (match(Token.TokenType.EQUIVALENCE)) {
            operator = prevToken();
            curr = arithmetic();
            root = new ExprNode.BinaryNode(operator, root, curr);
        }

        return root;
    }

    public ExprNode arithmetic() {
        ExprNode root = term();
        ExprNode curr;
        Token operator;
        while (match(List.of(Token.TokenType.PLUS, Token.TokenType.MINUS))) {
            operator = prevToken();
            curr = term();
            root = new ExprNode.BinaryNode(operator, root, curr);
        }

        return root;
    }

    public ExprNode term() {
        ExprNode root = factor();
        ExprNode curr;
        Token operator;
        while (match(List.of(Token.TokenType.MULTIPLY, Token.TokenType.DIVIDE))) {
            operator = prevToken();
            curr = factor();
            root = new ExprNode.BinaryNode(operator, root, curr);
        }

        return root;
    }

    public ExprNode factor() {
        ExprNode root = unary();
        ExprNode curr;
        Token operator;
        while (match(Token.TokenType.EXPONENT)) {
            operator = prevToken();
            curr = factor();
            root = new ExprNode.BinaryNode(operator, root, curr);
        }
        return root;
    }

    public ExprNode unary() {
        if (match(Token.TokenType.MINUS)) {
            ExprNode curr = unary();
            Token operator = prevToken();
            return new ExprNode.UnaryNode(operator, curr);
        }

        return primary();
    }

    public ExprNode primary() {
        if (match(Token.TokenType.NUMBER)) {
            Token curr = prevToken();
            return new ExprNode.LiteralNode(curr.VALUE);
        }

        if (match(Token.TokenType.LPAREN)) {
            ExprNode curr = expression();
            if(!match(Token.TokenType.RPAREN)) {
                DanielScript.error(peek().LINE, "Missing r-parenthesis");
                synchronize();
            }
            return new ExprNode.GroupingNode(curr);
        }

        DanielScript.error(current, "Expected information");
        return null;
    }

    public boolean isMissing(Token.TokenType type) {
        return !check(type);
    }

    private void synchronize() {
        currToken();
    
        while (!isAtEnd()) {    
          switch (peek().TYPE) {
            case IF:
            case PRINT:
            case RETURN:
              return;
            default:
                break;
          }
    
          currToken();
        }
      }
}
