package calculator;


public class Token {
    TokenType type;
    char text;
    private Boolean[] table;
    String expression;

    Token(TokenType type, char text) {
        this.type = type;
        this.text = text;
        this.expression = String.valueOf(text);
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