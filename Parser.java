import java.util.ArrayList;

class Parser {

    ProgScanner progScanner;
    ProgScanner.Tokens tokens;
    ArrayList<Object> objs;

    public Parser(String filename) throws Exception {
        progScanner = new ProgScanner(filename);
    }


    //public void parse() throws Exception{
    //    program();
    //}

    public void program() throws Exception {
            System.out.println("Program called");
            stmt_sequence();
    }

    public boolean stmt_sequence() throws Exception {
        boolean isstmt_seq = false;
        boolean isvalid = statement();
        if (!isvalid){
            System.out.println("Error statement expected");
            // System.exit(1);
        } 
        isstmt_seq = true;
        System.out.println("Statment sequence called " +  isvalid);
        // tokens = progScanner.scan_program_file();
        boolean issemicolon = false;
        if (tokens.equals(ProgScanner.Tokens.SEMI)){
            System.out.println("In Semi colon");
            issemicolon = true;
        }
        while (issemicolon) {
            System.out.println("Semi colon found");
            stmt_sequence();
            isstmt_seq = true;
            issemicolon = false;
        }
        return isstmt_seq;
    }

    public boolean statement() throws Exception {
        boolean isvalid = true;
        tokens = progScanner.scan_program_file("");
        System.out.println("in stmt Reading => " + tokens + " position " + progScanner.position);
        if (tokens.equals(ProgScanner.Tokens.IF)) {
            if_stmt();
            isvalid = true;
        } else if (tokens.equals(ProgScanner.Tokens.REPEAT)) {
            repeat_stmt();
            isvalid = true;
        } else if (tokens.equals(ProgScanner.Tokens.IDENTIFIER)) {
            assign_stmt();
            isvalid = true;
        } else if (tokens.equals(ProgScanner.Tokens.WRITE)) {
            write_stmt();
            isvalid = true;
        } else if (tokens.equals(ProgScanner.Tokens.READ)) {
            read_stmt();
            isvalid = true;
        } else {
            System.out.println("Invalid statement found!!!");
            isvalid = false;
            // System.exit(1);
        }

        return isvalid;
    }

    public void if_stmt() throws Exception{
        System.out.println("if statement called");
        boolean isexp = exp();
        System.out.println("return value in if_stmt => " + isexp);
        if (!isexp){
            System.out.println("in if statement");
            progScanner.position -= 1;
            tokens = progScanner.scan_program_file("");
            if (tokens.equals(ProgScanner.Tokens.THEN)){
                System.out.println("Token encountered => " + tokens);
                boolean isstmt_seq = stmt_sequence();
                if (!isstmt_seq){
                    System.out.println("Statement sequence expected in if statment");
                } else {
                    progScanner.position -= 1;
                    tokens = progScanner.scan_program_file("");
                    if (tokens.equals(ProgScanner.Tokens.ELSE)){
                        boolean isstmt_seq2 = stmt_sequence();
                        if (!isstmt_seq2){
                            System.out.println("Statement sequence expected in if statment");
                        } else {
                            tokens = progScanner.scan_program_file("");
                            if (tokens.equals(ProgScanner.Tokens.END)){
                                 tokens = progScanner.scan_program_file("");
                                System.out.println("valid if statement => " + tokens);
                                 if (tokens.equals(ProgScanner.Tokens.SEMI)){
                                        statement();
                                    }
                            }
                        }
                    } else {
                        System.out.println("Expecting an ELSE token in if statment");
                    }
                }
            } else{
                System.out.println("Expecting THEN token in if statment !!");
            }
        } else {
            System.out.println("Expecting an exp -> ( exp ) | number | identifier in if statment");
        }
        // stmt_sequence();
        // stmt_sequence();

 

    }
 
