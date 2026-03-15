package calculator;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.LinkedHashMap;
import java.util.Map;

public class Lexer {
    public static List<Token> operation;
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
        private Boolean[] table;

        Token(TokenType type, String text) {
            this.type = type;
            this.text = text;
        }

        public Boolean[] getTable() {
            return table;
        }

        public Boolean getTable(int i) {
            return table[i];
        }

        public void setTable(Boolean[] table) {
            this.table = table;
        }
    }

    //GPT
    public static TruthTable solveToTruthTable(String operationInfixa) {
        List<Token> infix = tokenize(operationInfixa);
        ArrayList<Token> postfix = toPosFixa(infix);
        operation = postfix;

        LinkedHashMap<String, Integer> varIndex = new LinkedHashMap<>();
        for (Token token : infix) {
            if (token.type == TokenType.VAR && !varIndex.containsKey(token.text)) {
                varIndex.put(token.text, varIndex.size() + 1); // columns are 1-based
            }
        }

        int varCount = varIndex.size();
        TruthTable tt = new TruthTable(varCount);

        int[] idx = new int[]{postfix.size() - 1};
        Token resultToken = evalPostfix(postfix, idx, tt, varIndex);
        tt.addColumn(resultToken.getTable());
        return tt;
    }

    //GPT
    private static Token evalPostfix(
            ArrayList<Token> postfix,
            int[] idx,
            TruthTable tt,
            Map<String, Integer> varIndex
    ) {
        Token token = postfix.get(idx[0]--);

        if (token.type == TokenType.VAR) {
            if (token.getTable() == null) {
                Integer column = varIndex.get(token.text);
                token.setTable(tt.getColum(column));
            }
            return token;
        }

        if (token.type == TokenType.NOT) {
            Token buffer = evalPostfix(postfix, idx, tt, varIndex);
            Boolean[] result = new Boolean[buffer.getTable().length];
            for (int i = 0; i < result.length; i++) {
                result[i] = token.type.calculate(buffer.getTable(i), null);
            }
            Token resultToken = new Token(token.type, "tmp");
            resultToken.setTable(result);
            return resultToken;
        }

        Token b = evalPostfix(postfix, idx, tt, varIndex);
        Token a = evalPostfix(postfix, idx, tt, varIndex);
        Boolean[] result = new Boolean[a.getTable().length];
        for (int i = 0; i < result.length; i++) {
            result[i] = token.type.calculate(a.getTable(i), b.getTable(i));
        }
        Token resToken = new Token(token.type, "tmp");
        resToken.setTable(result);
        return resToken;
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

    private static int sub_varCount(ArrayList<Token> tokens) {
        return (int) tokens.stream()
                .filter(t -> t.type == TokenType.VAR)
                .map(t -> t.text)
                .distinct()
                .count();
    }

    private static int sub_operatorCount(ArrayList<Token> tokens) {
        int quant = 0;
        for(Token token : tokens) {
            quant += ((token.type != TokenType.VAR)&&(token.type != TokenType.LPARENTHESIS)&&((token.type != TokenType.RPARENTHESIS))) ?  1 : 0;
        }
        return quant;
    }

    private static ArrayList<Token> parseOperation(String operationInfixa) {
        operation = tokenize(operationInfixa);
        return toPosFixa(operation);
    }
}
