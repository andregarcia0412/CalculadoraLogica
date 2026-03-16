import calculator.Lexer;
import calculator.TruthTable;
import ui.GUI;

public class Main {
    public static void main(String[] args) {
        TruthTable resposta = Lexer.solveToTruthTable("(p^q)→r");
        Boolean[][] arr = resposta.getTable();

        new GUI();
    }
}
