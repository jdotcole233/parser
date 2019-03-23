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
            File documentname = new File(getFilename());
            if (!documentname.exists()) {
                System.out.println("File not found");
            }
            Scanner scanner = new Scanner(documentname);
            while (scanner.hasNext()) {
                String a = scanner.next();
                try{
                    Integer ar = Integer.parseInt(a);
                    if (ar instanceof Integer) {
                        objs.add(ar);
                    // System.out.println("In => " + ar);
                    Thread.sleep(200);
                   } 

                }catch(NumberFormatException ex){
                    // System.out.println(ex.getMessage());
                    objs.add(a);
                }
                System.out.print("\033[H\033[2J");
                Thread.sleep(100);
                System.out.println("Scanning.. ");
                System.out.flush();

            }
            System.out.println("Scanning Ended .....");

            for (Object ob : objs){
                System.out.print(ob);
            }
            System.out.println();
            System.out.println("Done loading program");
            Thread.sleep(50);
            Thread.sleep(50);
            System.out.println("Parsing programing");
            System.out.println();
    }

    public ProgScanner.Tokens scan_program_file() throws Exception  {
        ProgScanner.Tokens tokenret = null;
        // System.out.println("next..");
        // System.out.println("size of file " + objs.size());
            Thread.sleep(100);
            if (position >= sizeofprogram){
                    return null; 
                }

                Pattern p = Pattern.compile("\\[0-9]+");
                Matcher m = p.matcher(objs.get(position).toString());   

               if (objs.get(position) instanceof Integer){
                //    System.out.println(objs.get(position));
                   tokenret =  ProgScanner.Tokens.NUMBER;
               }

                if (objs.get(position) instanceof String ) {
                    if (objs.get(position).equals("if")) {
                        // System.out.println(objs.get(position));
                        tokenret = ProgScanner.Tokens.IF;
                    } 
                    else if (objs.get(position).equals("then")) {
                        // System.out.println(objs.get(position));
                        tokenret = ProgScanner.Tokens.THEN;
                    } 
                    else if (objs.get(position).equals("else")) {
                        // System.out.println(objs.get(position));

                        tokenret = ProgScanner.Tokens.ELSE;
                    } 
                    else if (objs.get(position).equals("end")) {
                        // System.out.println(objs.get(position));

                        tokenret = ProgScanner.Tokens.END;
                    } 
                    else if (objs.get(position).equals("repeat")) {
                        // System.out.println(objs.get(position));

                        tokenret = ProgScanner.Tokens.REPEAT;
                    } 
                    else if (objs.get(position).equals("until")) {
                        // System.out.println(objs.get(position));

                        tokenret = ProgScanner.Tokens.UNTIL;
                    } 
                    else if (objs.get(position).equals("write")){
                        // System.out.println(objs.get(position));

                        tokenret = ProgScanner.Tokens.WRITE;
                    } 
                    else if (objs.get(position).equals("read")) {
                        // System.out.println(objs.get(position));

                        tokenret = ProgScanner.Tokens.READ;
                    }
                    else if (objs.get(position).equals("+")) {
                        // System.out.println(objs.get(position));

                        tokenret = ProgScanner.Tokens.PLUSOP;
                    } 
                    else if (objs.get(position).equals("-")) {
                        // System.out.println(objs.get(position));

                        tokenret = ProgScanner.Tokens.SUBOP;
                    } 
                    else if (objs.get(position).equals("*")) {
                        // System.out.println(objs.get(position));

                        tokenret = ProgScanner.Tokens.MULOP;
                    } 
                    else if (objs.get(position).equals("/")) {
                        // System.out.println(objs.get(position));

                        tokenret = ProgScanner.Tokens.DIVOP;
                    } 
                    else if (objs.get(position).equals("=")) {
                        // System.out.println(objs.get(position));

                        tokenret = ProgScanner.Tokens.EQOP;
                    } 
                    else if (objs.get(position).equals("<")) {
                        // System.out.println(objs.get(position));

                        tokenret = ProgScanner.Tokens.LESSOP;
                    } 
                    else if (objs.get(position).equals("(")) {
                        // System.out.println(objs.get(position));

                        tokenret = ProgScanner.Tokens.LFTPARA;
                    } 
                    else if (objs.get(position).equals(")")) {
                        // System.out.println(objs.get(position));

                        tokenret = ProgScanner.Tokens.RGTPARA;
                    } 
                    else if (objs.get(position).equals(";")) {
                        // System.out.println(objs.get(position));

                        tokenret = ProgScanner.Tokens.SEMI;
                    } 
                    else if (objs.get(position).equals(":=")) {
                        // System.out.println(objs.get(position));
                        tokenret = ProgScanner.Tokens.ASSGN;
                    } 
                    else  if (!objs.get(position).equals("if") || !objs.get(position).equals("then") || !objs.get(position).equals("else") || !objs.get(position).equals("end") 
                    || !objs.get(position).equals("repeat") || !objs.get(position).equals("until") || !objs.get(position).equals("write") || !objs.get(position).equals("read") ){

                        tokenret = ProgScanner.Tokens.IDENTIFIER;
                       
                    //     Pattern pi = Pattern.compile("\\W");
                    //     Matcher mi = pi.matcher(objs.get(position).toString());

                    //    if (mi.find()) 
                    //     {
                    //           System.out.println("invalid"); 
                    //           return null;    
                    //     } else {
                    //          //System.out.println(mi.group());
                    //          System.out.println(objs.get(position));

                    //      }

                    }
                }
                // System.out.println("current position of pointer " + position);

                position++;
          
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