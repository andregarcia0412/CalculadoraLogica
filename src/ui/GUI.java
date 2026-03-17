    package ui;


    import calculator.Lexer;
    import calculator.Parser;
    import calculator.TruthTable;

    import javax.swing.*;
    import java.awt.*;
    import java.util.Arrays;

    public class GUI {
        private JFrame frame;
        private JPanel mainKeyboardPanel;
        private JPanel varsPanel;
        private JPanel opsPanel;

        private Button buttonA;
        private Button buttonB;
        private Button buttonC;
        private Button buttonD;
        private Button buttonE;
        private Button buttonF;
        private Button buttonConditional;
        private Button buttonBiconditional;
        private Button buttonExclusiveOr;
        private Button buttonNegation;
        private Button buttonAnd;
        private Button buttonOr;
        private Button buttonOpenParenthesis;
        private Button buttonCloseParenthesis;
        private Button buttonEquals;
        private Button buttonReturn;
        private Button ButtonClearAll;

        private JTextField textField;
        private String operationText = "";

        public GUI() {
            frame = new JFrame();
            textField = new JTextField(operationText);

            initializeButtons();

            Font font = new Font("Segoe UI Symbol", Font.BOLD, 40);
            setTextField(font);

            Dimension buttonDimension = new Dimension(80, 60);
            setButtonsSize(
                    buttonDimension,
                    buttonA,
                    buttonB,
                    buttonC,
                    buttonD,
                    buttonE,
                    buttonF,
                    buttonConditional,
                    buttonBiconditional,
                    buttonExclusiveOr,
                    buttonNegation,
                    buttonAnd,
                    buttonOr,
                    buttonOpenParenthesis,
                    buttonCloseParenthesis,
                    buttonEquals,
                    buttonReturn
            );

            setButtonsFont(
                    font,
                    buttonA,
                    buttonB,
                    buttonC,
                    buttonD,
                    buttonE,
                    buttonF,
                    buttonConditional,
                    buttonBiconditional,
                    buttonExclusiveOr,
                    buttonNegation,
                    buttonAnd,
                    buttonOr,
                    buttonOpenParenthesis,
                    buttonCloseParenthesis,
                    buttonEquals,
                    buttonReturn
            );

            setupPanels();

            setFrame();
        }

        private void setTextField(Font font) {
            textField.setFont(font);
            textField.setEditable(false);
            textField.setHorizontalAlignment(JTextField.RIGHT);
            textField.setPreferredSize(new Dimension(500, 80));
            textField.setBackground(Color.WHITE);
            textField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.GRAY,1),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
        }

        private void setupPanels(){
            mainKeyboardPanel = new JPanel();
            mainKeyboardPanel.setLayout(new BorderLayout(10, 10));
            mainKeyboardPanel.setBorder(BorderFactory.createEmptyBorder(50, 20, 50, 20));

            varsPanel = new JPanel();
            varsPanel.setLayout(new GridLayout(3, 2, 5, 5));
            varsPanel.add(buttonA);
            varsPanel.add(buttonB);
            varsPanel.add(buttonC);
            varsPanel.add(buttonD);
            varsPanel.add(buttonE);
            varsPanel.add(buttonF);

            opsPanel = new JPanel();
            opsPanel.setLayout(new GridLayout(4, 3, 5, 5));
            opsPanel.add(buttonOpenParenthesis);
            opsPanel.add(buttonCloseParenthesis);
            opsPanel.add(buttonReturn);

            opsPanel.add(buttonAnd);
            opsPanel.add(buttonOr);
            opsPanel.add(buttonNegation);

            opsPanel.add(buttonConditional);
            opsPanel.add(buttonBiconditional);
            opsPanel.add(buttonExclusiveOr);

            opsPanel.add(new JLabel(""));
            opsPanel.add(new JLabel(""));
            opsPanel.add(buttonEquals);

            mainKeyboardPanel.add(varsPanel, BorderLayout.WEST);
            mainKeyboardPanel.add(opsPanel, BorderLayout.CENTER);
        }

        private void setFrame(){
            frame.setLayout(new BorderLayout(0, 10));
            frame.add(textField, BorderLayout.NORTH);
            frame.add(mainKeyboardPanel, BorderLayout.CENTER);

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("Calculadora Lógica");
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }

        private void setButtonsFont(Font font, JButton... buttons) {
            for(JButton button: buttons) {
                button.setFont(font);
            }
        }

        private void setButtonsSize(Dimension dimension, JButton... buttons) {
            for(JButton button : buttons) {
                button.setPreferredSize(dimension);
            }
        }

        private void initializeButtons() {
            buttonA = new Button("A", () -> {
                operationText += "A";
                textField.setText(operationText);
            });
            buttonB = new Button("B", () -> {
                operationText += "B";
                textField.setText(operationText);
            });
            buttonC = new Button("C", () -> {
                operationText += "C";
                textField.setText(operationText);
            });
            buttonD = new Button("D", () -> {
                operationText += "D";
                textField.setText(operationText);
            });
            buttonE = new Button("E", () -> {
                operationText += "E";
                textField.setText(operationText);
            });
            buttonF = new Button("F", () -> {
                operationText += "F";
                textField.setText(operationText);
            });
            buttonConditional = new Button("→", () -> {
                operationText += "→";
                textField.setText(operationText);
            });
            buttonBiconditional = new Button("↔", () -> {
                operationText += "↔";
                textField.setText(operationText);
            });
            buttonExclusiveOr = new Button("⊻", () -> {
                operationText += "⊻";
                textField.setText(operationText);
            });
            buttonNegation = new Button("∼", () -> {
                operationText += "∼";
                textField.setText(operationText);
            });
            buttonAnd = new Button("∧", () -> {
                operationText += "∧";
                textField.setText(operationText);
            });
            buttonOr = new Button("∨", () -> {
                operationText += "∨";
                textField.setText(operationText);
            });
            buttonOpenParenthesis = new Button("(", () -> {
                operationText += "(";
                textField.setText(operationText);
            });
            buttonCloseParenthesis = new Button(")", () -> {
                operationText += ")";
                textField.setText(operationText);
            });
            buttonEquals = new Button("⊨", () -> {
                boolean valida = Parser.isFBF(Lexer.tokenize(operationText));
                if(!valida) {
                    JOptionPane.showMessageDialog(frame, "Não é uma FBF válida", "Resultado", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                TruthTable resposta = Lexer.solveToTruthTable(operationText);
                System.out.println(resposta);

                JTable table = createTable(resposta);
                Boolean[] lastColumn = resposta.getColum(resposta.getArguments());
                System.out.println(Arrays.toString(lastColumn));

                boolean hasTrueValues = false;
                boolean hasFalseValues = false;

                for (Boolean bool : lastColumn) {
                    if(bool) {
                        hasTrueValues = true;
                    } else{
                        hasFalseValues = true;
                    }
                }

                JOptionPane.showMessageDialog(frame, new JScrollPane(table), String.format("Tabela verdade (%s)", hasTrueValues && hasFalseValues ? "Contingência" : hasTrueValues ? "Tautologia" : "Contradição"), JOptionPane.INFORMATION_MESSAGE);

            });
            buttonReturn = new Button("⌫", () -> {
                if(!operationText.isEmpty()) {
                    operationText = operationText.substring(0, operationText.length()-1);
                    textField.setText(operationText);
                }
            });
        }

        private JTable createTable(TruthTable tt) {
            Boolean[][] table = tt.getTable();

            int rows = tt.getLines();
            int cols = tt.getArguments();

            Object[][] data = new Object[rows][cols];
            String[] columnNames = tt.getColumnNames();

            for(int i = 0; i < rows; i++) {
                for(int j = 0; j < cols; j++) {
                    data[i][j] = table[i][j] ? "T" : "F";
                }
            }

            JTable jt = new JTable(data, columnNames) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            jt.setRowHeight(25);
            jt.setPreferredScrollableViewportSize(new Dimension(1000, 500));
            return jt;
        }

    }
