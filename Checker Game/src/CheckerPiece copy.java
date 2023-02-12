import java.awt.*;
import javax.swing.JComponent;

/**
 * @category Project_04
 * In this project, we will design a checkerboard graphical user interface (GUI), 
 * not necessarily functional, that can draw a board along with the checker pieces on that board.
 * @author Haoran Chen
 * @create: April 27, 2021
 */

public class CheckerPiece extends JComponent{
	
	/**
	 * @param instance variables 
	 */
    private char status;
    private int row;
    private int column;
    private final int SIDE = 60;
    private final int RADIUS = 40;
    private boolean capturable;
    private boolean moveable;

    
	/**
	 * @param workhorse constructor 
	 */
    public CheckerPiece(int row, int column, char status) throws IllegalCheckerboardArgumentException{
        
    	if (row < 0 || row > 7)
    		throw new IllegalCheckerboardArgumentException(" invalid row ");
        
    	if (column < 0 || column > 7)
    		throw new IllegalCheckerboardArgumentException(" invalid column ");
 
    	if (status != 'r' & status != 'b' & status != 'e' & status != 'k' & status != 'q')    
    		throw new IllegalCheckerboardArgumentException(" invalid square ");
    	 
    	else if ((column + row) % 2 == 0 & status != 'e')
    		throw new IllegalCheckerboardArgumentException(" invalid square ");
        
        if ((column + row) % 2 == 1 & status == 'e' & (row < 3 || row > 4))  
        	throw new IllegalCheckerboardArgumentException(" invalid square ");
        
        this.row = row;
        this.column = column;
        this.status = status;
    }

    
    /**
     *@param instance method 
     */
    @Override
    public void paintComponent(Graphics g){
    	
    	//Set the withe and green color for board 
        if ((column + row) % 2 == 0)
            g.setColor(Color.WHITE);
        else
            g.setColor(Color.GREEN);
        
        //Set the color of pieces 
        g.fillRect(0, 0, SIDE, SIDE);
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, SIDE, SIDE);

        //Set up the status of each blank
        if (status == 'e')
            return;
        if (status == 'b' || status == 'k') 
            g.setColor(Color.BLACK);
        if (status == 'r' || status == 'q')
            g.setColor(Color.RED);

        g.fillOval(10, 10, RADIUS, RADIUS);
        g.setColor(Color.BLACK);
        
        if (this.isKing()) 
            g.setColor(Color.YELLOW);
        
        g.drawOval(10, 10, RADIUS, RADIUS);
    }

    
    /**
     * @param instance method
     * @return boolean
     */
    public boolean isKing() {
    	
        if (status == 'b' & row == 7){

        	status = 'k';
            return true;
        }
       
        if (status == 'r' & row == 0){
        	
            status = 'q';
            return true;
        }
       
        if (status == 'k' || status == 'q'){
            
        	return true;
        }
        return false;
    }

    
 
    /**
     * @param turnBlack
     * Checks if a CheckerPiece object is valid to be moved.
     * @return boolean
     */
    public int validToBeMoved(boolean turnBlack){
    	
        if (status == 'e'){
            return 2;
        }
        
        if (turnBlack && (status == 'r' || status == 'q')){
            return 3;
        }
        
        if (!turnBlack && (status == 'b' || status == 'k')){
            return 4;
        }
        return 1;
    }

    
    /**
     * @param tbm
     * @param boardStatus
     * Checks if a CheckerPiece object is valid to be moved to the destination.
     * @return int
     */
    public int validDestination(CheckerPiece tbm, char[][] boardStatus){
    	
        if (status != 'e'){
            return 2;
        }
        
        if ((row + column) % 2 == 0) {
            return 3;
        }
       
        if (tbm.getRow() == this.row){
            return 4;
        }
       
        if (!tbm.isKing() && !isForward(tbm)){
            return 5;
        }
       
        if (!isACapture(tbm, boardStatus) && !isValidMove(tbm)){
            return 6;
        }
        return 1;
    }

    
    /**
     * @param tbm
     * @param boardStatus
     * Checks if the current checker is capturing another checker or not
     * @return boolean
     */
    public boolean isACapture(CheckerPiece tbm, char[][] boardStatus){
        if (Math.abs(row - tbm.getRow()) != 2){
            return false;
        }
        
        if (Math.abs(column - tbm.getColumn()) != 2){
            return false;
        }
        
        char mid = boardStatus[(tbm.getRow() + row) / 2][(tbm.getColumn() + column) / 2];
        
        if (mid == status || mid == 'e'){
            return false;
        }
        return true;
    }

    
    /**
     * @param tbm
     * Checks if the move being made is valid or not (without capturing)
     * @return boolean
     */
    private boolean isValidMove(CheckerPiece tbm){
    	
        if (Math.abs(tbm.getRow() - row) != 1){
            return false;
        }
        
        if (Math.abs(tbm.getColumn() - column) != 1){
            return false;
        }
        return true;
    }

    
    /**
     * @param tbm
     * Checks if the destination is considered as forward comparing to the checker that will be moved
     * @return boolean
     */
    public boolean isForward(CheckerPiece tbm){
        
    	if (tbm.getStatus() == 'b' && row > tbm.getRow()){
            return true;
        }
       
    	if (tbm.getStatus() == 'r' && row < tbm.getRow()) {
            return true;
        }
        return false;
    }

    
    /**
     * @param instance method
     */
    @Override
    public String toString(){
        return "CheckerPiece [status= " + status + 
        		", row= " + row + 
        		", col= " + column + 
        		", capturable= " + capturable
                + ", moveable= " + moveable + "]";
    }
    
    
    /**
	 * @param Status the Status to get
	 * @return char
	 */
	public char getStatus(){
		
        return status;
    }

    /**
	 * @param Status the Status to set
	 */
    public void setStatus(char status){
    	
        this.status = status;
        repaint();
    }

    /**
	 * @param Row the Row to get
	 * @return int
	 */
	public int getRow(){
		
        return row;
    }

    /**
	 * @param column the column to get
	 * @return int
	 */
    public int getColumn() 
    {
        return column;
    }

    /**
	 * @param isCapturable
	 * @return boolean
	 */
    public boolean isCapturable(){
    	
        return capturable;
    }

    /**
	 * @param Capturable the Capturable to set
	 */
    public void setCapturable(boolean capturable){
    	
        this.capturable = capturable;
    }

    
    /**
	 * @param isMoveable
	 * @return boolean
	 */
    public boolean isMoveable(){
    	
        return moveable;
    }

    
    /**
	 * @param Moveable the Moveable to set
	 */
    public void setMoveable(boolean moveable){
        
    	this.moveable = moveable;
    }
}