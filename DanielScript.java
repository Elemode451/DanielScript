import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Class representing the overall interpreter
public class DanielScript {
    private static final List<Error> ERRORS = new ArrayList<>();
    private static final Evaluator INTERP = new Evaluator();

    // Behavior: Handles actual interpretation of a DanielScript program
    // Exceptions: None (all are caught)  -- Caught ones are FileNotFound exception
    public static void main(String[] args){ 
        args = new String[]{"test.ds"};
        if(args == null || args.length > 1) {
            System.out.println("Intended use: ds [file]"); 
            System.exit(-1);
        } else if(args.length == 0) {
            // will add REPL later
        } else {
            List<Token> tokens = readFromFile(args[0].trim());
            ExprNode parse = parseTokens(tokens);
            INTERP.run(parse);
        }

        System.out.println("Successfully executed DanielScript program " + args[0]);
        System.exit(0);
    }

    private static ExprNode parseTokens(List<Token> tokens) {
        try {
            Parser parser = new Parser(tokens);
            var parse = parser.parse();
            if(hadError()) {
                System.out.println("ERRORS");
                ERRORS.forEach(System.out::println);
                System.exit(-1);
            }

            return parse;
    
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }

        return null;
    }
    
    // Reads the tokens from a file. fileName must be non-null.
    private static List<Token> readFromFile(String fileName) {
        String currentDirectory = System.getProperty("user.dir") + "\\" + fileName;
        String code = null;
        try {
            byte[] source = Files.readAllBytes(Paths.get(currentDirectory));
            // UTF-8
            code = new String(source, Charset.defaultCharset());
        } catch (Exception e) {
            e.printStackTrace(); 
            System.exit(-1);
        }

        if(code == null || code.isBlank() || code.isEmpty()) {
            System.out.println("No code to process!");
            System.exit(-1);
        }
            
        // Read in the file
        Lexer lexer = new Lexer(code);
        return lexer.getTokens();
    }

    // A class representing an error in the interpretation.
    private static class Error {
        public int line;
        public String information;

        // line of error, information about error (must be non-null)
        public Error(int line, String information) {
            this.line = line;
            this.information = information;
        }

        public String toString() {
            StringBuilder string = new StringBuilder();
            string.append(information);
            string.append(" on line ");
            string.append(line);
            return string.toString();
        }   
    }

    public static void error(int line, String information) {
        ERRORS.add(new Error(line, information));
    }

    private static boolean hadError() {
        return ERRORS.size() != 0;
    }






}