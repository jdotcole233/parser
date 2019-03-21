
class Parser {

    ProgScanner progScanner;
    ProgScanner.Tokens tokens;

    public Parser(String filename) throws Exception {
        progScanner = new ProgScanner(filename);
    }


    public void parse() throws Exception{
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
        System.out.println("in stmt Reading => " + tokens + " position " + progScanner.position);
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
        System.out.println("in assign_stmt Reading => " + tokens + " position " + progScanner.position);
        if (tokens.equals(ProgScanner.Tokens.ASSGN)){
            System.out.println("In Assign ");
                exp();
        } else {
            System.out.println("Error, Expecting an expression");
            return;
        }
    }

    public  read_stmt()throws Exception {
        tokens = progScanner.scan_program_file();
        System.out.println("in read_stmt Reading => " + tokens + " position " + progScanner.position);
        if(tokens.equals(ProgScanner.Tokens.IDENTIFIER)){
            System.out.println("Here");
            return;
        }
    }

    public boolean write_stmt()throws Exception {
        boolean isvalid = true;
        if (exp()) {
            System.out.println("Valid string in write stmt");
            isvalid = true;
        } else {
            System.out.println("Somthing went wrong in write_stmt()");
            isvalid = false;
        }
        return isvalid;
    }

    public boolean exp() throws Exception {
        boolean isvalid = true;
        if(term()){
            // tokens = progScanner.scan_program_file();
            // System.out.println("in exp Reading => " + tokens);
            while (comparison_op()) {
                if(term()){
                    System.out.println("valid strinfg");
                    isvalid = true;
                } else{
                    System.out.println("Something went wrong in term() in exp()");
                    isvalid = false;
                }
            }
            System.out.println("valid string term");
            isvalid = true;
        } else {
            System.out.println("Something went wrong in exp()");
            isvalid = false;
        }
        return isvalid;
    }

    public boolean comparison_op() {
        boolean isvalid = true;
        tokens = progScanner.scan_program_file();
        System.out.println("in comparison operator Reading => " + tokens);
        if (tokens.equals(ProgScanner.Tokens.LESSOP) || tokens.equals(ProgScanner.Tokens.EQOP)){
            isvalid = true;
        } else {
            isvalid = false;
        }
        return isvalid;
    }

    public boolean simple_exp()throws Exception {
        boolean isvalid = true;
        if (term()) {
            // tokens = progScanner.scan_program_file();
            // System.out.println("in simple exp Reading => " + tokens);
            while (addop()) {
                if (term()){
                    System.out.print("valid string");
                    isvalid = true;
                }
            }
            System.out.print("valid string");
            isvalid = true;
        } else {
            System.out.println("Something went wrong in simple_exp()");
            isvalid = false;
        }
    
        return isvalid;
    }

    public boolean addop()throws Exception {
        boolean isvalid = true;
        tokens = progScanner.scan_program_file();
        if (tokens.equals(ProgScanner.Tokens.PLUSOP) || tokens.equals(ProgScanner.Tokens.SUBOP)){
            isvalid = true;
        } else{
            System.out.println("Something went wrong in addop()");
            isvalid = false;
        }

        return isvalid;
    }

    public boolean term()throws Exception {     
        boolean isvalid = true;   
        if(factor()){
            // tokens = progScanner.scan_program_file();
            // System.out.println("Reading => in term" + tokens);
            while (mulop()) {
                if (factor()){
                    System.out.println("valid string");
                    isvalid =  true;
                } else {
                    System.out.println("Something went wrong with fact() in term()");
                    isvalid = false;
                }
            }
            System.out.print("valid string");
            isvalid = true;
        } else{
            System.out.println("Something went wrong in term()");
            isvalid = false;
        }
        return isvalid;
    }

    public boolean mulop()throws Exception {
        boolean isvalid = true;
        tokens = progScanner.scan_program_file();
        System.out.println("Reading => in mulop" + tokens);
        if (tokens.equals(ProgScanner.Tokens.MULOP) || tokens.equals(ProgScanner.Tokens.DIVOP)){
            isvalid = true;
        } else {
            isvalid = false;
        }
       
        return isvalid;
    }

    public boolean factor()throws Exception {
        boolean isvalid = true;
        tokens = progScanner.scan_program_file();
        System.out.println("Reading in factor => " + tokens);
        if (tokens.equals(ProgScanner.Tokens.LFTPARA)){
             if (exp()){
                 //tokens = progScanner.scan_program_file();
                 if (factor()){
                        System.out.println("valid string");
                        isvalid = true;
                 } else {
                     System.out.println("Something went wrong in factor() in factor() ");
                     isvalid = false;
                 }
             } else {
                 System.out.print("Something went wrong in exp() in factor()");
                 isvalid = false;
             }
        }  else if (tokens.equals(ProgScanner.Tokens.RGTPARA)){
            isvalid = true;
        }
        else if (tokens.equals(ProgScanner.Tokens.NUMBER)){
                System.out.println("Number found");
                isvalid = true;
        } else if (tokens.equals(ProgScanner.Tokens.IDENTIFIER)){
                System.out.println("Identifier found");
                isvalid = true;
        } else {
            System.out.println("Invalid token encountered");
            isvalid = false;
        }
        return isvalid;
    }

    public void match (ProgScanner.Tokens token){
         ProgScanner.Tokens checktoken = null;
        if (checktoken.equals(token)){

        } else {
            System.out.println("Invalid token");
        }
    }
}