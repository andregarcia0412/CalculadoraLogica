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

    public static TruthTable parseTruthTable(String operationInfixa) {
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

        }

        return output;
    }
}
