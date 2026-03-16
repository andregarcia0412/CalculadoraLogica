package ui;


import calculator.Lexer;
import calculator.TruthTable;

import javax.swing.*;
import java.awt.*;

public class GUI {
    private JFrame frame;
    private JPanel panel;

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

    private JTextField textField;
    private String operationText = "";

    public GUI() {
        frame = new JFrame();
        panel = new JPanel();
        textField = new JTextField(operationText);

        initializeButtons();

        Font font = new Font("Segoe UI Symbol", Font.BOLD, 40);
        setTextField(font);

        Dimension buttonDimension = new Dimension(150, 100);
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

        setPanel();

        addButtonsToPanel(
                panel,
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

        setFrame();
    }

    private void setTextField(Font font) {
        textField.setFont(font);
        textField.setEditable(false);
    }

    private void setPanel(){
        panel.setBorder(BorderFactory.createEmptyBorder(300, 250, 300, 250));
        panel.setLayout(new GridLayout(4, 3));
    }

    private void setFrame(){
        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Calculadora Lógica");
        frame.setResizable(false);
        frame.add(textField, BorderLayout.NORTH);
        frame.pack();
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

    private void addButtonsToPanel(JPanel panel, JButton... buttons) {
        for(JButton button : buttons) {
            panel.add(button);
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
        buttonNegation = new Button("~", () -> {
            operationText += "~";
            textField.setText(operationText);
        });
        buttonAnd = new Button("^", () -> {
            operationText += "^";
            textField.setText(operationText);
        });
        buttonOr = new Button("v", () -> {
            operationText += "v";
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
            //Tiago, pode inserir algo aqui.
            TruthTable resposta = Lexer.solveToTruthTable(operationText);
            System.out.println(resposta);
        });
        buttonReturn = new Button("-", () -> {
            if(!operationText.isEmpty()) {
                operationText = operationText.substring(0, operationText.length()-1);
                textField.setText(operationText);
            }
        });
    }

}
