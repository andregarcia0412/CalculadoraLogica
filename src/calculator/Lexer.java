package calculator;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.LinkedHashMap;
import java.util.Map;

public class Lexer {
    public static List<Token> operation;
    //GPT
    public static TruthTable solveToTruthTable(String operationInfixa) {
        List<Token> infix = tokenize(operationInfixa);
        ArrayList<Token> postfix = toPosFixa(infix);
        operation = postfix;

        LinkedHashMap<Character, Integer> varIndex = new LinkedHashMap<>();
        for (Token token : infix) {
            if (token.type == TokenType.VAR && !varIndex.containsKey(token.text)) {
                varIndex.put(token.text, varIndex.size() + 1); // columns are 1-based
            }
        }

        int varCount = varIndex.size();
        char[] variables = new char[varCount];
        int i = 0;

        for(char c : varIndex.keySet()) {
            variables[i++] = c;
        }

        TruthTable tt = new TruthTable(variables);

        int[] idx = new int[]{postfix.size() - 1};
        evalPostfix(postfix, idx, tt, varIndex);
        return tt;
    }

    //GPT
    private static Token evalPostfix(
            ArrayList<Token> postfix,
            int[] idx,
            TruthTable tt,
            Map<Character, Integer> varIndex
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

            String expression = "(~" + buffer.expression + ")";

            Token resultToken = new Token(token.type, '#');
            resultToken.setTable(result);
            resultToken.expression = expression;

            tt.addColumn(result, expression);
            return resultToken;
        }

        Token b = evalPostfix(postfix, idx, tt, varIndex);
        Token a = evalPostfix(postfix, idx, tt, varIndex);
        Boolean[] result = new Boolean[a.getTable().length];
        for (int i = 0; i < result.length; i++) {
            result[i] = token.type.calculate(a.getTable(i), b.getTable(i));
        }

        String expression = "(" + a.expression + token.text + b.expression + ")";

        Token resToken = new Token(token.type, '#');
        resToken.setTable(result);
        resToken.expression = expression;
        tt.addColumn(result, expression);
        return resToken;
    }

    public static List<Token> tokenize(String input){
        List<Token> tokens = new ArrayList<>();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            switch (c){
                case '∼':
                    tokens.add(new Token(TokenType.NOT,'~'));
                    break;
                case '∧':
                    tokens.add(new Token(TokenType.AND,'^'));
                    break;
                case '∨':
                    tokens.add(new Token(TokenType.OR, 'v'));
                    break;
                case '⊻':
                    tokens.add(new Token(TokenType.XOR,'⊻'));
                    break;
                case '→':
                    tokens.add(new Token(TokenType.IMPLIES,'→'));
                    break;
                case '↔':
                    tokens.add(new Token(TokenType.IFF,'↔'));
                    break;
                case '(':
                    tokens.add(new Token(TokenType.LPARENTHESIS,'('));
                    break;
                case ')':
                    tokens.add(new Token(TokenType.RPARENTHESIS,')'));
                    break;

                default:
                    if(Character.isLetter(c)){
                        tokens.add(new Token(TokenType.VAR,c));
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

                case LPARENTHESIS:
                    operator.push(token);
                    break;

                case NOT, AND, OR, XOR, IMPLIES, IFF:
                    while(!operator.isEmpty() && sub_validPrecedence(operator,token)){
                        output.add(operator.pop());
                    }
                    operator.push(token);
                    break;
                case RPARENTHESIS:
                    while(!operator.isEmpty() && operator.peek().type != TokenType.LPARENTHESIS){
                        output.add(operator.pop());
                    }
                    if(!operator.isEmpty()){
                        operator.pop();
                    }
                    break;
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
        return operator.peek().type.precedence >= token.type.precedence;
    }
}
