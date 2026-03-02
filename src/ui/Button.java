package ui;

import javax.swing.*;

public class Button extends JButton {
    public Button(String symbol, Runnable action) {
        super(symbol);
        addActionListener(e -> action.run());
    }
}
