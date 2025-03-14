package connect4.ui;

import connect4.Connect4Game;
import connect4.GameLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Main event handler class for the Connect 4 game.
 * This class manages user interactions and coordinates with the game logic and UI.
 * Provides functionality for:
 * - Visual feedback with hover effects
 * - Processing player moves and updating the game state
 * - Resetting the game board for a new round
 * 
 * @author Martha Michael
 * @version 1.0
 */

public class ButtonHandlers {
    /**
     * Handles hover effects for the game buttons.
     * 
     * This class provides visual feedback to guide player interactions:
     * - Changes the button color to white when the mouse enters (indicating a valid cell for the next move)
     * - Restores the appropriate color when the mouse exits (blue for empty, red or yellow for occupied cells)
     */
    public static class HoverEffect extends MouseAdapter {
        private int row;
        private int col;
        private Color originalColor;
        private JButton[][] buttonArray;
        private char[][] charArray;
        
        public HoverEffect(int r, int c, JButton[][] buttons, char[][] chars) {
            row = r;
            col = c;
            buttonArray = buttons;
            charArray = chars;
        }
        
        @Override
        public void mouseEntered(MouseEvent e) {
            JButton button = (JButton)e.getSource();
            if (button.isEnabled()) {
                originalColor = button.getBackground();
                button.setBackground(Color.WHITE);
                button.repaint();
            }
        }
        
        @Override
        public void mouseExited(MouseEvent e) {
            JButton button = (JButton)e.getSource();
            if (button.isEnabled()) {
                if (charArray[row][col] == ' ') {
                    button.setBackground(new Color(0, 0, 204)); // Return to blue if empty
                } else if (charArray[row][col] == 'R') {
                    button.setBackground(Color.RED);
                } else if (charArray[row][col] == 'Y') {
                    button.setBackground(Color.YELLOW);
                }
                button.repaint();
            }
        }
    }
    
    /**
     * Handles button click events for the game board.
     * 
     * This class processes player moves and implements the core game flow:
     * - Places player tokens on the board when a column is selected
     * - Updates the game board's visual state to show the move
     * - Switches player turns
     * - Checks for win/draw conditions after each move
     */
    public static class ButtonPressed implements ActionListener {
        private int row;
        private int col;
        private JButton[][] buttonArray;
        private char[][] charArray;
        private Connect4Game game;
        private GameLogic gameLogic;
        
        public ButtonPressed(int r, int c, JButton[][] buttons, char[][] chars, 
                            Connect4Game gameRef, GameLogic logic) {
            row = r;
            col = c;
            buttonArray = buttons;
            charArray = chars;
            game = gameRef;
            gameLogic = logic;
        }         
        
        public void actionPerformed(ActionEvent e) {
            buttonArray[row][col].setEnabled(false);
            
            // Enable the button above this one if it exists
            if (row-1 >= 0) {
                buttonArray[row-1][col].setEnabled(true);
                buttonArray[row-1][col].setBackground(new Color(0, 0, 204));
                buttonArray[row-1][col].repaint();
            }
            
            // Get current player before making the move
            char currentPlayer = game.getCurrentPlayer();
            
            // Make move for current player
            if (currentPlayer == 'R') {
                charArray[row][col] = 'R';
                buttonArray[row][col].setBackground(Color.RED);
                buttonArray[row][col].setOpaque(true);
                buttonArray[row][col].setBorderPainted(true);
                buttonArray[row][col].repaint();
                game.setCurrentPlayer('Y'); 
                game.getMessageLabel().setText("Yellow's Turn");
            }
            else { 
                charArray[row][col] = 'Y'; 
                buttonArray[row][col].setBackground(Color.YELLOW);
                buttonArray[row][col].setOpaque(true);
                buttonArray[row][col].setBorderPainted(true);
                buttonArray[row][col].repaint();
                game.setCurrentPlayer('R');
                game.getMessageLabel().setText("Red's Turn");
            }
            
            // The player who just moved (meaning a player who just played their turn)
            char playerJustMoved = (currentPlayer == 'R') ? 'Y' : 'R';
            
            // Check for win
            boolean isWinner = gameLogic.winner(playerJustMoved, charArray);
            
            if (isWinner) {
                if (playerJustMoved == 'R') {
                    game.getMessageLabel().setText("Winner: Red");
                    game.incrementScore('R');
                    disableAllButtons();
                }
                if (playerJustMoved == 'Y') {
                    game.getMessageLabel().setText("Winner: Yellow");
                    game.incrementScore('Y');
                    disableAllButtons();
                }            
            }
            
            // Check for draw if no winner
            if (!isWinner && gameLogic.draw(playerJustMoved, charArray)) {
                game.getMessageLabel().setText("Draw!");
            }
        }
        
        private void disableAllButtons() {
            for (int r = 0; r < 6; r++) {
                for (int c = 0; c < 7; c++) {
                    buttonArray[r][c].setEnabled(false);
                }
            }
        }
    }
    
    /**
     * Handles the reset button click event.
     * 
     * This class implements the functionality to start a new game:
     * - Clears the game board (visually and internal data structure)
     * - Resets all buttons to their default state
     * - Enables only the bottom row of buttons (as per Connect 4 rules)
     * - Resets the player turn to Red (first player)
     */
    public static class ResetPressed implements ActionListener {
        private JButton[][] buttonArray;
        private char[][] charArray;
        private Connect4Game game;
        
        public ResetPressed(JButton[][] buttons, char[][] chars, Connect4Game gameRef) {
            buttonArray = buttons;
            charArray = chars;
            game = gameRef;
        }
        
        public void actionPerformed(ActionEvent e) {
            for (int row = 0; row < 6; row++) {
                for (int col = 0; col < 7; col++) {
                    buttonArray[row][col].setBackground(new Color(0, 0, 204));
                    buttonArray[row][col].setOpaque(true);
                    buttonArray[row][col].setBorderPainted(true);
                    buttonArray[row][col].repaint();
                    charArray[row][col] = ' ';
                    
                    // Only enable bottom row
                    buttonArray[row][col].setEnabled(row == 5);
                    
                    if (row == 5) {
                        buttonArray[row][col].setBackground(new Color(0, 0, 204));
                    }
                }
            } 
            
            // Reset to initial game state
            game.setCurrentPlayer('R'); // Always start with Red after reset
            game.getMessageLabel().setText("Red Goes First!");
            game.getMessageLabel().setFont(new Font("SansSerif", Font.BOLD, 28));
        }        
    }
}