    public void repeat_stmt() throws Exception{
        System.out.println("repeat called");
        boolean isstmt_seq = stmt_sequence();
        if (!isstmt_seq){
            System.out.println("Statement sequence expected in repeat stmt");
        } else {
            tokens = progScanner.scan_program_file("");
            System.out.println("value in repeat => " + tokens);
            if (tokens.equals(ProgScanner.Tokens.UNTIL)){
                System.out.println("Until found => " + tokens);
                boolean isexp = exp();
                if (!isexp){
                    System.out.println("Expecting an expression in repeat statement");
                }
            }
        }
        // exp();
    }

    public void assign_stmt() throws Exception{
        System.out.println("assignment called");
        tokens = progScanner.scan_program_file("");
        if (tokens.equals(ProgScanner.Tokens.ASSGN)){
            boolean isexp = exp();
            if (!isexp) {
                System.out.println("Expecting an expression");
            }
        }
        
    }

    public void write_stmt() throws Exception{
        System.out.println("write statement called");
        boolean isexp = exp();
        if (!isexp){
            System.out.println("Expression expected in write statement ");
        }
    }

    public void read_stmt() throws Exception{
        System.out.println("read statement called");
        String done = "";
        // progScanner.position -= 1;
        tokens = progScanner.scan_program_file(done);
        if (!tokens.equals(ProgScanner.Tokens.IDENTIFIER)){
            System.out.println("Expecting an identifer after read statement");
            return;
        }
        System.out.println("Identifier found");

        if (tokens.equals(ProgScanner.Tokens.SEMI)){
                statement();
            }
    }

    public boolean exp() throws Exception{
        System.out.println("Expression called");
        boolean isexp = false;
        boolean isterm = term();
        if(isterm){
            // progScanner.position -= 1;
            tokens = progScanner.scan_program_file("");
            boolean iscomp = comparison_op(tokens);
            while (iscomp){
                simple_exp();
                isexp = true;
                iscomp = false;
            }
            // System.out.println("Comp failed");
            // isexp = true;

        }
        // comparison_op();
        // simple_exp();
        return isexp;
    }

    public boolean comparison_op(ProgScanner.Tokens token) throws Exception{
        System.out.println("Comparison operation called");
        boolean iscomp = false;
        // tokens = progScanner.scan_program_file();
        System.out.println("Value in comp => " + token);
        if (token.equals(ProgScanner.Tokens.LESSOP) || token.equals(ProgScanner.Tokens.EQOP)){
             iscomp = true;
        }

        return iscomp;
    }


    public boolean simple_exp () throws Exception{
        System.out.println("Simple expression called");
        boolean issimple_exp = false;
        boolean isterm = term();
        if (isterm){
            System.out.println("in Simple expression");
            tokens = progScanner.scan_program_file("");
            boolean isaddop = addop(tokens);
            while(isaddop){
                simple_exp();
                issimple_exp = true;
                System.out.println("after isaddopp is called => " + tokens);
                isaddop = false;
            }
            // issimple_exp = true;
            if (tokens.equals(ProgScanner.Tokens.SEMI)){
                statement();
            }

             if (tokens.equals(ProgScanner.Tokens.PLUSOP) || tokens.equals(ProgScanner.Tokens.SUBOP)){
                System.out.println("value after enters plus in simple_exp => " + tokens);
                isterm = true;

            }
        }
        // addop();
        // simple_exp();
        return issimple_exp;
    }

    public boolean addop(ProgScanner.Tokens token) throws Exception{
        System.out.println("Add op called");
        boolean isaddop = false;
        // tokens = progScanner.scan_program_file("");
        if (token.equals(ProgScanner.Tokens.PLUSOP) || token.equals(ProgScanner.Tokens.SUBOP)){
            isaddop = true;
        }

        return isaddop;
    }

