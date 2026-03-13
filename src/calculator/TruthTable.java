package calculator;

public class TruthTable {
    Boolean[][] table;
    int arguments;
    int lines;

    public TruthTable(int arguments) {
        this.arguments = arguments;
        this.lines = 1 << arguments;
        this.table = new Boolean[lines][arguments];

        for(int i = 0; i < lines; i++) {
            for(int j = arguments -1; j >= 0; j--) {
                table[i][j] = (i & (1 << j)) != 0;
            }
        }
    }

    public Boolean[][] getTable() {
        return table;
    }

    public boolean[] getColum(int columnIndex){
        boolean[] result = new boolean[lines];
        for(int j = 0; j < lines; j++){
            result[j] = table[j][columnIndex];
        }
        return result;
    }

    public int getArguments() {
        return arguments;
    }


    public int getLines() {
        return lines;
    }

    @Override
    public String toString() {
        String str = "";
        for (Boolean[] bools : table) {
            str += "\n";
            for (int j = arguments - 1; j >= 0; j--) {
                str += (bools[j] == true ? 1 : 0) + " ";
            }
        }

        return str;
    }
}
