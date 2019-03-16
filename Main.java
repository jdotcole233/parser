import java.io.FileNotFoundException;

class Main {
    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("<No program file found!!! >");
            System.exit(1);
        }
        
        try{
            Parser parser = new Parser(args[0]);
            parser.parse();
        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }
 

    }
}