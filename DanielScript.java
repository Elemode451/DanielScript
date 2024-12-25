import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

// Class representing the overall interpreter
public class DanielScript {
    private static List<Error> errors = new ArrayList<>();
    // Behavior: Handles actual interpretation of a DanielScript program
    // Exceptions: None (all are caught)  -- Caught ones are FileNotFound exception
    public static void main(String[] args){ 
        args = new String[]{"test.ds"};
        if(args == null || args.length > 1 || args.length < 1) {
            System.out.println("Intended use: ds [file]"); 
            System.exit(-1);
        } else {
            List<Token> tokens = readFromFile(args[0].trim());
            // tokens.forEach(System.out::println);
            parseTokens(tokens);
        }

        System.out.println("Successfully executed DanielScript program " + args[0]);
        System.exit(0);
    }

    private static void parseTokens(List<Token> tokens) {
        try {
            Parser parser = new Parser(tokens);
            PrettyPrinter p = new PrettyPrinter();
            var parse = parser.parse();
            System.out.println(p.print(parse));
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
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
        errors.add(new Error(line, information));
    }






}