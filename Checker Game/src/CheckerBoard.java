import java.awt.Component;
import java.awt.GridLayout;
import javax.swing.JPanel;

/**
 * @category Project_04
 * In this project, we will design a checkerboard graphical user interface (GUI), 
 * not necessarily functional, that can draw a board along with the checker pieces on that board.
 * @author Haoran Chen
 * @create: April 27, 2021
 */

public class CheckerBoard extends JPanel{

	
	/**
	 * @param instance variables 
	 */
    private int black;
    private int red;
    private int blackCanCapture;
    private int redCanCapture;
    private char[][] boardStatus;

    
	/**
	 * @param workhorse constructor 
	 */
    public CheckerBoard(char[][] boardStatus) throws IllegalCheckerboardArgumentException {
       
    	this.black = 12;
        this.red = 12;
        this.boardStatus = new char[8][8];
        this.setLayout(new GridLayout(8, 8));
        
        for (int i = 0; i < boardStatus.length; i++){
            for (int j = 0; j < boardStatus[i].length; j++){
            	
                this.boardStatus[i][j] = boardStatus[i][j];
                add(new CheckerPiece(i, j, boardStatus[i][j]));
            }
        }
        setCheckersState();
    }

    
    /**
     *@param instance method 
     */
    public void setBoardStatus(char[][] boardStatus){
    	
        this.boardStatus = boardStatus;
        repaint();
    }

    /**
     *@param instance method 
     *Sets the status of a checker square
     */
    public void setCheckerPiece(int row, int column, char status){
    	
        boardStatus[row][column] = status;
        ((CheckerPiece) this.getComponent(row * 8 + column)).setStatus(status);
        repaint();
    }

   
    /**
     * @return char
     */
    public char[][] getBoardStatus(){
    	
        return boardStatus;
    }


    /**
     * Gets the CheckerPiece object using the row and column of that checker square   
     * @param row
     * @param column
     * @return
     */
    public CheckerPiece getCheckerPiece(int row, int column){
    	
        return ((CheckerPiece) this.getComponent(row * 8 + column));
    }

    
     /**
     * @param boardStatus
     * Resets the entire checker board back to the initial state
     */
    public void reset(char[][] boardStatus){
        black = 12;
        red = 12;
        for (int i = 0; i < boardStatus.length; i++){
        	
            for (int j = 0; j < boardStatus[i].length; j++) {
            	
                //Changes the status of the square if it's not the same as the beginning
                if (boardStatus[i][j] != this.boardStatus[i][j]){
                	
                    setCheckerPiece(i, j, boardStatus[i][j]);
                }
            }
        }
        setCheckersState();
    }

    
    /**
     * @return boolean
     * Checks if any more moves can be made
     */
    public boolean notMoveable(){
    	
        if (black == 0){
            return true;
        }
        
        if (red == 0){
            return true;
        }
        
        int redMoveable = 0, blackMoveable = 0;
        
        for (Component c : getComponents()){
        	
            CheckerPiece cp = (CheckerPiece) c;
            
            if (cp.isMoveable()) 
            	
                if (cp.getStatus() == 'b' || cp.getStatus() == 'k')
                    blackMoveable++;
                
                if (cp.getStatus() == 'r' || cp.getStatus() == 'q') 
                    redMoveable++;
            
        }
        return redMoveable == 0 || blackMoveable == 0;
    }

    

    /**
     * Sets the captureable and moveable fields of every CheckePiece object
     */
    public void setCheckersState(){
    	
        blackCanCapture = 0;
        redCanCapture = 0;
        
        for (Component c : getComponents()){
        	
            CheckerPiece cp = (CheckerPiece) c;
            cp.setCapturable(captureable(cp.getRow(), cp.getColumn()));
            cp.setMoveable(moveable(cp.getRow(), cp.getColumn()));
            
            if ((cp.getStatus() == 'b' || cp.getStatus() == 'k') && cp.isCapturable()) 
                blackCanCapture++;
            
            if ((cp.getStatus() == 'r' || cp.getStatus() == 'q') && cp.isCapturable())
                redCanCapture++;
        }
    }

  
  
