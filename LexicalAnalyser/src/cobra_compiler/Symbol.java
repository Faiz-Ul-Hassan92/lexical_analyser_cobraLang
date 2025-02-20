package cobra_compiler;



public class Symbol
{
    private String name;
    private Scope scope;
    private Data_Type data_type;
    private Symbol_Type symbol_type;
    public Symbol()
    {

    }
    public Symbol(String name, Scope scope, Data_Type data_type, Symbol_Type symbol_type)
    {
        this.name = name;
        this.scope = scope;
        this.data_type = data_type;
        this.symbol_type = symbol_type;
    }
    public void set_name(String name)
    {
        this.name = name;
        return;
    }
    public void set_scope(Scope scope)
    {
        this.scope = scope;
        return;
    }
    public void set_data_type(Data_Type data_type)
    {
        this.data_type = data_type;
        return;
    }
    public void set_role(Symbol_Type symbol_type)
    {
        this.symbol_type = symbol_type;
        return;
    }
    public String get_name()
    {
        return this.name;
    }
    public Scope get_scope()
    {
        return this.scope;
    }
    public Data_Type get_data_type()
    {
        return this.data_type;
    }
    public Symbol_Type get_role()
    {
        return this.symbol_type;
    }
    public void display()
    {
        System.out.print(this.name);
        System.out.print(", ");
        if(this.scope == Scope.Global)
        {
            System.out.print("Global");
        }
        else if(this.scope == Scope.Local)
        {
            System.out.print("Local");
        }
        System.out.print(", ");
        if(this.data_type == Data_Type.Integer)
        {
            System.out.print("Integer");
        }
        else if(this.data_type == Data_Type.Decimal)
        {
            System.out.print("Decimal");
        }
        else if(this.data_type == Data_Type.Boolean)
        {
            System.out.print("Boolean");
        }
        else if(this.data_type == Data_Type.Character)
        {
            System.out.print("Character");
        }
        else if(this.data_type == Data_Type.String)
        {
            System.out.print("String");
        }
        System.out.print(", ");
        if(this.symbol_type == Symbol_Type.Variable)
        {
            System.out.print("Variable");
        }
        else if(this.symbol_type == Symbol_Type.Procedure)
        {
            System.out.print("Procedure");
        }
        return;
    }
}
