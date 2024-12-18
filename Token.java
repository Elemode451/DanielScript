public class Token {
    public final TokenType TYPE;
    public final Object VALUE;

    public static enum TokenType {
        // Datatypes
        VARIABLE, INTEGER,
        
        // Operators
        MINUS, PLUS, MULTIPLY, DIVIDE, 
        
        // Keywords
        RETURN, PRINT,

        // Assignment, grouping
        ASSIGNMENT, LPAREN, RPAREN,

        // EOF token
        EOF,
        // All other things
        ERROR
    }
    
    // Behavior: Creates a new token. Value must be non-null. 
    // Exceptions: None
    public Token(String value) {
        // will take in a value, figure out what type it is, and store accordingly one created


        // switch(type) {
        //     // Store as strings
        //     case INTEGER:
        //     this.VALUE = Integer.parseInt(value);
        //         break;
        //     // Parenthesis 
        //     case RPAREN:
        //     case LPAREN:
        //     case MINUS:
        //     case MULTIPLY:
        //     case PLUS:
        //         this.VALUE = (Character) value.toCharArray()[0];
        //         break;
        //     case VARIABLE:
        //     case ASSIGNMENT:
        //     case DIVIDE:
        //     case PRINT:
        //     case RETURN:
        //         this.VALUE = value;
        //         break;
        //     case EOF:
        //         this.VALUE = "EOF";
        //         break;
        //     default:
        //         this.VALUE = "ERROR";
        //         break;
        // }
    }


    
}
