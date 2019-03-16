import java.io.*;

class ProgScanner {

    enum Tokens {
        PLUSOP, SUBOP, MULOP, DIVOP, EQOP, LESSOP, LFTPARA, RGTPARA, SEMI, ASSGN, IF, ELSE, THEN, END, REPEAT, UNTIL, READ, WRITE, NUMBER, IDENTIFIER
    }

    private String filename;
    private FileWriter fileWriter;
    String error;
    FileReader fileReader;
    Tokens tokens;



    public ProgScanner(String  filename) throws  Exception{
         this.filename = filename;
         fileWriter = new FileWriter("errortxt.txt");

    }

    public tokens scan_program_file() {

        try {
            fileReader = new FileReader(getFilename());

            File checkfile = new File(getFilename());
            if (!checkfile.exists()) {
                fileWriter.write("File does not exist");
                readFile("errortxt");
                System.exit(1);
            }

            if (!checkfile.canRead()) {
                fileWriter.write("The file cannot be read!!!");
                readFile("errortxt");
                System.exit(1);
            }
            int character = fileReader.read();
            while (Character.isWhitespace((char) character)) {

                    if (Character.isLetter((char) character)){
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append((char) character);

                    if (stringBuilder.equals("if")){
                        return tokens.IF;
                    } else if (stringBuilder.equals("then")){
                        return tokens.THEN;
                    } else if(stringBuilder.equals("else")){
                        return tokens.ELSE;
                    } else if (stringBuilder.equals("end")) {
                        return tokens.END;
                    } else if (stringBuilder.equals("repeat")) {
                        return tokens.REPEAT;
                    } else if (stringBuilder.equals("until")) {
                        return tokens.UNTIL;
                    } else if (stringBuilder.equals("write")) {
                        return tokens.WRITE;
                    } else {
                        return tokens.IDENTIFIER;
                    }                    
                } else if (Character.isDigit((char) character)){
                    return tokens.NUMBER;
               }

               switch ((char) character){
                   case "+":
                   return tokens.PLUSOP;
                   case "-":
                   return tokens.SUBOP;
                    case "*":
                    return tokens.MULOP;
                    case "/":
                    return tokens.DIVOP;
                    case "=":
                    return tokens.EQOP;
                    case "<":

                    return tokens.LESSOP;
                    case "(":

                    return tokens.LFTPARA;
                    case ")":

                    return tokens.RGTPARA;
                    case ";":

                    return tokens.SEMI;
                    case ":=":

                    return tokens.EQOP;
               }

                character = fileReader.read();
            }
            System.out.println();
            fileReader.close();

        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
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