import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

// Class representing the overall interpreter
public class DanielScript {
    
    // Behavior: Handles actual interpretation of a DanielScript program
    // Exceptions: None (all are caught)  -- Caught ones are FileNotFound exception
    public static void main(String[] args){ 
        if(args == null || args.length > 1 || args.length < 1) {
            System.out.println("Intended use: ds [file]"); 
            System.exit(-1);
        } else {
            readFromFile(args[0].trim());
        }

        System.out.println("Successfully executed DanielScript program " + args[0]);
        System.exit(0);
    }
    
    // Reads the tokens from a file. fileName must be non-null.
    private static void readFromFile(String fileName) {
        String currentDirectory = System.getProperty("user.dir") + "\\" + fileName;
        System.out.println(currentDirectory);
        try {
            byte[] source = Files.readAllBytes(Paths.get(currentDirectory));
            // UTF-8
            String code = new String(source, Charset.defaultCharset());
            if(code.isBlank() || code.isEmpty()) {
                System.out.println("No code to process!");
                System.exit(-1);
            }
                
            // Read in the file
            Lexer lexer = new Lexer(code);
            if(lexer.hadError()) {
                System.out.println(lexer.getErrors());
                System.exit(-1);
            }

            for(Token token : lexer.getTokens()) {
                System.out.println(token);
            }
            
        } catch (Exception e) {
            e.printStackTrace(); 
            System.exit(-1);
        }
    }

    // A class representing an error in the interpretation.
    public static class Error {
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






}