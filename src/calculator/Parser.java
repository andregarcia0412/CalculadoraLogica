package calculator;

import java.util.List;


public class Parser {
    public static boolean isFBF(List<Token> tokens) {
        if (tokens.isEmpty()) return false;
        int parentheses = 0;
        TokenType previous = null;
        for (Token token : tokens) {
            switch (token.type) {
                case VAR: //variavel nao pode vir depois de outra variavel ou parentese
                    if (previous == TokenType.VAR ||
                            previous == TokenType.RPARENTHESIS)
                        return false;
                    break;
                case NOT: //negação nao pode vir depois de uma variavel ou parentese
                    if (previous == TokenType.VAR ||
                            previous == TokenType.RPARENTHESIS)
                        return false;
                    break;
                case AND: case OR: case XOR: case IMPLIES: case IFF: //Negação do conectivo na primeira posição e negacao de vir depois de parentese ou outro conectivo
                    if (previous == null ||
                            previous == TokenType.LPARENTHESIS ||
                            previous == TokenType.NOT ||
                            isConnective(previous))
                        return false;
                    break;
                case LPARENTHESIS: // '(' nao pode depois de uma variavel ou ')', dai incrementa o contador +1 para cada parentese inicial
                    if (previous == TokenType.VAR ||
                            previous == TokenType.RPARENTHESIS)
                        return false;
                    parentheses++;
                    break;
                case RPARENTHESIS: // "()" "~)" caso pra invalidar fórmulas vazias ou incompletas
                    if (previous == TokenType.LPARENTHESIS ||
                            previous == TokenType.NOT ||
                            isConnective(previous))
                        return false;
                    parentheses--;
                    if (parentheses < 0) return false;
                    break;
            }
            previous = token.type;
        } //verificacoes finais
        if (parentheses != 0) return false;
        if (previous == TokenType.NOT ||
                previous == TokenType.LPARENTHESIS ||
                isConnective(previous)) return false;
        return true;
    }
    private static boolean isConnective(TokenType type) {
        if (type == null) return false;
        return type == TokenType.AND ||
                type == TokenType.OR  ||
                type == TokenType.XOR ||
                type == TokenType.IMPLIES ||
                type == TokenType.IFF;
    } //metodo auxiliar p evitar repetição de codigo
}