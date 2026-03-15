package calculator;

import java.util.Arrays;

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

    public Boolean[] getColum(int columnIndex){
        Boolean[] result = new Boolean[lines];
        for(int j = 0; j < lines; j++){
            result[j] = table[j][columnIndex-1];
        }
        return result;
    }

    public void addColumn(Boolean[] truthTable){
        Boolean[][] copy = new  Boolean[lines][truthTable.length];

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
