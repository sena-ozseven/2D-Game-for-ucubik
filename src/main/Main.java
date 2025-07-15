package main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("for ucubik");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack();
        //causes this window to be sized to fit the preferred size...
        //...and layouts of its subclasses (=GamePanel)

        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}