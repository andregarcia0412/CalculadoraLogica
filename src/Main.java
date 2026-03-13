import calculator.Lexer;
import calculator.LogicCalculator;
import calculator.TruthTable;
import ui.GUI;

public class Main {
    public static void main(String[] args) {
        TruthTable table = new TruthTable(4);
        System.out.println(table);

        TruthTable resposta = Lexer.solveToTruthTable("(p^q)→r");

        TruthTable table2 = new TruthTable(2);
        for(int i = 0; i < table2.getLines(); i++) {
            System.out.println(LogicCalculator.calculateConjunction(table2.getTable()[i][0], table2.getTable()[i][1]));
        }

        new GUI();

    }
}
