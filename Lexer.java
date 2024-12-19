import java.io.*;
import java.util.*;

public class Lexer {
    private final String sourceCode;

    public Lexer(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public Token.TokenType matchToken(String curr) {
        return null;
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
