package connect4;

import connect4.ui.ButtonHandlers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Main game controller class for Connect 4 game.
 * This class manages the game state and coordinates between UI and game logic.
 * Sets up the game board setup, message display, player turns,
 * and serves as the central controller to connect all components.
 * 
 * 
 * @author Martha Michael
 * @version 1.0
 */
public class Connect4Game extends JPanel {
    private JButton[][] buttonArray;
    private JButton resetButton;
    private char[][] charArray;
    private JPanel buttonPanel, southButtons;
    private JLabel messageLabel;
    private char currentPlayer;
    private int rScore;
    private int yScore;
    
    // Game logic handler
    private GameLogic gameLogic;
    
    // UI handlers
    private ButtonHandlers.HoverEffect hoverEffect;
    private ButtonHandlers.ButtonPressed buttonPressed;
    private ButtonHandlers.ResetPressed resetPressed;
  
    public Connect4Game() {
        setLayout(new BorderLayout()); 
        
        // Initialize game state
        currentPlayer = 'R';  // Red starts first
        gameLogic = new GameLogic();
      
        setupGameBoard();
        setupMessageLabel();
        setupControlButtons();
    }
   
    private void setupGameBoard() {
        buttonArray = new JButton[6][7];
        charArray = new char[6][7];
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 7));
        
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                buttonArray[row][col] = new JButton("");
                buttonArray[row][col].setBackground(new Color(0, 0, 204));
                buttonArray[row][col].setOpaque(true);
                buttonArray[row][col].setBorderPainted(true);
                buttonArray[row][col].setContentAreaFilled(true);
                
                // Add event handlers
                hoverEffect = new ButtonHandlers.HoverEffect(row, col, buttonArray, charArray);
                buttonPressed = new ButtonHandlers.ButtonPressed(
                    row, col, buttonArray, charArray, this, gameLogic);
                
                buttonArray[row][col].addMouseListener(hoverEffect);
                buttonArray[row][col].addActionListener(buttonPressed);
                buttonPanel.add(buttonArray[row][col]); 
                charArray[row][col] = ' ';
                
                // Only enable bottom row initially
                if(row != 5) {
                    buttonArray[row][col].setEnabled(false);
                }
            }
        }       
        add(buttonPanel, BorderLayout.CENTER);
    }
    
    private void setupMessageLabel() {
        messageLabel = new JLabel("Red Goes First!");
        messageLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        messageLabel.setForeground(new Color(145, 95, 109));
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        messageLabel.setOpaque(true);
        messageLabel.setBackground(new Color(238, 238, 238));
        messageLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(messageLabel, BorderLayout.NORTH);
    }
    
    private void setupControlButtons() {
        JPanel southButtons = new JPanel();
        southButtons.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        resetButton = new JButton("Reset");
        resetPressed = new ButtonHandlers.ResetPressed(buttonArray, charArray, this);
        resetButton.addActionListener(resetPressed);
        resetButton.setPreferredSize(new Dimension(100, 30));
        resetButton.setFont(new Font("DialogInput", Font.BOLD, 14));
        resetButton.setFocusPainted(false);
        
        southButtons.add(resetButton);
        add(southButtons, BorderLayout.SOUTH);
    }
    
    // Getters and setters
    public char getCurrentPlayer() {
        return currentPlayer;
    }
    
    public void setCurrentPlayer(char player) {
        currentPlayer = player;
    }
    
    public JLabel getMessageLabel() {
        return messageLabel;
    }
    
    public void incrementScore(char player) {
        if (player == 'R') rScore++;
        else if (player == 'Y') yScore++;
    }
}