    /**
     * @param row
     * @param column
     * Checks if a black checker is moveable
     * @return
     */
    private boolean blackMoveable(int row, int column){
    	
        if (blackCaptureable(row, column)){
            return true;
        }
        
        if (row + 1 <= 7){
        	
            if (column - 1 >= 0 && boardStatus[row + 1][column - 1] == 'e'){
                return true;
            }
           
            if (column + 1 <= 7 && boardStatus[row + 1][column + 1] == 'e') {
                return true;
            }
        }
        return false;
    }

    
    /**
     * @param row
     * @param column
     * Checks if a red checker is moveable
     * @return
     */
    private boolean redMoveable(int row, int column){
       
    	if (redCaptureable(row, column)){
            return true;
        }
        
    	if (row - 1 >= 0){
    		
            if (column - 1 >= 0 && boardStatus[row - 1][column - 1] == 'e'){
                return true;
            }
           
            if (column + 1 <= 7 && boardStatus[row - 1][column + 1] == 'e'){
                return true;
            }
        }
        return false;
    }


    /**
     * @param row
     * @param column
     * Checks if a red checker can make a capture move
     * @return boolean
     */
    private boolean redCaptureable(int row, int column){
    	
    	if (row - 2 >= 0){
            
    		if (column - 2 >= 0 && boardStatus[row - 2][column - 2] == 'e' && boardStatus[row - 1][column - 1] == 'b'){
                redCanCapture++;
                return true;
            }
            
    		if (column + 2 <= 7 && boardStatus[row - 2][column + 2] == 'e' && boardStatus[row - 1][column + 1] == 'b'){
                redCanCapture++;
                return true;
            }
        }
        return false;
    }

    
     /**
     * @param row
     * @param column
     * Checks if a black checker can make a capture move
     * @return
     */
    private boolean blackCaptureable(int row, int column){
        
    	if (row + 2 <= 7){
            
    		if (column - 2 >= 0 && boardStatus[row + 2][column - 2] == 'e' && boardStatus[row + 1][column - 1] == 'r'){
                return true;
            }
            
    		if (column + 2 <= 7 && boardStatus[row + 2][column + 2] == 'e' && boardStatus[row + 1][column + 1] == 'r'){
                return true;
            }
        }
        return false;
    }


    /**
     * @param row
     * @param column
     * Checks if a king checker can make a capture move
     * @return boolean
     */
    private boolean kingCaptureable(int row, int column){
    	
        return blackCaptureable(row, column) || redCaptureable(row, column);
    }

    
    /**
     * @param row
     * @param column
     * Checks if a king checker is moveable
     * @return boolean
     */
    private boolean kingMoveable(int row, int column){
    	
        return blackMoveable(row, column) || redMoveable(row, column);
    }

    
    /**
     * @param row
     * @param column
     * Checks if a checker can make a capture move
     * @return boolean
     */
    boolean captureable(int row, int column){
    	
        if (boardStatus[row][column] == 'b'){
        	
            return blackCaptureable(row, column);
        }
        
        if (boardStatus[row][column] == 'r'){
        	
            return redCaptureable(row, column);
        }
        
        if (boardStatus[row][column] == 'k' || boardStatus[row][column] == 'q'){
        	
            return kingCaptureable(row, column);
        }
        return false;
    }


    /**
     * @param row
     * @param column
     * Checks is a checker is move able
     * @return
     */
    public boolean moveable(int row, int column){
    	
        if (boardStatus[row][column] == 'b'){
            return blackMoveable(row, column);
        }
        
        if (boardStatus[row][column] == 'r'){
            return redMoveable(row, column);
        }
        
        if (boardStatus[row][column] == 'k' || boardStatus[row][column] == 'q'){
        	return kingMoveable(row, column);
        }
        return false;
    }

    
    /**
     * @param instance method 
     */
    @Override
    public String toString(){
    	
        return "Black: " + black + ", Red: " + red + " ";
    }
    
    
    /**
     * @return int
     */
    public int getBlackCanCapture(){
    	
        return blackCanCapture;
    }

    
    /**
     * @return int 
     */
    public int getRedCanCapture(){
    	
        return redCanCapture;
    }

   
    /**
     * @return int 
     */
    public int getBlack(){
    	
        return black;
    }

   
    /**
     * @param black
     */
    public void setBlack(int black){
    	
        this.black = black;
    }

    
    /**
     * @return int 
     */
    public int getRed(){
    	
        return red;
    }

   
    /**
     * @param red
     */
    public void setRed(int red){
    	
        this.red = red;
    }
}