import ProgScanner.Tokens;

class Parser {

    ProgScanner progScanner;
    Tokens tokens;

    public Parser(String filename) throws Exception {
        progScanner = new ProgScanner(filename);
    }


    public void parse(){
        progScanner.scan_program_file();
        program();
    }

    public void program() {
        stmt_sequence();
    }

    public void stmt_sequence() {
         tokens = progScanner.scan_program_file();
          
         while (tokens.equals(tokens.SEMI)){
                stmt_sequence();

         }
        
    }

    public tokens statement() {
        tokens = progScanner.scan_program_file();
        if (tokens.equals("if"))
         
        return true;
    }

    public void if_stmt() {
        tokens = progScanner.scan_program_file();
        if (tokens.equals("if")) {
            exp();
            if (tokens.equals("then")) {
                stmt_sequence();
                if (tokens.equals("else")) {
                    stmt_sequence();
                }
            }
        }

    }

    public void repeat_stmt() {
        tokens = progScanner.scan_program_file();
        if (tokens.equals("repeat")){
            stmt_sequence();
            if (tokens.equals("until")){
                exp();
            }
        }
    }

    public void assign_stmt() {
        tokens = progScanner.scan_program_file();
        if (tokens.equals("IDENTIFER")){

        }
    }

    public void read_stmt() {
        tokens = progScanner.scan_program_file();
        if(tokens.equals("read")){

        }
    }

    public void write_stmt() {
        tokens = progScanner.scan_program_file();
        if (token.equals("write")){
            exp();
        }
    }

    public void exp() {

    }

    public void comparison_op() {

    }

    public void simple_exp() {

    }

    public void addop() {
        tokens = progScanner.scan_program_file();

        if (tokens.equals('+') || tokens.equals('-')){

        }
    }

    public void term() {        
        factor();
        tokens = progScanner.scan_program_file();
        mulop();

    }

    public void mulop() {
        tokens = progScanner.scan_program_file();
        if (tokens.equals('*') || tokens.equals('/')){
             tokens = progScanner.scan_program_file();
             if (!tokens.equals('(') || tokens.equals("number") || tokens.equals("identifier")) {
                System.out.println("Invalid token encountered");
            }
        } else{
            System.out.println("Invalid token encoutered");
        }
    }

    public void factor() {
        tokens = progScanner.scan_program_file();
        if (tokens.equals('(')) {
            exp();
            tokens = progScanner.scan_program_file();
            if (!tokens.equals(')')){
                System.out.println("Expecting ) ");
            }
        } else if (tokens.equals("number")){
                factortoken = progScanner.scan_program_file();
        } else if (tokens.equals("identifier")){
                factortoken = progScanner.scan_program_file();
        } else {
            System.out.println("Invalid token encountered");
        }
    }

    public void match (Tokens token){
         Token checktoken;
        if (checktoken.equals(token)){

        } else {
            System.out.println("Invalid token");
        }
    }
}