import java.io.*;
import java.util.*;

public class Lexer {
    private final String source;
    private final List<Token> tokens;
    
    private int line;
    private int start = 0;
    private int offset = 0;

    public Lexer(String source) {
        this.source = source;
        this.tokens = new ArrayList<>();

        char curr;
        while(offset < source.length()) {
            start = offset;
            curr = source.charAt(offset++); 
            
            switch(curr) {
                case '(': addToken(Token.TokenType.LPAREN); break; 
                case ')': addToken(Token.TokenType.RPAREN); break; 
                case '-': addToken(Token.TokenType.MINUS); break; 
                case '+': addToken(Token.TokenType.PLUS); break; 
                case '*': addToken(Token.TokenType.MULTIPLY); break;
                case '^': addToken(Token.TokenType.EXPONENT); break; 
                case '=':
                    if(checkAhead('=')) {
                        addToken(Token.TokenType.EQUIVALENCE);
                    } else {
                        addToken(Token.TokenType.ASSIGNMENT);
                    }
                    break;
                case '/':
                    if(checkAhead('/')) {
                        // comments can only span one line
                        while(nextChar() != '\n' && offset < source.length()) {
                            curr = source.charAt(offset++); 
                        }
                    } else {
                        addToken(Token.TokenType.DIVIDE);
                    }
                case '\n':
                    line++;
                    break;
                default:
                    if(isValidDigit(curr)) {
                        double value = valueOfNumber();
                        addToken(Token.TokenType.NUMBER, value);
                    }

            }
        }
        
    }
    
    private double valueOfNumber() {
        while(isValidDigit(nextChar()) && offset <= source.length()) offset++;

        if(nextChar() == '.' && isValidDigit(nextAfterChar())) {
            offset++;
        }

        while(isValidDigit(nextChar()) && offset <= source.length()) offset++;

        return Double.parseDouble(source.substring(start, offset));
    }

    private boolean isValidDigit(char c) {
        return !(c > '9' || c < '0');
    }


    private boolean checkAhead(char check) {
        if(offset > source.length()) {
            return false;
        }

        boolean isNextExpected = source.charAt(offset) == check;
        if(isNextExpected) {
            // only consume if correct
            offset++;
        }

        return isNextExpected;
    }

    private char nextChar() {
        if(offset < source.length()) {
            return source.charAt(offset);
        }

        // EOF character
        return '\0';
    }

    private char nextAfterChar() {
        if(offset < source.length()) {
            return source.charAt(offset + 1);
        }

        // EOF character
        return '\0';
    }

    private void addToken(Token.TokenType type) {
        addToken(type, null);
    }
  
    private void addToken(Token.TokenType type, Object literal) {
        String value = source.substring(start, offset);
        tokens.add(new Token(type, value, literal, line));
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

    // EOF,
    // ERROR;

    
}
