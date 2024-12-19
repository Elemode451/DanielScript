import java.util.function.Function;

public class Token {
    public final TokenType TYPE;
    public final Object VALUE;
    public final String lexeme;

    public static enum TokenType {
        // Datatypes
        INTEGER(s -> Integer.valueOf(s)),
        DECIMAL(s -> Double.valueOf(s)),
        VARIABLE, 
        
        // Operators
        MINUS, 
        PLUS, 
        MULTIPLY, 
        DIVIDE, 
        EQUIVALENCE,
        
        // Keywords (Only adding return for now)
        RETURN, PRINT, IF, THEN, ELSE,

        // Assignment, grouping
        ASSIGNMENT, 
        LPAREN, 
        RPAREN, 

        // EOF, Error
        EOF,
        ERROR;
        
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
    public Token(TokenType type, String value) {
        // will take in a value, figure out what type it is, and store accordingly one created
        this.TYPE = type;
        this.lexeme = value;
        this.VALUE = type.applyCast(value);
    }


    
}
