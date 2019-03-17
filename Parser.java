
class Parser {

    ProgScanner progScanner;
    ProgScanner.Tokens tokens;

    public Parser(String filename) throws Exception {
        progScanner = new ProgScanner(filename);
    }


    public void parse() throws Exception{
        progScanner.scan_program_file();
        program();
    }

    public void program() throws Exception {
        stmt_sequence();
    }

    public void stmt_sequence() throws Exception {
         statement();
         tokens = progScanner.scan_program_file();
         while (tokens.equals(ProgScanner.Tokens.SEMI)){
                statement();
         }
    }

    public void statement()throws Exception {
        tokens = progScanner.scan_program_file();
        if (tokens.equals(ProgScanner.Tokens.IF)){
            if_stmt();
        } else if (tokens.equals(ProgScanner.Tokens.REPEAT)){
            repeat_stmt();
        } else if (tokens.equals(ProgScanner.Tokens.IDENTIFIER)){
            assign_stmt();
        } else if (tokens.equals(ProgScanner.Tokens.WRITE)){
            write_stmt();
        } else if (tokens.equals(ProgScanner.Tokens.READ)){
            read_stmt();
        } else {
            System.out.println("Invalid statement found!!!");
        }
    }

    public void if_stmt()throws Exception {
            exp();
            tokens = progScanner.scan_program_file();
            if (tokens.equals(ProgScanner.Tokens.THEN)) {
                stmt_sequence();
                tokens = progScanner.scan_program_file();
                if (tokens.equals(ProgScanner.Tokens.ELSE)) {
                    stmt_sequence();
                } else{
                    tokens = progScanner.scan_program_file();
                    if (tokens.equals(ProgScanner.Tokens.END)){
                        return;
                    }
                }
            } 
    }

    public void repeat_stmt()throws Exception {
             stmt_sequence();
             tokens = progScanner.scan_program_file();

            if (tokens.equals(ProgScanner.Tokens.UNTIL)){
                exp();
            }
    }

    public void assign_stmt()throws Exception {
        tokens = progScanner.scan_program_file();
        if (tokens.equals(ProgScanner.Tokens.ASSGN)){
                exp();
        }
    }

    public void read_stmt()throws Exception {
        tokens = progScanner.scan_program_file();
        if(tokens.equals(ProgScanner.Tokens.IDENTIFIER)){
            return;
        }
    }

    public void write_stmt()throws Exception {
        exp();
    }

    public void exp()throws Exception {
        simple_exp();
        tokens = progScanner.scan_program_file();
        if (tokens.equals(ProgScanner.Tokens.LESSOP) || tokens.equals(ProgScanner.Tokens.EQOP)){
            term();
        } else{
            return;
        }

    }

    public void comparison_op() {

    }

    public void simple_exp()throws Exception {
        term();
        tokens = progScanner.scan_program_file();
        if (tokens.equals(ProgScanner.Tokens.PLUSOP) || tokens.equals(ProgScanner.Tokens.SUBOP)) {
            term();
        }else{
            return;
        }
    }

    public void addop()throws Exception {
        tokens = progScanner.scan_program_file();
    }

    public void term()throws Exception {        
        factor();
        tokens = progScanner.scan_program_file();
        if (tokens.equals(ProgScanner.Tokens.MULOP) || tokens.equals(ProgScanner.Tokens.DIVOP)) {
            factor();
        } else{
            return;
        }
    }

    public void mulop()throws Exception {
        tokens = progScanner.scan_program_file();
       
    }

    public void factor()throws Exception {
        tokens = progScanner.scan_program_file();
        if (tokens.equals(ProgScanner.Tokens.LFTPARA)){
            exp();
            tokens = progScanner.scan_program_file();
            if (!tokens.equals(ProgScanner.Tokens.RGTPARA)){
                System.out.println("Expecting ) ");
            }
        } else if (tokens.equals(ProgScanner.Tokens.NUMBER)){
                System.out.println("Number found");
                return;
        } else if (tokens.equals(ProgScanner.Tokens.IDENTIFIER)){
                System.out.println("Identifier found");
                return;
        } else {
            System.out.println("Invalid token encountered");
            return;
        }
    }

    public void match (ProgScanner.Tokens token){
         ProgScanner.Tokens checktoken = null;
        if (checktoken.equals(token)){

        } else {
            System.out.println("Invalid token");
        }
    }
}