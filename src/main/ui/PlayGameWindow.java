package ui;

import javax.swing.*;

// Represents a Game Launching Window
public class PlayGameWindow {

    ImageIcon image = new ImageIcon("./data/loading.gif");
    JLabel label = new JLabel(image);

    PlayGameWindow(String gameName) {
        JFrame frame = new JFrame("Launching " + gameName);
        label.setVerticalTextPosition(JLabel.BOTTOM);
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);

        frame.add(label);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200,675);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
