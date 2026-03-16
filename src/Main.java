import calculator.Lexer;
import calculator.TruthTable;
import ui.GUI;

public class Main {
    public static void main(String[] args) {

        TruthTable resposta = Lexer.solveToTruthTable("(p^q)→r");
        Boolean[][] arr = resposta.getTable();
        imprimirMatriz(arr);

        new GUI();
    }

    public static void imprimirMatriz(Boolean[][] matriz) {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }

    }
}
