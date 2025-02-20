package cobra_compiler;



import java.io.*;
import java.util.ArrayList;



public class Lexical_Analyzer
{
    private final int number_of_keywords;
    private String keyword_table[];
    private String error_table[];
    private String standard_procedures[];
    public Lexical_Analyzer()
    {
        this.number_of_keywords = 15;
        this.keyword_table = new String[this.number_of_keywords];
        this.keyword_table[0] = "func";
        this.keyword_table[1] = "var";
        this.keyword_table[2] = "const";
        this.keyword_table[3] = "if";
        this.keyword_table[4] = "elif";
        this.keyword_table[5] = "else";
        this.keyword_table[6] = "for";
        this.keyword_table[7] = "while";
        this.keyword_table[8] = "ret";
        this.keyword_table[9] = "void";
        this.keyword_table[10] = "inte";
        this.keyword_table[11] = "deci";
        this.keyword_table[12] = "bool";
        this.keyword_table[13] = "char";
        this.keyword_table[14] = "string";
        this.error_table = new String[6];
        this.error_table[0] = "Error 1: Invalid program character";
        this.error_table[1] = "Error 2: Invalid character string";
        this.error_table[2] = "Error 3: Invalid constant character";
        this.error_table[3] = "Error 4: Invalid program comment";
        this.error_table[4] = "Error 5: Invalid decimal number";
        this.error_table[5] = "Error 6: Invalid exponent";
        this.standard_procedures = new String[2];
        this.standard_procedures[0] = "tout";
        this.standard_procedures[1] = "tin";
    }


