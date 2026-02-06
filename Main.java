import java.io.*;
import java.util.*;

/**
 * Simple Encryption/Decryption Program
 * Main entry point for the application
 */
public class Main {
    
    /**
     * Encrypts text using Caesar cipher (substitution method)
     * Only encrypts alphabetic letters (A-Z, a-z)
     * Numbers, spaces, punctuation remain unchanged
     * Key range: 0-255 (8-bit key)
     */
    public static String caesarEncrypt(String text, int key) {
        StringBuilder result = new StringBuilder();
        
        // Process each character in the text
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            
            if (Character.isUpperCase(ch)) {
                // Encrypt uppercase letters (A-Z)
                int position = ch - 'A';  // Convert to 0-25
                int newPosition = (position + key) % 26;
                char encrypted = (char) ('A' + newPosition);
                result.append(encrypted);
            } 
            else if (Character.isLowerCase(ch)) {
                // Encrypt lowercase letters (a-z)
                int position = ch - 'a';  // Convert to 0-25
                int newPosition = (position + key) % 26;
                char encrypted = (char) ('a' + newPosition);
                result.append(encrypted);
            }
            else {
                // Keep numbers, spaces, punctuation unchanged
                result.append(ch);
            }
        }
        
        return result.toString();
    }
    
    /**
     * Decrypts text encrypted with Caesar cipher
     * Only decrypts alphabetic letters (A-Z, a-z)
     * Numbers, spaces, punctuation remain unchanged
     */
    public static String caesarDecrypt(String text, int key) {
        StringBuilder result = new StringBuilder();
        
        // Process each character
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            
            if (Character.isUpperCase(ch)) {
                // Decrypt uppercase letters (A-Z)
                int position = ch - 'A';
                int newPosition = (position - key + 26) % 26;
                char decrypted = (char) ('A' + newPosition);
                result.append(decrypted);
            } 
            else if (Character.isLowerCase(ch)) {
                // Decrypt lowercase letters (a-z)
                int position = ch - 'a';
                int newPosition = (position - key + 26) % 26;
                char decrypted = (char) ('a' + newPosition);
                result.append(decrypted);
            }
            else {
                // Keep numbers, spaces, punctuation unchanged
                result.append(ch);
            }
        }
        
        return result.toString();
    }
    
    /**
     * Encrypts text using Columnar Transposition
     * The key represents the number of columns
     */
    public static String columnarEncrypt(String text, int key) {
        // Handle edge cases
        if (key <= 1 || text.length() <= 1) {
            return text;
        }
        
        // Calculate number of rows needed
        int numRows = (int) Math.ceil((double) text.length() / key);
        
        // Create 2D array (grid) to hold the text
        char[][] grid = new char[numRows][key];
        
        // Fill grid with spaces initially
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < key; j++) {
                grid[i][j] = ' ';
            }
        }
        
        // Fill the grid row by row with text
        int index = 0;
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < key; col++) {
                if (index < text.length()) {
                    grid[row][col] = text.charAt(index);
                    index++;
                }
            }
        }
        
        // Read the grid column by column to create encrypted text
        StringBuilder result = new StringBuilder();
        for (int col = 0; col < key; col++) {
            for (int row = 0; row < numRows; row++) {
                result.append(grid[row][col]);
            }
        }
        
        return result.toString();
    }
    
    /**
     * Decrypts text encrypted with Columnar Transposition
     */
    public static String columnarDecrypt(String text, int key) {
        // Handle edge cases
        if (key <= 1 || text.length() <= 1) {
            return text;
        }
        
        // Calculate number of rows
        int numRows = (int) Math.ceil((double) text.length() / key);
        
        // Create 2D array (grid)
        char[][] grid = new char[numRows][key];
        
        // Fill the grid column by column from encrypted text
        int index = 0;
        for (int col = 0; col < key; col++) {
            for (int row = 0; row < numRows; row++) {
                if (index < text.length()) {
                    grid[row][col] = text.charAt(index);
                    index++;
                }
            }
        }
        
        // Read the grid row by row to get original text
        StringBuilder result = new StringBuilder();
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < key; col++) {
                result.append(grid[row][col]);
            }
        }
        
        return result.toString();
    }
    
    /**
     * Reads content from a file
     */
    public static String readFile(String filename) {
        try {
            FileInputStream fis = new FileInputStream(filename);
            InputStreamReader reader = new InputStreamReader(fis, "ISO-8859-1");
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuilder content = new StringBuilder();
            String line;
            boolean first = true;
            
            while ((line = bufferedReader.readLine()) != null) {
                if (!first) {
                    content.append("\n");
                }
                content.append(line);
                first = false;
            }
            
            bufferedReader.close();
            return content.toString();
            
        } catch (FileNotFoundException e) {
            System.out.println("Error: File '" + filename + "' not found.");
            return null;
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Writes content to a file
     */
    public static boolean writeFile(String filename, String content) {
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            OutputStreamWriter writer = new OutputStreamWriter(fos, "ISO-8859-1");
            writer.write(content);
            writer.close();
            return true;
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Main method - program starts here
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== Encryption/Decryption Program ===\n");
        
        // Get operation choice
        String operation = "";
        while (true) {
            System.out.print("Do you want to encrypt (E) or decrypt (D)? ");
            operation = scanner.nextLine().trim().toUpperCase();
            if (operation.equals("E") || operation.equals("D")) {
                break;
            }
            System.out.println("Invalid choice. Please enter 'E' or 'D'.");
        }
        
        // Get method choice
        String method = "";
        while (true) {
            System.out.print("Do you want to use substitution (S) or transposition (T)? ");
            method = scanner.nextLine().trim().toUpperCase();
            if (method.equals("S") || method.equals("T")) {
                break;
            }
            System.out.println("Invalid choice. Please enter 'S' or 'T'.");
        }
        
        // Get secret key
        int key = 0;
        while (true) {
            try {
                System.out.print("Input the secret key: ");
                String keyInput = scanner.nextLine().trim();
                key = Integer.parseInt(keyInput);
                
                // Validate key based on method
                if (method.equals("S")) {
                    if (key >= 0 && key <= 255) {
                        break;
                    }
                    System.out.println("Key must be between 0 and 255 for substitution method.");
                } else {
                    if (key >= 2) {
                        break;
                    }
                    System.out.println("Key must be at least 2 for transposition method.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid key. Please enter a number.");
            }
        }
        
        // Get input filename
        System.out.print("Input the name of the file you want to process: ");
        String filename = scanner.nextLine().trim();
        
        // Read file content
        String content = readFile(filename);
        if (content == null) {
            scanner.close();
            return;
        }
        
        // Process the file
        String result = "";
        String outputFilename = "";
        
        if (operation.equals("E")) {
            // Encryption
            if (method.equals("S")) {
                result = caesarEncrypt(content, key);
            } else {
                result = columnarEncrypt(content, key);
            }
            
            // Create output filename
            int dotIndex = filename.lastIndexOf('.');
            if (dotIndex > 0) {
                outputFilename = filename.substring(0, dotIndex) + "_enc.txt";
            } else {
                outputFilename = filename + "_enc.txt";
            }
            
        } else {
            // Decryption
            if (method.equals("S")) {
                result = caesarDecrypt(content, key);
            } else {
                result = columnarDecrypt(content, key);
            }
            
            // Create output filename
            int dotIndex = filename.lastIndexOf('.');
            if (dotIndex > 0) {
                outputFilename = filename.substring(0, dotIndex) + "_dec.txt";
            } else {
                outputFilename = filename + "_dec.txt";
            }
        }
        
        // Write result to file
        if (writeFile(outputFilename, result)) {
            if (operation.equals("E")) {
                System.out.println("\nThe file has been encrypted and the results have been saved in the file " + outputFilename + ".");
            } else {
                System.out.println("\nThe file has been decrypted and the results have been saved in the file " + outputFilename + ".");
            }
        } else {
            System.out.println("\nFailed to save the output file.");
        }
        
        scanner.close();
    }
}