package cobra_compiler;



import java.util.ArrayList;



public class Symbol_Table
{
    private ArrayList<Symbol> symbols;
    public Symbol_Table()
    {
        this.symbols = new ArrayList<Symbol>();
    }
    public void lexical_phase(ArrayList<Token> tokens)
    {
        if(tokens != null)
        {
            for(Token token : tokens)
            {
                if(token.get_type() == Token_Type.Identifier)
                {
                    this.symbols.add(new Symbol(token.get_lexeme(), Scope.Global, Data_Type.Integer, Symbol_Type.Procedure));
                }
            }
        }
        return;
    }
    public void display()
    {
        for(Symbol symbol : this.symbols)
        {
            symbol.display();
            System.out.print('\n');
        }
    }
}
