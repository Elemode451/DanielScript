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
        if(args.length > 1) {
            System.out.println("Intended use: ds [file]"); 
            System.exit(-1);
        } else if(args.length == 0) {
            // will add support for in-terminal interpretation
        } else {
            readFromFile(args[0].trim());
        }

        System.out.println("Successfully executed DanielScript program " + args[0]);
        System.exit(0);
    }
    
    // Reads the tokens from a file. fileName must be non-null.
    private static void readFromFile(String fileName) {
        String currentDirectory = System.getProperty("user.dir") + "\\" + fileName;
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
            for(Token token : lexer.getTokens()) {
                System.out.println(token);
            }
            
        } catch (Exception e) {
            System.out.println("File " + fileName + " not found!");
            System.exit(-1);
        }
    }








}