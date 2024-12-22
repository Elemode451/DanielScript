import java.util.List;

public class Parser {
    private List<Token> tokens;
    private int current;

    // Tokens must be non-null
    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.current = 0;
    }

    public ASTNode expression() {
        return equality();
    }

    public ASTNode equality() {
        return arithmetic();
    }

    public ASTNode arithmetic() {
        return term();
    }

    public ASTNode term() {
        
    }
}
