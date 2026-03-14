package calculator;

import java.util.List;

public class Parser {
    public static boolean isFBF(List<Lexer.Token> tokens) {
        if (tokens.isEmpty()) return false;
        int parentheses = 0;
        Lexer.TokenType previous = null;
        for (Lexer.Token token : tokens) {
            switch (token.type) {
                case VAR: //variavel nao pode vir depois de outra variavel ou parentese
                    if (previous == Lexer.TokenType.VAR ||
                            previous == Lexer.TokenType.RPARENTHESIS)
                        return false;
                    break;
                case NOT: //negação nao pode vir depois de uma variavel ou parentese
                    if (previous == Lexer.TokenType.VAR ||
                            previous == Lexer.TokenType.RPARENTHESIS)
                        return false;
                    break;
                case AND: case OR: case XOR: case IMPLIES: case IFF: //Negação do conectivo na primeira posição e negacao de vir depois de parentese ou outro conectivo
                    if (previous == null ||
                            previous == Lexer.TokenType.LPARENTHESIS ||
                            previous == Lexer.TokenType.NOT ||
                            isConnective(previous))
                        return false;
                    break;
                case LPARENTHESIS: // '(' nao pode depois de uma variavel ou ')', dai incrementa o contador +1 para cada parentese inicial
                    if (previous == Lexer.TokenType.VAR ||
                            previous == Lexer.TokenType.RPARENTHESIS)
                        return false;
                    parentheses++;
                    break;
                case RPARENTHESIS: // "()" "~)" caso pra invalidar fórmulas vazias ou incompletas
                    if (previous == Lexer.TokenType.LPARENTHESIS ||
                            previous == Lexer.TokenType.NOT ||
                            isConnective(previous))
                        return false;
                    parentheses--;
                    if (parentheses < 0) return false;
                    break;
            }
            previous = token.type;
        } //verificacoes finais
        if (parentheses != 0) return false;
        if (previous == Lexer.TokenType.NOT ||
                previous == Lexer.TokenType.LPARENTHESIS ||
                isConnective(previous)) return false;
        return true;
    }
    private static boolean isConnective(Lexer.TokenType type) {
        if (type == null) return false;
        return type == Lexer.TokenType.AND ||
                type == Lexer.TokenType.OR  ||
                type == Lexer.TokenType.XOR ||
                type == Lexer.TokenType.IMPLIES ||
                type == Lexer.TokenType.IFF;
    } //metodo auxiliar p evitar repetição de codigo
}