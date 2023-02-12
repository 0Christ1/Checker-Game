import java.awt.GridLayout;

import javax.swing.JPanel;

/**
 * @category Project_04
 * In this project, we will design a checkerboard graphical user interface (GUI), 
 * not necessarily functional, that can draw a board along with the checker pieces on that board.
 * @author Haoran Chen
 * @create: May 8, 2021
 */

public class CB extends JPanel{

	/**
	 * @parm boardStatus
	 * @param ROW
	 * @param COLUMN
	 */
	private char[][]boardStatus;
	private final int ROW = 8;
	private final int COLUMN = 8;
	
	
	/**
	 * @param boardStatus
	 * @throws IllegalCheckerboardArgumentException
	 */
	public CB(char[][] boardStatus) throws IllegalCheckerboardArgumentException {
		
		this.boardStatus = new char[ROW][COLUMN];
		this.setLayout(new GridLayout(ROW, COLUMN));
	}


	/**
	 * @param boardStatus the boardStatus to set
	 */
	public void setBoardStatus(char[][] boardStatus) {
		this.boardStatus = boardStatus;
		repaint();
	}

	
	/**
	 * the checker piece to set 
	 * @param row
	 * @param column
	 * @param status
	 */
	public void setCheckerPiece(int row, int column, char status) {
		status = boardStatus[row][column];
		repaint();
	}

	
	/**
	 * @return the boardStatus
	 */
	public char[][] getBoardStatus() {
		return boardStatus;
	}
}