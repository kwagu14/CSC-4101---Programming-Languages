/*
        both people submit 
        DO NOT ZIP 

*/

// Aliza Siddiqui
// Karley Waguespack 

/*
    Changes made: 
        1) took out condition that checked for whitespace for letters ( varName= for() functName{} )
        2) For numbers, only thing that can follow is a white space, symbol, or the EOF, so changed condition to reflect that
        3) added a break once syntax errors are found so that the lexical analysis stops

*/


import java.util.*;
import java.io.*;
import java.nio.charset.Charset.*;

class Lexer{

    public static TreeMap<String, String> symbols = new TreeMap<String, String>();
    public static TreeMap<String, String> reservedWords = new TreeMap<String, String>();


    private static void fillReservedWordsMap(){
        reservedWords.put("if", "IF");
        reservedWords.put("for", "FOR");
        reservedWords.put("while", "WHILE");
        reservedWords.put("function", "FUNCTION");
        reservedWords.put("return", "RETURN");
        reservedWords.put("int", "INT");
        reservedWords.put("else", "ELSE");
        reservedWords.put("do", "DO");
        reservedWords.put("break", "BREAK");
        reservedWords.put("end", "END");
    }

    
    private static void fillSymbolsMap(){
        symbols.put("=", "ASSIGN");
        symbols.put("+", "ADD");
        symbols.put("-", "SUB");
        symbols.put("*", "MUL");
        symbols.put("/", "DIV");
        symbols.put("%", "MOD");
        symbols.put(">", "GT");
        symbols.put("<", "LT");
        symbols.put(">=", "GE");
        symbols.put("<=", "LE");
        symbols.put("++", "INC");
        symbols.put("(", "LP");
        symbols.put(")", "RP");
        symbols.put("{", "LB");
        symbols.put("}", "RB");
        symbols.put("|", "OR");
        symbols.put("&", "AND");
        symbols.put("==", "EE");
        symbols.put("!", "NEG");
        symbols.put(",", "COMMA");
        symbols.put(";", "SEMI");
        
    }


    private static BufferedReader createBufferedReader(String fileName) throws IOException {
            FileInputStream file = new FileInputStream(fileName);
            InputStreamReader input = new InputStreamReader(file);
            BufferedReader bufferedReader = new BufferedReader(input);        
            return bufferedReader;
    }


    public static void Tokenize(String fileName) throws IOException{
        BufferedReader bufferedReader = createBufferedReader(fileName);
        int c = 0; 
        
        c = bufferedReader.read();
        while(c != -1){
            
            //used for accumulating identifiers/integers as strings for displaying to console
            String identifier = ""; 
            String integerLiteral = "";

            char character = (char) c; 
            if(Character.isLetter(character)){

                identifier += Character.toString(c);
                c = bufferedReader.read();
                character = (char) c;

                while(Character.isLetter(character) || Character.isDigit(character)){
                    //store the character inside of the string for the current identifier
                    identifier += character; 
                    //advance to next char
                    c = bufferedReader.read();
                    character = (char) c;
                }

                //finished reading; check if its a reserved word, function, or just an identifier
                if(reservedWords.containsKey(identifier)){
                    System.out.println(reservedWords.get(identifier));
                }else if(identifier.equals("function")){
                    System.out.println("FUNCTION");
                }else{
                    System.out.println("IDENT:" + identifier); 
                }

            }
            else if(Character.isDigit(character)){
               
                integerLiteral += character; 
                c = bufferedReader.read();
                character = (char) c;

                while(Character.isDigit(character)){
                    integerLiteral += character; 
                    c = bufferedReader.read();
                    character = (char) c;
                }
                
                //we finished reading number; only things that can follow: whitespace, symbol, or EOF
                if(Character.isWhitespace(character) || c == -1 || symbols.containsKey(Character.toString(character))){
                     System.out.println("INT_LIT:" + integerLiteral);

                }else{
                    System.out.println("Syntax error: INVALID IDENTIFIER NAME");
                    break; //there's an error, so stop analysis
                }

            }
            else if(Character.isWhitespace(character)){
                //get the next character
                c = bufferedReader.read();
                character = (char) c;
                while(Character.isWhitespace(character)){
                    //keep advancing
                    c = bufferedReader.read();
                    character = (char) c;
                }
                
            }

            else {
                
                //used for accumulating characters (to check for compound operators)
                String symbolStr = "";
                symbolStr += Character.toString(c);
                if(symbols.containsKey(Character.toString(c))){
                    //get the next character
                    int nextC = bufferedReader.read();
                    char nextChar = (char) nextC;
                    //add it to the symbol string
                    symbolStr += nextChar;
                    
                    if(symbols.containsKey(symbolStr)){
                        //compound operator (e.g. == or <=)
                        System.out.println(symbols.get(symbolStr));
                        c = bufferedReader.read();
                    }else{
                        //normal operator
                        System.out.println(symbols.get(Character.toString(c)));
                        c = nextC; 
                    }
  
                }else{
                    System.out.println("Syntax error: INVALID CHARACTER");
                    break;
                }
               

            }
        }


    }


    public static void main(String[] args) throws IOException{
        fillReservedWordsMap();
        fillSymbolsMap();

        Tokenize("testFile.txt");

    }

}