    public String read_file(String file_path)
    {
        StringBuilder program = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file_path)))
        {
            String buffer;
            while ((buffer = br.readLine()) != null)
            {
                program.append(buffer).append("\n");
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return program.toString();
    }
    public ArrayList<Token> tokenize(String file_path)
    {
        String program = this.read_file(file_path);
        program = program + '\0';
        ArrayList<Token> tokens = new ArrayList<Token>();
        String text = "";
        int line = 1;
        for(int i = 0; i < (program.length() - 1); ++i)
        {
            if(program.charAt(i) >= 'a' && program.charAt(i) <= 'z')
            {
                text = text + program.charAt(i);
                ++i;
                if(i >= (program.length() - 2))
                {
                    tokens.add(new Token(text, Token_Type.Identifier));
                    break;
                }
                while(true)
                {
                    if(i >= (program.length() - 2))
                    {
                        tokens.add(new Token(text, Token_Type.Identifier));
                        break;
                    }
                    if(program.charAt(i) >= 'a' && program.charAt(i) <= 'z')
                    {
                        text = text + program.charAt(i);
                    }
                    else
                    {
                        tokens.add(new Token(text, Token_Type.Identifier));
                        --i;
                        break;
                    }
                    ++i;
                }
            }
            else if(program.charAt(i) >= '0' && program.charAt(i) <= '9')
            {
                int count = 0;
                text = text + program.charAt(i);
                ++i;
                if(i >= (program.length() - 2))
                {
                    tokens.add(new Token(text, Token_Type.Constant));
                    break;
                }
                while(true)
                {
                    if(i >= (program.length() - 2))
                    {
                        tokens.add(new Token(text, Token_Type.Constant));
                        break;
                    }
                    if(program.charAt(i) >= '0' && program.charAt(i) <= '9')
                    {
                        text = text + program.charAt(i);
                    }
                    else if(program.charAt(i) == '.' && count == 0)
                    {
                        text = text + program.charAt(i);
                        ++count;
                        if(program.charAt((i + 1)) < '0' || program.charAt((i + 1)) > '9')
                        {
                            System.out.print(this.error_table[4]);
                            System.out.print(" at line ");
                            System.out.print(line);
                            System.out.print(".\n");
                            return null;
                        }
                        else if(program.charAt((i - 1)) < '0' || program.charAt((i - 1)) > '9')
                        {
                            System.out.print(this.error_table[4]);
                            System.out.print(" at line ");
                            System.out.print(line);
                            System.out.print(".\n");
                            return null;
                        }
                    }
                    else
                    {
                        tokens.add(new Token(text, Token_Type.Constant));
                        --i;
                        break;
                    }
                    ++i;
                }
            }
            else if(program.charAt(i) == '\"')
            {
                text = text + '\"';
                ++i;
                if(i >= (program.length() - 2))
                {
                    System.out.print(this.error_table[1]);
                    System.out.print(" at line ");
                    System.out.print(line);
                    System.out.print(".\n");
                    return null;
                }
                while(program.charAt(i) != '\"')
                {
                    if(i >= (program.length() - 2))
                    {
                        System.out.print(this.error_table[1]);
                        System.out.print(" at line ");
                        System.out.print(line);
                        System.out.print(".\n");
                        return null;
                    }
                    if(program.charAt(i) == '\n')
                    {
                        ++line;
                    }
                    else
                    {
                        text = text + program.charAt(i);
                    }
                    ++i;
                }
                text = text + '\"';
                tokens.add(new Token(text, Token_Type.Literal));
            }
            else if(program.charAt(i) == '\'')
            {
                text = text + '\'';
                ++i;
                if(i >= (program.length() - 2))
                {
                    System.out.print(this.error_table[2]);
                    System.out.print(" at line ");
                    System.out.print(line);
                    System.out.print(".\n");
                    return null;
                }
                text = text + program.charAt(i);
                ++i;
                if(i >= (program.length() - 2))
                {
                    System.out.print(this.error_table[2]);
                    System.out.print(" at line ");
                    System.out.print(line);
                    System.out.print(".\n");
                    return null;
                }
                if(program.charAt(i) == '\'')
                {
                    text = text + '\'';
                    tokens.add(new Token(text, Token_Type.Constant));
                }
                else
                {
                    System.out.print(this.error_table[2]);
                    System.out.print(" at line ");
                    System.out.print(line);
                    System.out.print(".\n");
                    return null;
                }
            }
            else if(program.charAt(i) == '@')
            {
                text = text + program.charAt(i);
                ++i;
                if(i >= (program.length() - 2))
                {
                    System.out.print(this.error_table[5]);
                    System.out.print(" at line ");
                    System.out.print(line);
                    System.out.print(".\n");
                    return null;
                }
                if((program.charAt(i) == '+' || program.charAt(i) == '-'))
                {
                    text = text + program.charAt(i);
                    ++i;
                    if(i >= (program.length() - 2))
                    {
                        System.out.print(this.error_table[5]);
                        System.out.print(" at line ");
                        System.out.print(line);
                        System.out.print(".\n");
                        return null;
                    }
                    if(program.charAt(i) == '0')
                    {
                        System.out.print(this.error_table[5]);
                        System.out.print(" at line ");
                        System.out.print(line);
                        System.out.print(".\n");
                        return null;
                    }
                }
                if(program.charAt(i) < '0' || program.charAt(i) > '9')
                {
                    System.out.print(this.error_table[5]);
                    System.out.print(" at line ");
                    System.out.print(line);
                    System.out.print(".\n");
                    return null;
                }
                while(true)
                {
                    if(i >= (program.length() - 2) && (program.charAt((i - 1)) >= '0' && program.charAt((i - 1)) <= '9'))
                    {
                        tokens.add(new Token(text, Token_Type.Exponent));
                        break;
                    }
                    if(program.charAt(i) >= '0' && program.charAt(i) <= '9')
                    {
                        text = text + program.charAt(i);
                    }
                    else
                    {
                        tokens.add(new Token(text, Token_Type.Exponent));
                        --i;
                        break;
                    }
                    ++i;
                }
            }
            else if(program.charAt(i) == '+')
            {
                tokens.add(new Token("+", Token_Type.Operator));
            }
            else if(program.charAt(i) == '-')
            {
                tokens.add(new Token("-", Token_Type.Operator));
            }
            else if(program.charAt(i) == '*')
            {
                tokens.add(new Token("*", Token_Type.Operator));
            }
            else if(program.charAt(i) == '/')
            {
                tokens.add(new Token("/", Token_Type.Operator));
            }
            else if(program.charAt(i) == '%')
            {
                tokens.add(new Token("%", Token_Type.Operator));
            }
            else if(program.charAt(i) == '^')
            {
                tokens.add(new Token("^", Token_Type.Operator));
            }
            else if(program.charAt(i) == '=' && program.charAt((i + 1)) != '=')
            {
                tokens.add(new Token("=", Token_Type.Operator));
            }
            else if(program.charAt(i) == '>' && program.charAt((i + 1)) != '=')
            {
                tokens.add(new Token(">", Token_Type.Operator));
            }
            else if(program.charAt(i) == '<' && program.charAt((i + 1)) != '=')
            {
                tokens.add(new Token("<", Token_Type.Operator));
            }
            else if(program.charAt(i) == '=' && program.charAt((i + 1)) == '=')
            {
                tokens.add(new Token("==", Token_Type.Operator));
                ++i;
            }
            else if(program.charAt(i) == '>' && program.charAt((i + 1)) == '=')
            {
                tokens.add(new Token(">=", Token_Type.Operator));
                ++i;
            }
            else if(program.charAt(i) == '<' && program.charAt((i + 1)) == '=')
            {
                tokens.add(new Token("<=", Token_Type.Operator));
                ++i;
            }
            else if(program.charAt(i) == '{')
            {
                tokens.add(new Token("{", Token_Type.Operator));
            }
            else if(program.charAt(i) == '}')
            {
                tokens.add(new Token("}", Token_Type.Operator));
            }
            else if(program.charAt(i) == '(')
            {
                tokens.add(new Token("(", Token_Type.Operator));
            }
            else if(program.charAt(i) == ')')
            {
                tokens.add(new Token(")", Token_Type.Operator));
            }
            else if(program.charAt(i) == '[')
            {
                tokens.add(new Token("[", Token_Type.Operator));
            }
            else if(program.charAt(i) == ']')
            {
                tokens.add(new Token("]", Token_Type.Operator));
            }
            else if(program.charAt(i) == ',')
            {
                tokens.add(new Token(",", Token_Type.Punctuator));
            }
            else if(program.charAt(i) == ';')
            {
                tokens.add(new Token(";", Token_Type.Punctuator));
            }
            else if(program.charAt(i) == '#')
            {
                ++i;
                if(i >= (program.length() - 2))
                {
                    System.out.print(this.error_table[3]);
                    System.out.print(" at line ");
                    System.out.print(line);
                    System.out.print(".\n");
                    return null;
                }
                while(program.charAt(i) != '#')
                {
                    if(i >= (program.length() - 2))
                    {
                        System.out.print(this.error_table[3]);
                        System.out.print(" at line ");
                        System.out.print(line);
                        System.out.print(".\n");
                        return null;
                    }
                    if(program.charAt(i) == '\n')
                    {
                        ++line;
                    }
                    ++i;
                }
            }
            else if(program.charAt(i) == '\n')
            {
                ++line;
            }
            else if(program.charAt(i) == ' ')
            {
                continue;
            }
            else if(program.charAt(i) == '\0')
            {
                break;
            }
            else
            {
                System.out.print(this.error_table[0]);
                System.out.print(" at line ");
                System.out.print(line);
                System.out.print(".\n");
                return null;
            }
            text = "";
        }
        for (Token token : tokens)
        {
            for(int i = 0; i < this.number_of_keywords; ++i)
            {
                if(token.get_lexeme().equals(this.keyword_table[i]))
                {
                    token.set_type(Token_Type.Keyword);
                }
            }
        }
        return tokens;
    }
}
