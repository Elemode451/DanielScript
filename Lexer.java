import java.io.*;
import java.util.*;

public class Lexer {
    private final String source;
    private final List<Token> tokens;
    private final Map<String, Token.TokenType> reserved;
    private final List<DanielScript.Error> errors;
    private int line;
    private int start = 0;
    private int offset = 0;

    public Lexer(String source) {
        this.source = source;
        this.tokens = new ArrayList<>();
        this.errors = new ArrayList<>();
        this.reserved = new HashMap<>();

        reserved.put("if", Token.TokenType.IF);
        reserved.put("then", Token.TokenType.THEN);
        reserved.put("else", Token.TokenType.ELSE);
        reserved.put("print", Token.TokenType.PRINT);
        reserved.put("return", Token.TokenType.RETURN);
        
        char curr;
        while(offset < source.length()) {
            start = offset;
            curr = source.charAt(offset++); 
            
            switch(curr) {
                case '(': addToken(Token.TokenType.LPAREN); break; 
                case ')': addToken(Token.TokenType.RPAREN); break; 
                case '+': addToken(Token.TokenType.PLUS); break; 
                case '*': addToken(Token.TokenType.MULTIPLY); break;
                case '^': addToken(Token.TokenType.EXPONENT); break; 
                case '-':
                  if(Character.isDigit(nextChar())) {
                    double value = valueOfNumber();
                    addToken(Token.TokenType.NUMBER, value);
                  } else {
                    addToken(Token.TokenType.MINUS);
                  }

                  break; 
                case '=':
                    addToken(checkAhead('=') ? Token.TokenType.EQUIVALENCE : Token.TokenType.ASSIGNMENT);
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
                case '\t':
                case '\r':
                case ' ':
                    break;
                default:
                    if(Character.isDigit(curr)) {
                        double value = valueOfNumber();
                        addToken(Token.TokenType.NUMBER, value);
                    } else if(Character.isLetter(curr) || curr == '_') {
                        String endName = findName();
                        if(reserved.containsKey(endName)) {
                            addToken(reserved.get(endName), endName);
                        } else {
                            addToken(Token.TokenType.VARIABLE, endName);
                        }
                    }  else {
                        errors.add(new DanielScript.Error(line, "Unexpected character " + curr));
                    }
                        
            }
        }
    }

    private String findName() {
        while(Character.isLetter(nextChar()) || nextChar() == '_' && offset < source.length()) offset++;
        return source.substring(start, offset);
    }
    
    private double valueOfNumber() {
        while(Character.isDigit(nextChar()) && offset < source.length()) offset++;

        if(nextChar() == '.' && Character.isDigit(nextAfterChar())) {
            offset++;
        }

        while(Character.isDigit(nextChar()) && offset < source.length()) offset++;
        double sub = Double.parseDouble(source.substring(start, offset));
        return sub;
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

    public boolean hadError() {
        return errors.size() != 0;
    }
    
    public List<DanielScript.Error> getErrors() {
        return this.errors;
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

    
}
