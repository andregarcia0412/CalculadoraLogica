import calculator.Lexer;
import calculator.Token;
import calculator.TruthTable;
import ui.GUI;
import calculator.Parser;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        String[] testes = {
                "A^B",
                "(A→B)",
                "(~A^B)",
                "A→B→C",
                "A))^B",
                "A^^B",
                "→A",
                "A^",
        };

        for (String formula : testes) {
            List<Token> tokens = Lexer.tokenize(formula);
            boolean valida = Parser.isFBF(tokens);
            System.out.println(formula + " → " + (valida ? "FBF válida" : "NÃO é FBF"));
        }

        /*
        * GUI:
        *   - Adicionar meio de mostrar o resultado da tabela verdade
        * LogicCalculator:
        * */
        TruthTable resposta = Lexer.solveToTruthTable("(p^q)→r");
        Boolean[][] arr = resposta.getTable();

        new GUI();
    }
}
