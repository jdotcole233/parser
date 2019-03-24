import java.io.File;
import java.io.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.*;
import java.util.Timer;
import java.util.regex.*;


class ProgScanner {

    private String filename;
    private FileWriter fileWriter;
    String error;
    FileReader fileReader;
    Token.Tokens tokens;
    StringBuilder stringBuilder = new StringBuilder();
    ArrayList<Object> objs;
    Integer sizeofprogram = null;
    Integer position = 0;


    public ProgScanner(String  filename) throws  Exception{
         this.filename = filename;
         objs = new ArrayList<>();
         loadprogram();
         sizeofprogram = objs.size();
         fileWriter = new FileWriter("errortxt.txt");

    }


    public void loadprogram() throws Exception{

            File documentname = new File(getFilename());        //get file name 
            if (!documentname.exists()) {                       // check if file exists
                System.out.println("File not found");
            }
            Scanner scanner = new Scanner(documentname);
            while (scanner.hasNext()) {
                String a = scanner.next();
                try{
                    Integer ar = Integer.parseInt(a);           //parse data to integer
                    if (ar instanceof Integer) {                //check if object is of type int then add to List
                        objs.add(ar);
                    Thread.sleep(200);
                   } 

                }catch(NumberFormatException ex){
                    objs.add(a);                                // add non integer numbers to List
                }
                System.out.print("\033[H\033[2J");
                Thread.sleep(100);
                System.out.println("Scanning.. ");
                System.out.flush();

            }
            System.out.println("Scanning Ended .....");

            for (Object ob : objs){
                System.out.print(ob);                           // Output all objects in the List
            }
            System.out.println();
            System.out.println("Done loading program");
            Thread.sleep(50);
            Thread.sleep(50);
            System.out.println("Parsing programing");
            System.out.println();
    }



    /*
    *   @Function name: Scanner
    *   @Return type: Enum
    *
    *   Scanner function to scan tokens from List
    *   Check if token exists in the list and returns token type
    */

    public Token.Tokens scan_program_file(String done) throws Exception  {
        Token.Tokens tokenret = null;
            Thread.sleep(100);

                Pattern p = Pattern.compile("\\[0-9]+");
                Matcher m = p.matcher(objs.get(position).toString());   

               if (objs.get(position) instanceof Integer){
                   tokenret =  Token.Tokens.NUMBER;                     
               }

                if (objs.get(position) instanceof String ) {
                    if (objs.get(position).equals("if")) {
                        tokenret = Token.Tokens.IF;
                    } 
                    else if (objs.get(position).equals("then")) {
                        tokenret = Token.Tokens.THEN;
                    } 
                    else if (objs.get(position).equals("else")) {
                        tokenret = Token.Tokens.ELSE;
                    } 
                    else if (objs.get(position).equals("end")) {
                        tokenret = Token.Tokens.END;
                    } 
                    else if (objs.get(position).equals("repeat")) {
                        tokenret = Token.Tokens.REPEAT;
                    } 
                    else if (objs.get(position).equals("until")) {
                        tokenret = Token.Tokens.UNTIL;
                    } 
                    else if (objs.get(position).equals("write")){
                        tokenret = Token.Tokens.WRITE;
                    } 
                    else if (objs.get(position).equals("read")) {
                        tokenret = Token.Tokens.READ;
                    }
                    else if (objs.get(position).equals("+")) {
                        tokenret = Token.Tokens.PLUSOP;
                    } 
                    else if (objs.get(position).equals("-")) {
                        tokenret = Token.Tokens.SUBOP;
                    } 
                    else if (objs.get(position).equals("*")) {
                        tokenret = Token.Tokens.MULOP;
                    } 
                    else if (objs.get(position).equals("/")) {
                        tokenret = Token.Tokens.DIVOP;
                    } 
                    else if (objs.get(position).equals("=")) {
                        tokenret = Token.Tokens.EQOP;
                    } 
                    else if (objs.get(position).equals("<")) {
                        tokenret = Token.Tokens.LESSOP;
                    } 
                    else if (objs.get(position).equals("(")) {
                        tokenret = Token.Tokens.LFTPARA;
                    } 
                    else if (objs.get(position).equals(")")) {
                        tokenret = Token.Tokens.RGTPARA;
                    } 
                    else if (objs.get(position).equals(";")) {
                        tokenret = Token.Tokens.SEMI;
                    } 
                    else if (objs.get(position).equals(":=")) {
                        tokenret = Token.Tokens.ASSGN;
                    } 
                    else  if (!objs.get(position).equals("if") || !objs.get(position).equals("then") || !objs.get(position).equals("else") || !objs.get(position).equals("end") 
                    || !objs.get(position).equals("repeat") || !objs.get(position).equals("until") || !objs.get(position).equals("write") || !objs.get(position).equals("read") ){

                        tokenret = Token.Tokens.IDENTIFIER;
                    }
                }
                       
            // Check if end of List has been reached
            // and set the current position to the last position
            if (position >= sizeofprogram - 1){
                     position = sizeofprogram - 1;
                     if (done.equals("done")){
                         System.exit(1);
                     }
                }

                position++;
  
          
        return tokenret;
    }


    public String getFilename() {
        return filename;
    }
}