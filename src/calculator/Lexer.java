package calculator;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Lexer {
    public static List<Token> operationTokenized;
    enum TokenType {
        VAR(0),
        NOT(5),
        AND(4),
        OR(3),
        XOR(3),
        IMPLIES(2),
        IFF(1),
        LPARENTHESIS(-1),
        RPARENTHESIS(-1);

        public final int precedence;

        TokenType(int precedence){
            this.precedence = precedence;
        }
    }

    public static class Token {
        TokenType type;
        String text;

        Token(TokenType type, String text) {
            this.type = type;
            this.text = text;
        }
    }

    //work in progress
    public static TruthTable solveToTruthTable(String operationInfixa) {
        operationTokenized = tokenize(operationInfixa);
        List<Token> operationPosFixa = toPosFixa(operationTokenized);
        //calculate


        return new TruthTable(1);
    }



    public static List<Token> tokenize(String input){
        List<Token> tokens = new ArrayList<>();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            switch (c){
                case '~':
                    tokens.add(new Token(TokenType.NOT,"~"));
                    break;
                case '^':
                    tokens.add(new Token(TokenType.AND,"^"));
                    break;
                case 'v':
                    tokens.add(new Token(TokenType.OR, "v"));
                    break;
                case '⊻':
                    tokens.add(new Token(TokenType.XOR,"⊻"));
                    break;
                case '→':
                    tokens.add(new Token(TokenType.IMPLIES,"→"));
                    break;
                case '↔':
                    tokens.add(new Token(TokenType.IFF,"↔"));
                    break;
                case '(':
                    tokens.add(new Token(TokenType.LPARENTHESIS,"("));
                    break;
                case ')':
                    tokens.add(new Token(TokenType.RPARENTHESIS,")"));
                    break;

                default:
                    if(Character.isLetter(c)){
                        tokens.add(new Token(TokenType.VAR,String.valueOf(c)));
                    }
            }
        }
        //retorna em notação infixa.
        return  tokens;
    }

    //Algorítimo de Dijkstra | Shunting Yard Algorithm
    public static List<Token> toPosFixa(List<Token> tokens) {
        List<Token> output = new ArrayList<>();
        Stack<Token> operator = new Stack<>();
        for(Token token : tokens) {
            switch(token.type){
                case VAR:
                    output.add(token);
                    break;

                case NOT, AND, OR, XOR, IMPLIES, IFF, LPARENTHESIS:
                    while(!operator.isEmpty() && sub_validPrecedence(operator,token)){
                        output.add(operator.pop());
                    }
                    operator.push(token);
                    break;
                case RPARENTHESIS:
                    sub_clearStack(operator,output);
            }
        }
        sub_clearStack(operator,output);

        return output;
    }

    //"submetodos": ultilizados para separar camadas de abstração
    private static void sub_clearStack(Stack<Token> operator, List<Token> output){
        while(!operator.empty() &&  operator.peek().type != TokenType.LPARENTHESIS){
            output.add(operator.pop());
        }
    }

    private static boolean sub_validPrecedence(Stack<Token> operator, Token token) {
        return operator.peek().type.precedence > token.type.precedence;
    }
}
