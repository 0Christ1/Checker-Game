/**
 * @category Project_04
 * In this project, we will design a checkerboard graphical user interface (GUI), 
 * not necessarily functional, that can draw a board along with the checker pieces on that board.
 * @author Haoran Chen
 * @create: April 27, 2021
 */

public class IllegalCheckerboardArgumentException extends Exception{
    
    public IllegalCheckerboardArgumentException(){
    	
    	super();
    }
    
    public IllegalCheckerboardArgumentException(String message){
    	
        super(message);
    }
    
    public IllegalCheckerboardArgumentException(String message, Throwable err){
    	
        super(message, err);
    }
}


