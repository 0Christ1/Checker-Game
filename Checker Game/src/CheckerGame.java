import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * @category Project_04
 * In this project, we will design a checkerboard graphical user interface (GUI), 
 * not necessarily functional, that can draw a board along with the checker pieces on that board.
 * @author Haoran Chen
 * @create: April 27, 2021
 */

public class CheckerGame extends JFrame implements MouseListener, ActionListener{

	/**
	 * @param instance variables 
	 */
    private boolean turnBlack;
    private boolean captureMove;
    private boolean mustJump;
    private CheckerBoard cb;
    private JLabel statusLabel;
    private int picks;
    private CheckerPiece tbm;
    private CheckerPiece dest;
    private JOptionPane aboutPane;
    private static char[][] boardStatus = new char[][]{
        {'e', 'b', 'e', 'b', 'e', 'b', 'e', 'b'},
        {'b', 'e', 'b', 'e', 'b', 'e', 'b', 'e'},
        {'e', 'b', 'e', 'b', 'e', 'b', 'e', 'b'},
        {'e', 'e', 'e', 'e', 'e', 'e', 'e', 'e'},
        {'e', 'e', 'e', 'e', 'e', 'e', 'e', 'e'},
        {'r', 'e', 'r', 'e', 'r', 'e', 'r', 'e'},
        {'e', 'r', 'e', 'r', 'e', 'r', 'e', 'r'},
        {'r', 'e', 'r', 'e', 'r', 'e', 'r', 'e'}
        };

        
    /**
     * @param Default constructor 
     */
    public CheckerGame(){
        
    	//Initializes fields and components
        picks = 0;
        turnBlack = true;
        mustJump = false;
        setSize(505, 585);
        
        try{
        	
            cb = new CheckerBoard(boardStatus);
            
        }catch (IllegalCheckerboardArgumentException e){
        	
            e.getStackTrace();
        }
        addMouseEvent(cb);
        
        JMenuBar menuBar = new JMenuBar();
        JPanel statusPanel = new JPanel(new GridLayout(2, 1));
        aboutPane = new JOptionPane("About");

        //Set up the status bar
        statusLabel = new JLabel("New game! Black starts first.", JLabel.CENTER);
        JLabel informationLabel = new JLabel("This game was developed by Haoran Chen.", JLabel.CENTER);
        statusPanel.add(statusLabel);
        statusPanel.add(informationLabel);

        //Game menu
        JMenu gameMenu = new JMenu("Game");
        JMenuItem newItem = new JMenuItem("New");
        newItem.addActionListener(this);
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(this);
        gameMenu.add(newItem);
        gameMenu.add(exitItem);

        //Help menu
        JMenu helpMenu = new JMenu("Help");
        JMenuItem rulesItem = new JMenuItem("Checker Game Rules");
        rulesItem.addActionListener(this);
        JMenuItem aboutItem = new JMenuItem("About Checker Game App");
        aboutItem.addActionListener(this);
        helpMenu.add(rulesItem);
        helpMenu.add(aboutItem);

        menuBar.add(gameMenu);
        menuBar.add(helpMenu);

        //Add components to the frame
        setJMenuBar(menuBar);
        setTitle("Checker Game");
        add(cb, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.SOUTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    
    /**
     * @param args
     * Main method
     * @throws IllegalCheckerboardArgumentException
     */
    public static void main(String[] args) throws IllegalCheckerboardArgumentException{
    	
        CheckerGame cg = new CheckerGame();
    }


    public void setToBeMoved(CheckerPiece toBeMoved){
    	
        if (toBeMoved == null){
            statusLabel.setText("You haven't picked a checker. Please pick a black one.");
            return;
        }
        switch (toBeMoved.validToBeMoved(turnBlack)){
        
            case 2:
                statusLabel.setText("No checker to be moved. Please pick another one");
                break;
            case 3:
                statusLabel.setText("It's black turn. Please pick a black checker.");
                break;
            case 4:
                statusLabel.setText("It's red turn. Please pick a red checker.");
                break;
            case 1:
                this.tbm = toBeMoved;
                picks = 1;
                if (turnBlack)
                    statusLabel.setText("A black checker was picked");
                else 
                    statusLabel.setText("A red checker was picked");
        }
    }

    
   /**
    * Sets the destination square, if can't, then displays message according to the error code
    * @param dest
    */
    public void setDestination(CheckerPiece dest){
    	
        if (dest == null) {
            statusLabel.setText("You haven't picked a checker. Please pick a black one.");
            return;
        }
        switch (dest.validDestination(tbm, cb.getBoardStatus())){
            case 2:
                statusLabel.setText("The square is not empty. Please make another move");
                picks = 0;
                break;
            case 3:
                statusLabel.setText("Can only move diagonal. Please make another move");
                picks = 0;
                break;
            case 4:
                statusLabel.setText("Cannot move horizontally. Please make another move");
                picks = 0;
                break;
            case 5:
                statusLabel.setText("Cannot move backward. Please make another move");
                picks = 0;
                break;
            case 6:
                statusLabel.setText("Cannot move more than one space. Please make another move");
                picks = 0;
                break;
            case 1:
                this.dest = dest;
                picks = 2;
                if (dest.isACapture(tbm, cb.getBoardStatus())) 
                    captureMove = true;
        }
    }

 
    /**
     * @param first
     * @param second
     * Captures a checker between the checker to be moved and the destination square
     */
    private void capture(CheckerPiece first, CheckerPiece second){
    	
        if (first.getStatus() == 'r' || first.getStatus() == 'q') 
            cb.setBlack(cb.getBlack() - 1);

        if (first.getStatus() == 'b' || first.getStatus() == 'k') 
            cb.setRed(cb.getRed() - 1);

        //Removes the captured checker
        cb.setCheckerPiece((first.getRow() + second.getRow()) / 2,
        		(first.getColumn() + second.getColumn()) / 2, 'e');
    }


    /**
     * @param cb
     * Adds mouseEvent to every CheckerPiece object in the panel  
     */
    private void addMouseEvent(CheckerBoard cb){
    	
        for (int i = 0; i < cb.getBoardStatus().length; i++){
            for (int j = 0; j < cb.getBoardStatus().length; j++){
                ((CheckerPiece) cb.getComponent(i * 8 + j)).addMouseListener(this);
            }
        }
    }

    /**
     *@param e
     */
    @Override
    public void mouseClicked(MouseEvent e){
    	
        CheckerPiece cp = (CheckerPiece) e.getComponent();
        statusLabel.setText(cb.toString());
       
        if (picks == 0)
            setToBeMoved(cp);
        else if(picks == 1)
            setDestination(cp);
        
        if (picks == 2 && tbm != null && dest != null){
        	
            if (mustJump() && !captureMove){
                statusLabel.setText("You must make a jump if you can! Please make a jump move");
                resetFields();
            }else{
                moveChecker(tbm, dest);
                resetFields();
                if (turnBlack){
                    statusLabel.setText(cb.toString() + "[Black turn]");
                }else{
                    statusLabel.setText(cb.toString() + "[Red turn]");
                }
            }
        }
        if (cb.notMoveable()){
            endGame();
        }
    }

    
    /**
     * @param first
     * @param second
     * Moves the checker 
     */
    private void moveChecker(CheckerPiece first, CheckerPiece second){
        //Captures if it's capturing
        if (captureMove){
            capture(first, second);
        }
        //Moves the checker to the new destination
        cb.setCheckerPiece(second.getRow(), second.getColumn(), first.getStatus());
        cb.setCheckerPiece(first.getRow(), first.getColumn(), 'e');
        cb.setCheckersState();
    }

    /**
     * Displays the winner and disables all moves afterwards
     */
    private void endGame(){
    	
    	if (turnBlack){
            statusLabel.setText("Red won!");
        }else{
            statusLabel.setText("Black won!");
        }
        cb.setEnabled(false);
    }

    
    /**
     *@param e
     */
    @Override
    public void actionPerformed(ActionEvent e){
    	
        if (e.getActionCommand().equals("New")){
            reset();
        }
        
        if (e.getActionCommand().equals("Exit")){
            dispose();
        }
        
        if (e.getActionCommand().equals("Checker Game Rules")){
            JOptionPane.showMessageDialog(this, "For more information, use these two links:\n"
                    + "https://www.wikihow.com/Play-Checkers" + "\n" + 
            		"https://youtu.be/ScKIdStgAfU", "Rules", JOptionPane.INFORMATION_MESSAGE);
        }
        
        if (e.getActionCommand().equals("About Checker Game App")){
            JOptionPane.showMessageDialog(this, "Haoran Chen, chenh76@miamioh.edu, Miami University",
                    "About", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Resets the fields to the original state
     */
    private void resetFields(){
        
    	if ((captureMove && dest.isCapturable()) || (dest.isKing() && dest.isCapturable())){
            picks = 1;
            tbm = dest;
            dest = null;
        }else{
            if (!mustJump || captureMove){
                turnBlack = !turnBlack;
            }
            picks = 0;
            tbm = null;
            dest = null;
        }
        mustJump = mustJump();
        captureMove = false;
    }

    
    /**
     * @return boolean
     * Check if a capture move can be made
     */
    private boolean mustJump(){
        if (turnBlack && cb.getBlackCanCapture() != 0){
            return true;
        }
        
        if (!turnBlack && cb.getRedCanCapture() != 0){
            return true;
        }
        return false;
    }

    /**
     * Resets the checker board and all the related fields
     */
    private void reset(){
    	cb.reset(boardStatus);
        statusLabel.setText("New Game! Black starts first.");
        mustJump = false;
        captureMove = false;
        tbm = null;
        dest = null;
        picks = 0;
        turnBlack = true;
    }
    
    /**
     * @return JLabel
     */
    public JLabel getStatusLabel(){
        
    	return statusLabel;
    }

    @Override
    public void mousePressed(MouseEvent e){
    	
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}