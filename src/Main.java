import calculator.Lexer;
import calculator.LogicCalculator;
import calculator.TruthTable;
import ui.GUI;

import java.util.List;


public class Main {
    public static void main(String[] args) {
        TruthTable table = new TruthTable(2);
        System.out.println(table);

        List<Lexer.Token> tokens = Lexer.tokenize("(p^q)→r");
        System.out.println(tokens);

        TruthTable table2 = new TruthTable(2);
        for(int i = 0; i < table2.getLines(); i++) {
            System.out.println(LogicCalculator.calculateConjunction(table2.getTable()[i][0], table2.getTable()[i][1]));
        }
        /*
        * GUI:
        *   - Adicionar meio de mostrar o resultado da tabela verdade
        * LogicCalculator:
        *   - Incrementar análise sintática (subclasse)
        * */

        new GUI();
    }
}
