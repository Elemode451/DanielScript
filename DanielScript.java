import java.io.*;
import java.util.*;

// Class representing the overall interpreter
public class DanielScript {
    
    // main method, handle io 
    public static void main(String[] args){ 
        if(args.length < 1) {
            System.out.println("Need a file!"); 
            System.exit(-1);
        }
        String currentDirectory = System.getProperty("user.dir") + "\\" + args[0].trim();
        System.out.println(currentDirectory);
        List<Token> tokens;
        try {
            // Read in the file
            tokens = read(new Scanner(new File(currentDirectory)));
            for(Token token : tokens) {
                System.out.println(token);
            }
        } catch (Exception e) {
            System.out.println("File not found!");
            System.exit(-1);
        }


        System.out.println("Successfully executed DanielScript program " + args[0]);
        System.exit(0);



    }

    // Reads in the file provided in main. 
    private static List<Token> read(Scanner fileScan) {

        // List of tokens that will be populated and turned into AST later
        List<Token> tokens = new ArrayList<>();
       
        String curr;
        while(fileScan.hasNext()) {
            curr = fileScan.next();
            try {
                tokens.add(new Token(curr));
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        fileScan.close();

        return tokens;
    }







}