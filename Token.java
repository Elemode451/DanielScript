import java.util.function.Function;

public class Token {
    public final TokenType TYPE;
    public final Object VALUE;
    public final int LINE;

    public static enum TokenType {
        // Datatypes
        NUMBER(s -> Double.valueOf(s)),
        VARIABLE, 
        
        // Operators
        MINUS, 
        PLUS, 
        MULTIPLY, 
        EXPONENT,
        DIVIDE, 
        EQUIVALENCE,
        
        // Keywords
        RETURN, PRINT, IF, THEN, ELSE,

        // Assignment, grouping
        ASSIGNMENT, 
        LPAREN, 
        RPAREN, 

        // EOF
        SEMICOLON,
        EOF;
        
        //For datatypes with custom logic, will want to cast them when creating token
        private Function<String, Object> caster;

        //Caster will be non-null, since it's directly coming from the enum
        private TokenType(Function<String, Object> caster) {
            this.caster = caster;
        }
        
        // Tokens without custom logic
        private TokenType() {
            this.caster = null;
        }

        public Object applyCast(String value) {
            if(caster != null) {
                return this.caster.apply(value);
            }

            return value;
        }

    }
    
    // Behavior: Creates a new token. Value must be non-null. 
    // Exceptions: None
    public Token(TokenType type, String value, int line) {
        // will take in a value, figure out what type it is, and store accordingly once created
        this(type, value, null, line);
    }
    // Behavior: Creates a new token with a literal. Value must be non-null. 
    // Exceptions: None
    public Token(TokenType type, String value, Object literal, int line) {
        // will take in a value, figure out what type it is, and store accordingly once created
        this.TYPE = type;
        this.VALUE = type.applyCast(value);
        this.LINE = line;
    }

    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("Type: ");
        string.append(this.TYPE);

        string.append(" Value: ");
        string.append(this.VALUE);
        string.append(" Line number: ");
        string.append(this.LINE);
        return string.toString();
    }


    
}
