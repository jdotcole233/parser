import java.io.File;
import java.io.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.*;
import java.util.Timer;
import java.util.regex.*;


class ProgScanner {

    enum Tokens {
        PLUSOP, SUBOP, MULOP, DIVOP, EQOP, LESSOP, LFTPARA, RGTPARA, SEMI, ASSGN, IF, ELSE, THEN, END, REPEAT, UNTIL,
        READ, WRITE, NUMBER, IDENTIFIER
    }

    private String filename;
    private FileWriter fileWriter;
    String error;
    FileReader fileReader;
    ProgScanner.Tokens tokens;
    StringBuilder stringBuilder = new StringBuilder();



    public ProgScanner(String  filename) throws  Exception{
         this.filename = filename;
         fileWriter = new FileWriter("errortxt.txt");

    }

    public ProgScanner.Tokens scan_program_file() throws Exception  {
        ProgScanner.Tokens tokenret = null;
       
        File documentname  = new File(getFilename());

        if (!documentname.exists()){
            System.out.println("File not found");
        }

        Scanner scanner = new Scanner(documentname);
        ArrayList<Object> objs = new ArrayList<>();
        Integer ar = null;
        while (scanner.hasNext()){
            objs.add(scanner.next());
        }
        System.out.println(objs.size());

        for (Object string : objs){
            
            Thread.sleep(200);

            if (string instanceof String ) {
                if (string.equals("if")) {
                    tokenret = ProgScanner.Tokens.IF;
                } 
                else if (string.equals("then")) {
                    tokenret = ProgScanner.Tokens.THEN;
                } 
                else if (string.equals("else")) {
                    tokenret = ProgScanner.Tokens.ELSE;
                } 
                else if (string.equals("end")) {
                    tokenret = ProgScanner.Tokens.END;
                } 
                else if (string.equals("repeat")) {
                    tokenret = ProgScanner.Tokens.REPEAT;
                } 
                else if (string.equals("until")) {
                    tokenret = ProgScanner.Tokens.UNTIL;
                } 
                else if (string.equals("write")){
                    tokenret = ProgScanner.Tokens.WRITE;
                }
                else if (string.equals("+")) {
                    tokenret = ProgScanner.Tokens.PLUSOP;
                } 
                else if (string.equals("-")) {
                    tokenret = ProgScanner.Tokens.SUBOP;
                } 
                else if (string.equals("*")) {
                    tokenret = ProgScanner.Tokens.MULOP;
                } 
                else if (string.equals("/")) {
                    tokenret = ProgScanner.Tokens.DIVOP;
                } 
                else if (string.equals("=")) {
                    tokenret = ProgScanner.Tokens.EQOP;
                } 
                else if (string.equals("<")) {
                    tokenret = ProgScanner.Tokens.LESSOP;
                } 
                else if (string.equals("(")) {
                    tokenret = ProgScanner.Tokens.LFTPARA;
                } 
                else if (string.equals(")")) {
                    tokenret = ProgScanner.Tokens.RGTPARA;
                } 
                else if (string.equals(";")) {
                    tokenret = ProgScanner.Tokens.SEMI;
                } 
                else if (string.equals(":=")) {
                    tokenret = ProgScanner.Tokens.ASSGN;
                } 
                else  if (!string.equals("if") || !string.equals("then") || !string.equals("else") || !string.equals("end") 
                || !string.equals("repeat") || !string.equals("until") || !string.equals("write") || !string.equals("read") ){

                    Pattern p = Pattern.compile("\\[0-9]+");
                    Matcher m = p.matcher(string.toString());

                    if (m.find()) {
                        System.out.println(m.group());
                    } else {
                        System.out.println(string);
                    }

                }
            } 
        }


        return tokenret;
    }





    private void validateToken(char token) {

    }

    public String getFilename() {
        return filename;
    }


    


    public void readFile(String filename) {
            FileReader filereader = null;
            try{

                 filereader= new FileReader(filename);
                int character_point;
                while ((character_point = filereader.read()) != -1) {
                    System.out.println((char) character_point);
                }
                filereader.close();

         } catch (FileNotFoundException ex){
            System.out.println(ex.getMessage());
         } catch(IOException e){
            System.out.println(e.getMessage());
         }
         
    }
}