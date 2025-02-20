package cobra_compiler;



public class Token
{
    private String lexeme;
    private Token_Type type;
    Token()
    {

    }
    Token(String lexeme, Token_Type type)
    {
        this.lexeme = lexeme;
        this.type = type;
    }
    public void set_lexeme(String lexeme)
    {
        this.lexeme = lexeme;
        return;
    }
    public void set_type(Token_Type type)
    {
        this.type = type;
        return;
    }
    public String get_lexeme()
    {
        return this.lexeme;
    }
    public Token_Type get_type()
    {
        return this.type;
    }
    public void display()
    {
        System.out.print('<');
        System.out.print(this.lexeme);
        if(this.type == Token_Type.Identifier)
        {
            System.out.print(" | Identifier>");
        }
        else if(this.type == Token_Type.Keyword)
        {
            System.out.print(" | Keyword>");
        }
        else if(this.type == Token_Type.Operator)
        {
            System.out.print(" | Operator>");
        }
        else if(this.type == Token_Type.Constant)
        {
            System.out.print(" | Constant>");
        }
        else if(this.type == Token_Type.Exponent)
        {
            System.out.print(" | Exponent>");
        }
        else if(this.type == Token_Type.Literal)
        {
            System.out.print(" | Literal>");
        }
        else if(this.type == Token_Type.Punctuator)
        {
            System.out.print(" | Punctuator>");
        }
        return;
    }
}
