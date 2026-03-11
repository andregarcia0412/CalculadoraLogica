package calculator;
import java.util.ArrayList;
import java.util.List;

public class Lexer {
    enum TokenType {
        VAR,        // A,B,C...
        NOT,        // ~
        AND,        // ^
        OR,         // v
        XOR,        // ⊻
        IMPLIES,    // →
        IFF,        // ↔
        LPARENTHESIS,     // (
        RPARENTHESIS      // )
    }

    public static class Token {
        TokenType type;
        String text;

        Token(TokenType type, String text) {
            this.type = type;
            this.text = text;
        }
    }

    public class StaticStack {
        private ArrayList<Token> stack = new ArrayList();
        public void push(Token o) {
            stack.add(o);
        }
        public Object pop() {
            return stack.remove(stack.size()-1);
        }
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
        return  tokens;
    }
}
