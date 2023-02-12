import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

/**
 * @category Project_04
 * In this project, we will design a checkerboard graphical user interface (GUI), 
 * not necessarily functional, that can draw a board along with the checker pieces on that board.
 * @author Haoran Chen
 * @create: May 8, 2021
 */

public class CP extends JComponent{

	/**
	 * Instance variables
	 * @param row
	 * @param colum
	 * @param heightOfSquare
	 * @param widthOfCheckers
	 * @param status 
	 */
	private int row, column, heightOfSquare, widthOfCheckers;
	private char status;
	

	/**
	 * Workhorse constructor 
	 * @param row
	 * @param column
	 * @param status
	 * @throws IllegalCheckerboardArgumentException
	 */
	public CP(int row, int column, char status) throws IllegalCheckerboardArgumentException{
		
		 this.row = row;
		 this.column = column;
		 this.status = status;
	     
	     if((row > 0 && row < 7) && (column > 0 && column < 7)) 
	    	 if(status != 'e' || status != 'b' || status != 'r')
	    		 throw new IllegalCheckerboardArgumentException("Invalid square.");
	    	 if((column + row) % 2 == 0 && status != 'e')
		    	 throw new IllegalCheckerboardArgumentException("Invalid square.");
	     else
	    	 throw new IllegalCheckerboardArgumentException("Invalid row or column.");
	}
	
	
	/**
	 * instance method
	 */
	@Override
	public void paintComponent(Graphics g){
		
		//set up the value of height and width of the squares and checkers
		this.heightOfSquare = 60;
		this.widthOfCheckers = 40;
	
		
		//create and fill color with squares 
		if ((column + row) % 2 == 1)
            g.setColor(Color.green);
        else
            g.setColor(Color.white);
		
		
		//set up the color of each checkers
		if(status == 'b'){
			g.setColor(Color.black);
		}else if(status == 'r'){
			g.setColor(Color.red);
		}else if(status == 'e'){
			g.setColor(Color.black);
		}
		
		g.fillRect(0, 0, heightOfSquare, heightOfSquare);
		g.setColor(Color.black);
		g.drawRect(0, 0, heightOfSquare, heightOfSquare);
		
		g.fillOval(10, 10, widthOfCheckers, widthOfCheckers);
		g.setColor(Color.black);
		g.fillOval(10, 10, widthOfCheckers, widthOfCheckers);

	}
}