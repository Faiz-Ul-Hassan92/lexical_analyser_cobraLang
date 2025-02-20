package cobra_compiler;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // Your existing lexical analysis

        RegexToDFA.makeDFAs("Samples/regex_exp.txt");

        Lexical_Analyzer lex = new Lexical_Analyzer();
        ArrayList<Token> tokens = lex.tokenize("Samples/main.cba");
        if(tokens != null) {
            System.out.print("\nDisplaying Token Stream\n\n");
            for (Token token : tokens) {
                token.display();
                System.out.print('\n');
            }
            System.out.print("\nThe total number of tokens in this file is ");
            System.out.print(tokens.size());
            System.out.print(".\n");

            System.out.print("\nDisplaying Symbol Table\n\n");
            Symbol_Table symbol_table = new Symbol_Table();
            symbol_table.lexical_phase(tokens);
            symbol_table.display();
        }
    }
}