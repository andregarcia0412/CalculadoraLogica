package calculator;

public class TruthTable {
    Boolean[][] table;
    int arguments;
    int lines;
    final int baseArguments;
    String[] columnNames;

    public TruthTable(char[] variables) {
        this.arguments = variables.length;
        this.baseArguments = arguments;
        this.lines = 1 << arguments;
        this.table = new Boolean[lines][arguments];

        this.columnNames = new String[arguments];

        for(int i = 0; i < arguments; i++) {
            columnNames[i] = String.valueOf(variables[i]);
        }

        for(int i = 0; i < lines; i++) {
            for(int j = arguments -1; j >= 0; j--) {
                table[i][j] = (i & (1 << j)) != 0;
            }
        }
    }

    public Boolean[][] getTable() {
        Boolean[][] view = new Boolean[lines][arguments];
        for (int i = 0; i < lines; i++) {
            System.arraycopy(table[i], 0, view[i], 0, arguments);
        }

        // reflect rows
        for (int i = 0; i < lines / 2; i++) {
            Boolean[] temp = view[i];
            view[i] = view[lines - 1 - i];
            view[lines - 1 - i] = temp;
        }

        // reflect only the original variable columns
        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < baseArguments / 2; j++) {
                Boolean temp = view[i][j];
                view[i][j] = view[i][baseArguments - 1 - j];
                view[i][baseArguments - 1 - j] = temp;
            }
        }

        return view;
    }

    public Boolean[] getColum(int columnIndex){
        Boolean[] result = new Boolean[lines];
        for(int j = 0; j < lines; j++){
            result[j] = table[j][columnIndex-1];
        }
        return result;
    }

    public void addColumn(Boolean[] truthTable, String name){
        if (truthTable == null || truthTable.length != lines) {
            throw new IllegalArgumentException("Column size must match number of lines.");
        }

        Boolean[][] copy = new Boolean[lines][arguments + 1];
        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < arguments; j++) {
                copy[i][j] = table[i][j];
            }
            copy[i][arguments] = truthTable[i];
        }

        table = copy;

        String[] newNames = new String[arguments + 1];
        System.arraycopy(columnNames, 0, newNames, 0, arguments);

        newNames[arguments] = name;

        columnNames = newNames;
        arguments++;
    }

    public int getArguments() {
        return arguments;
    }


    public int getLines() {
        return lines;
    }

    public String[] getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }

    @Override
    public String toString() {

        Boolean[][] view = getTable();
        StringBuilder str = new StringBuilder();
        for (Boolean[] bools : view) {
            str.append("\n");
            for (int j = 0; j < arguments; j++) {
                str.append(bools[j] == true ? 1 : 0).append(" ");
            }
        }

        return str.toString();
    }

}
