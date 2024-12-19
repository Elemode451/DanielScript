import java.io.*;
import java.util.*;

public class Lexer {
    private final String source;
    private final List<Token> tokens;
    private int line = 0;
    private int start = 0;
    private int offset = 0;

    public Lexer(String source) {
        this.source = source;
        this.tokens = new ArrayList<>();

        char curr;
        while(offset <= source.length()) {
            curr = source.charAt(offset); 
            offset++;
            
            switch(curr) {
                case '(': addToken(Token.TokenType.LPAREN); break; 
                case ')': addToken(Token.TokenType.RPAREN); break; 
                case '-': addToken(Token.TokenType.MINUS); break; 
                case '+': addToken(Token.TokenType.PLUS); break; 
                case '*': addToken(Token.TokenType.MULTIPLY); break;
                case '^': addToken(Token.TokenType.EXPONENT); break; 
            }
        }
        
    }

    private void addToken(Token.TokenType type) {
        addToken(type, null);
    }
  
    private void addToken(Token.TokenType type, String literal) {
        String value = source.substring(start, offset);
        tokens.add(new Token(type, value, literal));
    }

    public List<Token> getTokens() {
        return tokens;
    }

    // INTEGER(s -> Integer.valueOf(s)),
    // DECIMAL(s -> Double.valueOf(s)),
    // VARIABLE, 
    
    // // Operators
    // MINUS, 
    // PLUS, 
    // MULTIPLY, 
    // DIVIDE, 
    // EQUIVALENCE,
    
    // // Keywords (Only adding return for now)
    // RETURN, PRINT, IF, THEN, ELSE,

    // // Assignment, grouping
    // ASSIGNMENT, 
    // LPAREN, 
    // RPAREN, 

    // // EOF, Error
    // EOF,
    // ERROR;

    
}
