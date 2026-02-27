public class Main {
    public static void main(String[] args) {
        TruthTable table = new TruthTable(3);
        System.out.println(table);

        System.out.println();

        TruthTable table2 = new TruthTable(2);
        for(int i = 0; i < table2.getLines(); i++) {
            System.out.println(LogicCalculator.calculateConjunction(table2.getTable()[i][0], table2.getTable()[i][1]));
        }
    }
}
