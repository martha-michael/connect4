package connect4;

/**
 * Core Game Logic for Connect 4 game.
 * This class captures the rules of the game and provides methods to the following:
 * - Check for win conditions (horizontal, vertical, and diagonal)
 * - Detect draw situations (full board with no winner)
 * - Validate game state
 * 
 * @author Martha Michael
 * @version 1.0
 */
public class GameLogic {
    /**
     * Checks if the specified player has won the game.
     * 
     * @param player The player to check for a win ('R' or 'Y')
     * @param matrix The game board matrix
     * @return true if the player has won, otherwise false
     */
    public boolean winner(char player, char[][] matrix) {
        // Check for horizontal wins
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 4; col++) {
                if (matrix[row][col] == player && 
                    matrix[row][col+1] == player && 
                    matrix[row][col+2] == player && 
                    matrix[row][col+3] == player) {
                    return true;
                }
            }
        } 
        
        // Check for vertical wins
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 7; col++) {
                if (matrix[row][col] == player && 
                    matrix[row+1][col] == player && 
                    matrix[row+2][col] == player && 
                    matrix[row+3][col] == player) {
                    return true;
                }
            }
        }
        
        // Check for diagonal wins (down-right)
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 4; col++) {
                if (matrix[row][col] == player && 
                    matrix[row+1][col+1] == player && 
                    matrix[row+2][col+2] == player && 
                    matrix[row+3][col+3] == player) {
                    return true;
                }
            }
        }
        
        // Check for diagonal wins (up-right)
        for (int row = 3; row < 6; row++) {
            for (int col = 0; col < 4; col++) {
                if (matrix[row][col] == player && 
                    matrix[row-1][col+1] == player && 
                    matrix[row-2][col+2] == player && 
                    matrix[row-3][col+3] == player) {
                    return true;
                }
            }
        }   
        
        return false;
    }
   
    /**
     * Checks if the game is a draw.
     * 
     * @param player The current player
     * @param matrix The game board matrix
     * @return true if the game is a draw, otherwise false
     */
    public boolean draw(char player, char[][] matrix) {
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                if (matrix[row][col] == ' ') {
                    return false; 
                }
            }
        }
        return true; 
    }
}