    public boolean term() throws Exception{
        System.out.println("Term called");
        boolean isterm = false;
        boolean isfactor = factor();

        if (isfactor){
            // progScanner.position -= 1;
            tokens = progScanner.scan_program_file("");
            System.out.println("Value in mulop => " + tokens);
            boolean ismulop = mulop(tokens);
            while(ismulop){
                factor();
                isterm = true;
                ismulop = false;
            }
            System.out.println("Mul op failed");
            // factor();
            if (tokens.equals(ProgScanner.Tokens.SEMI)){
                statement();
            }

            System.out.println("value after mul op fails => " + tokens);


            if (tokens.equals(ProgScanner.Tokens.LESSOP) || tokens.equals(ProgScanner.Tokens.EQOP)){
                // System.out.println("value after enters less in term => " + tokens);
                isterm = true;

            }

              if (tokens.equals(ProgScanner.Tokens.PLUSOP) || tokens.equals(ProgScanner.Tokens.SUBOP)){
                System.out.println("value after enters plus in simple_exp => " + tokens);
                isterm = true;

            }
            // isterm = true;
        }
        // mulop();
        // term();
        return isterm;
    }

    public boolean mulop (ProgScanner.Tokens token) throws Exception{
        System.out.println("Mul op called");
        boolean ismulop = false;
        // tokens = progScanner.scan_program_file();
        System.out.println("Mul op called " + token);
        if (token.equals(ProgScanner.Tokens.MULOP) || token.equals(ProgScanner.Tokens.DIVOP)){
            System.out.println("Mul op => " + token);
            ismulop = true;
        }


        return ismulop;
    }

    public boolean factor() throws Exception{
        System.out.println("Factor called"); 
        boolean isfactor = false;  
        String done = "not";
        tokens = progScanner.scan_program_file(done);
        System.out.println("Token in factor => " + tokens);
        if (tokens.equals(ProgScanner.Tokens.NUMBER)){
            System.out.println("Number found");
            isfactor = true;
        } else if (tokens.equals(ProgScanner.Tokens.IDENTIFIER)){
            System.out.println("Identifier found");
            isfactor = true;
        } else if (tokens.equals(ProgScanner.Tokens.LFTPARA)){
            System.out.println("Left paranthese");
            boolean isexp = exp();
            if (!isexp){
                isfactor = false;
            }
            System.out.println("Failed in factor");
            progScanner.position -= 1;
            tokens = progScanner.scan_program_file("");
            if (tokens.equals(ProgScanner.Tokens.RGTPARA)){
                System.out.println("Right paranthese => " + tokens);
                isfactor = true;
            } else {
                isfactor = false;
            }
        } 
        // else if (tokens.equals(ProgScanner.Tokens.RGTPARA)) {
        //     System.out.println("Right paranthese");
        //     isfactor = true;
        // }
         done = "done";
        return isfactor;     

        // boolean isvalid = true;
        // tokens = progScanner.scan_program_file();
        // System.out.println("Reading in factor => " + tokens);
        // if (tokens.equals(ProgScanner.Tokens.LFTPARA)) {
        //     if (exp()) {
        //         // tokens = progScanner.scan_program_file();
        //         if (factor()) {
        //             System.out.println("valid string");
        //             isvalid = true;
        //         } else {
        //             System.out.println("Something went wrong in factor() in factor() ");
        //             isvalid = false;
        //         }
        //     } else {
        //         System.out.print("Something went wrong in exp() in factor()");
        //         isvalid = false;
        //     }
        // } else if (tokens.equals(ProgScanner.Tokens.RGTPARA)) {
        //     isvalid = true;
        // } else if (tokens.equals(ProgScanner.Tokens.NUMBER)) {
        //     System.out.println("Number found");
        //     isvalid = true;
        // } else if (tokens.equals(ProgScanner.Tokens.IDENTIFIER)) {
        //     System.out.println("Identifier found");
        //     isvalid = true;
        // } else {
        //     System.out.println("Invalid token encountered");
        //     isvalid = false;
        // }
        // System.out.println("Leaving factor => ");

        // return isvalid;
    }
   
}