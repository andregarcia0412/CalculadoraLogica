package calculator;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Lexer {
    public static List<Token> operationTokenized;
    enum TokenType {
        VAR(0),

        NOT(5){
            @Override
            public Boolean calculate(Boolean a, Boolean b) {
                return LogicCalculator.negation(a);
            }
        },

        AND(4){
            @Override
            public Boolean calculate(Boolean a, Boolean b) {
                return LogicCalculator.conjunction(a, b);
            }
        },

        OR(3){
            @Override
            public Boolean calculate(Boolean a, Boolean b) {
                return LogicCalculator.disjunction(a, b);
            }
        },

        XOR(3){
            @Override
            public Boolean calculate(Boolean a, Boolean b) {
                return LogicCalculator.exclusiveDisjunction(a, b);
            }
        },

        IMPLIES(2){
            @Override
            public Boolean calculate(Boolean a, Boolean b) {
                return LogicCalculator.implication(a, b);
            }
        },

        IFF(1){
            @Override
            public Boolean calculate(Boolean a, Boolean b) {
                return LogicCalculator.biconditional(a, b);
            }
        },

        LPARENTHESIS(-1),
        RPARENTHESIS(-1);

        public final int precedence;

        TokenType(int precedence){
            this.precedence = precedence;
        }

        public Boolean calculate(Boolean a, Boolean b){
            throw new UnsupportedOperationException("Operation not supported for " + this);
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
        ArrayList<Token> operationPosFixa = toPosFixa(operationTokenized);
        operationPosFixa.reversed();

        TruthTable tt = new TruthTable(sub_getVARquantity(operationPosFixa));

        int contador = 0;
        Token atual = operationPosFixa.get(0);
        while(operationPosFixa.size()>1) {
            atual = operationPosFixa.get(contador);


            operationPosFixa.remove(contador);
            contador++;
        }

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
        return tokens;
    }

    //Algorítimo de Dijkstra | Shunting Yard Algorithm
    public static ArrayList<Token> toPosFixa(List<Token> tokens) {
        ArrayList<Token> output = new ArrayList<>();
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
    private static void sub_clearStack(Stack<Token> operator, ArrayList<Token> output){
        while(!operator.empty() &&  operator.peek().type != TokenType.LPARENTHESIS){
            output.add(operator.pop());
        }
    }

    private static boolean sub_validPrecedence(Stack<Token> operator, Token token) {
        return operator.peek().type.precedence > token.type.precedence;
    }

    private static int sub_getVARquantity(ArrayList<Token> tokens) {
        int quant = 0;
        for(Token token : tokens) {
            quant += (token.type == TokenType.VAR) ?  1 : 0;
        }
        return quant;
    }
}
