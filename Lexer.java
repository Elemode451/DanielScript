import java.util.*;

public class Lexer {
    private final String source;
    private final List<Token> tokens;
    private final Map<String, Token.TokenType> reserved;
    private int line;
    private int start = 0;
    private int offset = 0;

    public Lexer(String source) {
        this.source = source;
        this.tokens = new ArrayList<>();

        this.reserved = Map.of(
            "if", Token.TokenType.IF,
            "then", Token.TokenType.THEN,
            "else", Token.TokenType.ELSE,
            "print", Token.TokenType.PRINT,
            "return", Token.TokenType.RETURN
        );

        
        char curr;
        while(notAtEnd()) {
            start = offset;
            curr = source.charAt(offset++); 
            
            switch(curr) {
                case '(': addToken(Token.TokenType.LPAREN); break; 
                case ')': addToken(Token.TokenType.RPAREN); break; 
                case '+': addToken(Token.TokenType.PLUS); break; 
                case '*': addToken(Token.TokenType.MULTIPLY); break;
                case '^': addToken(Token.TokenType.EXPONENT); break; 
                case '-': addToken(Token.TokenType.MINUS); break;
                case '=':
                    addToken(checkAhead('=') ? Token.TokenType.EQUIVALENCE : Token.TokenType.ASSIGNMENT);
                    break;
                case '/':
                    if(checkAhead('/')) {
                        // comments can only span one line
                        while(nextChar() != '\n' && notAtEnd()) {
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
                        DanielScript.error(line, "Unexpected character " + curr);
                    }
                        
            }
        }

        addToken(Token.TokenType.EOF);
    }

    private boolean notAtEnd() {
        return offset < source.length();
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
