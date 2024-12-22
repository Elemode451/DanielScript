import java.util.ArrayList;
import java.util.List;

public class Parser {
    private final List<Token> TOKENS;
    private int current;
    private final List<DanielScript.Error> ERRORS;
    // Tokens must be non-null
    public Parser(List<Token> tokens) {
        this.TOKENS = tokens;
        this.current = 0;
        this.ERRORS = new ArrayList<>();
    }

    public ASTNode parse() {
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
        Token curr = peek();
        for(Token.TokenType type : types) {
            if(curr.TYPE.equals(type)) {
                current++;
                return true;
            }
        }

        return false;
    }

    private Token peek() {
        if(isAtEnd()) { 
            return TOKENS.get(current - 1);
        }

        return TOKENS.get(current);
    }

    private Token currToken() {
        if(isAtEnd()) { 
            return TOKENS.get(current - 1);
        }

        return TOKENS.get(current++);
    }

    private boolean isAtEnd() {
        return TOKENS.get(current).TYPE.equals(Token.TokenType.EOF);
    }

    private boolean check(Token.TokenType type) {
        if (isAtEnd()) {
            return false;
        }
        return peek().TYPE.equals(type);
    }

    public ASTNode expression() {
        return equality();
    }

    public ASTNode equality() {
        ASTNode root = arithmetic();
        ASTNode curr;
        Token operator;
        while(match(Token.TokenType.EQUIVALENCE)) {
            curr = arithmetic();
            operator = currToken();
            root = new ASTNode.BinaryNode(operator, root, curr);
        }

        return root;
    }

    public ASTNode arithmetic() {
        ASTNode root = term();
        ASTNode curr;
        Token operator;
        while(match(List.of(Token.TokenType.PLUS, Token.TokenType.MINUS))) {
            curr = term();
            operator = currToken();
            root = new ASTNode.BinaryNode(operator, root, curr);
        }

        return root;
    }

    public ASTNode term() {
        ASTNode root = factor();
        ASTNode curr;
        Token operator;
        while(match(List.of(Token.TokenType.MULTIPLY, Token.TokenType.DIVIDE))) {
            curr = factor();
            operator = currToken();
            root = new ASTNode.BinaryNode(operator, root, curr);
        }

        return root;    
    }

    public ASTNode factor() {
        ASTNode root = unary();
        ASTNode curr;
        Token operator;
        while(match(Token.TokenType.EXPONENT)) {
            curr = factor();
            operator = currToken();
            root = new ASTNode.BinaryNode(operator, root, curr);
        }
        return root;
    }

    public ASTNode unary() {
        if(match(Token.TokenType.MINUS)) {
            Token operator = currToken();
            ASTNode curr = unary();
            return new ASTNode.UnaryNode(operator, curr);
        }

        return primary();
    }

    public ASTNode primary() {
        if(match(Token.TokenType.NUMBER)) {
            Token curr = currToken();
            return new ASTNode.LiteralNode(curr.VALUE);
        }
        
        if(match(Token.TokenType.LPAREN)) {
            ASTNode curr = expression();
            System.out.println("Parenthesize");
            if(isMissing(Token.TokenType.RPAREN)) {
                StringBuilder info = new StringBuilder("Missing \")\"") ;

                if(check(Token.TokenType.EOF)) {
                    info.append(" at end!");
                } else {
                    info.append("at line ");
                    info.append(peek().LINE);
                }

                ERRORS.add(new DanielScript.Error(current, info.toString()));
                throw new RuntimeException(info.toString());
            }
            return curr;
        }

        ERRORS.add(new DanielScript.Error(current, "Expected information"));
        throw new RuntimeException("Expected information");

    }

    public boolean isMissing(Token.TokenType type) {
        return check(type);
    }
}
