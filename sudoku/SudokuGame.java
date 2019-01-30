package sudoku;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class SudokuGame extends JFrame implements KeyListener,FocusListener,ActionListener {
	
	private static final int WIDTH = 500;
	private static final int HEIGHT = 500;
	private static final String TITLE = "Sudoku Game!";
	
	private Timer time = null;
	private int seconds = 0;
	private ActionListener timeEar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			++seconds;
			time.restart();
			//System.out.println(seconds);
			showTime.setText("Time: " + seconds + " seconds");
		}};
		
	private JLabel showTime = new JLabel("Time: " + seconds + " seconds");
	
	private Integer[][] puzzle = new Integer[9][9];
	private Integer[][] answer = new Integer[9][9];
    
    private JTextField jText[][] = new JTextField[9][9];
    
    private int[] boxOrder = {0,  1,  2, 10, 11, 12, 20, 21, 22, 
    		 3,  4,  5, 13, 14, 15, 23, 24, 25, 
    		 6,  7,  8, 16, 17, 18, 26, 27, 28, 
    		30, 31, 32, 40, 41, 42, 50, 51, 52, 
    		33, 34, 35, 43, 44, 45, 53, 54, 55, 
    		36, 37, 38, 46, 47, 48, 56, 57, 58, 
    		60, 61, 62, 70, 71, 72, 80, 81, 82, 
    		63, 64, 65, 73, 74, 75, 83, 84, 85, 
    		66, 67, 68, 76, 77, 78, 86, 87, 88};
    
    private int[] indOrder = { 0,  1,  2,  3,  4,  5,  6,  7,  8, 
    		10, 11, 12, 13, 14, 15, 16, 17, 18, 
    		20, 21, 22, 23, 24, 25, 26, 27, 28, 
    		30, 31, 32, 33, 34, 35, 36, 37, 38, 
    		40, 41, 42, 43, 44, 45, 46, 47, 48, 
    		50, 51, 52, 53, 54, 55, 56, 57, 58, 
    		60, 61, 62, 63, 64, 65, 66, 67, 68, 
    		70, 71, 72, 73, 74, 75, 76, 77, 78, 
    		80, 81, 82, 83, 84, 85, 86, 87, 88};
	
    //Constructor
	public SudokuGame(int[][] primitive_puzzle, int[][] primitive_answer) {
		
		super(TITLE);
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowTools());
		setLocationRelativeTo(null);
		
		JPanel numberGrid = new JPanel();
		numberGrid.setLayout(new GridLayout(3,3));
		numberGrid.setBackground(Color.BLACK);
        
        //Convert primitive_puzzle to Integer() type
        for(int i = 0; i < 9; ++i) {
        	for(int j = 0; j < 9; ++j) {
        		puzzle[i][j] = new Integer(primitive_puzzle[i][j]);
        		answer[i][j] = new Integer(primitive_answer[i][j]);
        	}
        }
        
        
        //Create large grid of 9 JPanels
        JPanel jPanel[] = new JPanel[9];        
        for (int i = 0; i < 9; i++) {
            jPanel[i] = new JPanel();
            jPanel[i].setLayout(new GridLayout(3, 3));
            jPanel[i].setAlignmentX(CENTER_ALIGNMENT);
            jPanel[i].setAlignmentY(CENTER_ALIGNMENT);          
            numberGrid.add(jPanel[i]);
        }
        
        //Create a grid of 81 jText objects
        for(int i = 0; i < 9; ++i) {
        	for(int j = 0; j < 9; ++j) {
        		jText[i][j] = new JTextField();
                jText[i][j].setHorizontalAlignment(JTextField.CENTER);
                jText[i][j].setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
                jText[i][j].addFocusListener(this);
                jText[i][j].addKeyListener(this);
                jText[i][j].setColumns(1);
                jPanel[i].add(jText[i][j]);
        	}
        }
        
        //add puzzle to jTexts
        for(int i = 0; i < 81; ++i) {
        	int ind = boxOrder[i] % 10;
        	int box = (boxOrder[i] - boxOrder[i] % 10) / 10;
        	
        	int row = indOrder[i] / 10;
        	int col = indOrder[i] % 10;
        	
        	jText[box][ind].setText(puzzle[row][col].toString());
            jText[box][ind].setText(puzzle[row][col].toString());
        	jText[box][ind].setEditable(false);
        	
            if(puzzle[row][col]==0) {
            	jText[box][ind].setText("");
            	jText[box][ind].setEditable(true);
            }
        	//System.out.println(box + ", " + ind + "\t" + row + ", " + col);
        }
        add(numberGrid);    
        
        //Menu panel
        JPanel menu = new JPanel();
        menu.setLayout(new FlowLayout());
		menu.setBackground(Color.LIGHT_GRAY);
        
        JButton quit = new JButton("Give up");
        quit.setBackground(Color.WHITE);
        quit.addActionListener(this);
        menu.add(quit);
        
        add(menu, BorderLayout.SOUTH);
        
        //Start timer
        time = new Timer(1000, timeEar);
        time.start();
        
        
        menu.add(showTime);
        
    }	

	private void refresh() {
		//System.out.println("Changed focus, refreshing board...");
		boolean win = true;
		for(int i = 0; i < 81; ++i) {
			int ind = boxOrder[i] % 10;
        	int box = (boxOrder[i] - boxOrder[i] % 10) / 10;
        	int row = indOrder[i] / 10;
        	int col = indOrder[i] % 10;
			
			if(answer[row][col].toString().equals(jText[box][ind].getText()) && puzzle[row][col]==0) {
            	//System.out.println("Correct Answer!");
				jText[box][ind].setEditable(false);
				jText[box][ind].setForeground(Color.GREEN);
            } else if(puzzle[row][col]==0){
            	//System.out.println("Incorrect Answer...");
            	jText[box][ind].setForeground(Color.BLUE);
            }
			
			//Check win condition
			if(jText[box][ind].isEditable()) {
				win = false;
			}
		}
		
		if(win) {
			time.removeActionListener(timeEar);
			time.stop();
			
			VictoryWindow vw = new VictoryWindow(seconds);
			vw.setVisible(true);
			
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		Character c = new Character(e.getKeyChar());
		if(!Character.isDigit(c)) {
			e.consume();
		}
	}

	private void navigate(int k) {
		boolean hasFocus;
		switch (k) {
		case KeyEvent.VK_UP:
			//System.out.println("up");
			for(int i = 0; i < 81; ++i) {
				int ind = boxOrder[i] % 10;
	        	int box = (boxOrder[i] - boxOrder[i] % 10) / 10;
	        	hasFocus = (KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner() == jText[box][ind]);
	        	if(hasFocus) {
	        		//System.out.println("Found focus");
	        		if((ind - 3) >= 0) {
	        			jText[box][ind - 3].requestFocus();
	        		} else if(((box == 0) || (box == 1) || (box == 2)) && (ind - 3 <= 0)) {
	        			jText[box][ind].requestFocus();
	        		} else {
	        			jText[box - 3][ind + 6].requestFocus();
	        		}
	        	}
	        }
			break;
		case KeyEvent.VK_DOWN:
			//System.out.println("down");
			for(int i = 0; i < 81; ++i) {
				int ind = boxOrder[i] % 10;
	        	int box = (boxOrder[i] - boxOrder[i] % 10) / 10;
	        	hasFocus = (KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner() == jText[box][ind]);
	        	if(hasFocus) {
	        		//System.out.println("Found focus");
	        		if((ind != 6) && (ind != 7) && (ind != 8)) {
	        			jText[box][ind + 3].requestFocus();
	        		} else if((box == 6) || (box == 7) || (box == 8)) {
	        			jText[box][ind].requestFocus();
	        		} else {
	        			jText[box + 3][ind - 6].requestFocus();
	        		}
	        	}
	        }
			break;
		case KeyEvent.VK_LEFT:
			//System.out.println("up");
			for(int i = 0; i < 81; ++i) {
				int ind = boxOrder[i] % 10;
	        	int box = (boxOrder[i] - boxOrder[i] % 10) / 10;
	        	hasFocus = (KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner() == jText[box][ind]);
	        	if(hasFocus) {
	        		//System.out.println("Found focus");
	        		if((ind != 0) && (ind != 3) && (ind != 6)) {
	        			jText[box][ind-1].requestFocus();
	        		} else if((box == 0) || (box == 3) || (box == 6)) {
	        			jText[box][ind].requestFocus();
	        		} else {
	        			jText[box - 1][ind + 2].requestFocus();
	        		}
	        	}
	        }
			break;
		case KeyEvent.VK_RIGHT:
			//System.out.println("up");
			for(int i = 0; i < 81; ++i) {
				int ind = boxOrder[i] % 10;
	        	int box = (boxOrder[i] - boxOrder[i] % 10) / 10;
	        	hasFocus = (KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner() == jText[box][ind]);
	        	if(hasFocus) {
	        		//System.out.println("Found focus");
	        		if((ind != 2) && (ind != 5) && (ind != 8)) {
	        			jText[box][ind+1].requestFocus();
	        		} else if((box == 2) || (box == 5) || (box == 8)) {
	        			jText[box][ind].requestFocus();
	        		} else {
	        			jText[box + 1][ind - 2].requestFocus();
	        		}
	        	}
	        }
			break;
		default:
			System.out.println("Unexpected error in keyboard");
		}
	}

	@Override
	public void focusGained(FocusEvent e) {
		refresh();
	}

	@Override
	public void focusLost(FocusEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		int k = e.getKeyCode();
		if((k == KeyEvent.VK_UP) || (k == KeyEvent.VK_DOWN) || (k == KeyEvent.VK_LEFT) || (k == KeyEvent.VK_RIGHT)) {
			navigate(k);
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Game quit, displaying solution...");
		time.stop();
		for(int i = 0; i < 81; ++i) {
			int ind = boxOrder[i] % 10;
        	int box = (boxOrder[i] - boxOrder[i] % 10) / 10;
        	int row = indOrder[i] / 10;
        	int col = indOrder[i] % 10;
			
        	if(puzzle[row][col] == 0) {
        		jText[box][ind].setText(answer[row][col].toString());
            	jText[box][ind].setEditable(false);
    			jText[box][ind].setForeground(Color.GREEN);
        	}	
		}
	}

}
