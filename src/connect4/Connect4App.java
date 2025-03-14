package connect4;

import javax.swing.*;
import java.awt.*;

/**
 * Main application class for the Connect 4 game.
 * Sets up GUI window and initializes the game.
 * Configures the look and feel, and creates the main game window.

 * @author Martha Michael
 * @version 1.0
 */
public class Connect4App {
    public static void main(String[] args) {
        // Apply a consistent look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        JFrame frame = new JFrame("Connect 4");
        frame.setSize(500, 465);
        frame.setLocation(100, 100);
        frame.setContentPane(new Connect4Game());
        frame.setBackground(new Color(0, 0, 204));
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}