import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
 * @create: May 8, 2021
 */

public class CG extends JFrame implements ActionListener{

	/**
	 * @param boardStatus
	 * @param cb
	 * @param sl
	 */
	private static char[][]boardStatus;
	private CheckerBoard cb;
	private JLabel sl;
	
	/**
	 * @throws IllegalCheckerboardArgumentException
	 */
	public CG() throws IllegalCheckerboardArgumentException {
		
		cb = new CheckerBoard(boardStatus);
		JPanel sp = new JPanel(new GridLayout(2,1));
		JMenuBar mb = new JMenuBar();
		
		//Game menu
        JMenu gm = new JMenu("Game");
        JMenuItem ni = new JMenuItem("New");
        ni.addActionListener(this);
        JMenuItem ei = new JMenuItem("Exit");
        ei.addActionListener(this);
        gm.add(ni);
        gm.add(ei);
        mb.add(gm);
        
        //Help menu
        JMenu hm = new JMenu("Help");
        JMenuItem ri = new JMenuItem("Checker Game Rules");
        ri.addActionListener(this);
        JMenuItem ai = new JMenuItem("About Checker Game App");
        ai.addActionListener(this);
        hm.add(ri);
        hm.add(ai);
        mb.add(hm);
        
        //status bar and signature bar
        sl = new JLabel("This is where the status would be Black first.", JLabel.CENTER);
        JLabel sign = new JLabel("This game was developed by Haoran Chen.", JLabel.CENTER);
        sp.add(sl);
        sp.add(sign);
        
		//add components to the frame
        setJMenuBar(mb);
        add(cb, BorderLayout.CENTER);
        add(sp, BorderLayout.SOUTH);
		
	}


	/**
	 *ActionListener
	 *@param e
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("New"))
			Reset();
		else if(e.getActionCommand().equals("Exit"))
			dispose();
		else if(e.getActionCommand().equals("Checker Game Rules"))
			JOptionPane.showMessageDialog(this, "For more information, use these two links:\n"
                    + "https://www.wikihow.com/Play-Checkers" + "\n" + 
            		"https://youtu.be/ScKIdStgAfU", "Rules", JOptionPane.INFORMATION_MESSAGE);
		else if(e.getActionCommand().equals("About Checker Game App"))
			JOptionPane.showMessageDialog(this, "Haoran Chen, chenh76@miamioh.edu, Miami University",
                    "About", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * instance method
	 */
	private void Reset() {
		
		cb.reset(boardStatus);
		sl.setText("This is where the status would be Black first.");
		
	}
	
	/**
	 * @param args
	 * @throws IllegalCheckerboardArgumentException
	 */
	public static void main(String[] args) throws IllegalCheckerboardArgumentException {
		
		boardStatus = new char[][]{
	    {'e', 'b', 'e', 'b', 'e', 'b', 'e', 'b'},
	    {'b', 'e', 'b', 'e', 'b', 'e', 'b', 'e'},
	    {'e', 'b', 'e', 'b', 'e', 'b', 'e', 'b'},
	    {'e', 'e', 'e', 'e', 'e', 'e', 'e', 'e'},
	    {'e', 'e', 'e', 'e', 'e', 'e', 'e', 'e'},
	    {'r', 'e', 'r', 'e', 'r', 'e', 'r', 'e'},
	    {'e', 'r', 'e', 'r', 'e', 'r', 'e', 'r'},
	    {'r', 'e', 'r', 'e', 'r', 'e', 'r', 'e'}
	    };
		
		CG cg = new CG();
		cg.setSize(505, 585);
		cg.setTitle("Checker Game");
		cg.setDefaultCloseOperation(EXIT_ON_CLOSE);
		cg.setVisible(true);
		cg.setResizable(false);
	}
}