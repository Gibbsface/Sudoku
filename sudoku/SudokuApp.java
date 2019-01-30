package sudoku;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class SudokuApp extends JFrame implements ActionListener {
	
	private static final int WIDTH = 500;
	private static final int HEIGHT = 200;
	private static final String TITLE = "Please Select a Difficulty...";
	private int difficulty = -1;
	
	private JButton easy = new JButton("Easy");
	private JButton med = new JButton("Medium");
	private JButton hard = new JButton("Hard");
	private JButton extreme = new JButton("Extreme");
	private JButton custom = new JButton("Custom");
	
	JTextField customTxt = new JTextField();
	
	private int[][] puzzle = new int[9][9];
	private int[][] answer = new int[9][9];

	//Constructor
	public SudokuApp() {
		
		super(TITLE);
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		
		JPanel customPanel = new JPanel();
		customPanel.setLayout(new GridLayout(0,1));
		customPanel.setBackground(Color.LIGHT_GRAY);
		
		JLabel customMsg1 = new JLabel("To play with a custom file:", SwingConstants.CENTER);
		JLabel customMsg2 = new JLabel("click \"Custom\", enter the name below as \"filename.txt\",", SwingConstants.CENTER);
		JLabel customMsg3 = new JLabel("then click \"Start Game\"", SwingConstants.CENTER);
		
		customPanel.add(customMsg1);
		customPanel.add(customMsg2);
		customPanel.add(customMsg3);
		
		customTxt.setHorizontalAlignment(JTextField.CENTER);
        customTxt.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
		customPanel.add(customTxt, BorderLayout.SOUTH);
		
		JPanel diffButtons = new JPanel();
		diffButtons.setBackground(Color.LIGHT_GRAY);
		diffButtons.setLayout(new FlowLayout());
		
		JButton start = new JButton("Start Game");
		start.setBackground(Color.WHITE);
		start.addActionListener(this);
		
		custom.setBackground(Color.WHITE);
		custom.addActionListener(this);
		
		easy.setBackground(Color.GREEN);
		easy.addActionListener(this);
		
		med.setBackground(Color.CYAN);
		med.addActionListener(this);
		
		hard.setBackground(Color.YELLOW);
		hard.addActionListener(this);
		
		extreme.setBackground(Color.RED);
		extreme.addActionListener(this);
		
		diffButtons.add(easy);
		diffButtons.add(med);
		diffButtons.add(hard);
		diffButtons.add(extreme);
		diffButtons.add(custom);
		
		add(diffButtons, BorderLayout.NORTH);
		add(customPanel, BorderLayout.CENTER);
		add(start, BorderLayout.SOUTH);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		String buttonPressed = e.getActionCommand();
		
		if (buttonPressed.equals("Easy")) {
			//System.out.println("Easy");
			easy.setBackground(Color.GRAY);
			med.setBackground(Color.CYAN);			
			hard.setBackground(Color.YELLOW);
			extreme.setBackground(Color.RED);
			
			difficulty = 0;
		} else if (buttonPressed.equals("Medium")) {
			//System.out.println("Medium");
			easy.setBackground(Color.GREEN);
			med.setBackground(Color.GRAY);			
			hard.setBackground(Color.YELLOW);
			extreme.setBackground(Color.RED);
			custom.setBackground(Color.WHITE);
			difficulty = 1;
		} else if (buttonPressed.equals("Hard")) {
			//System.out.println("Hard");
			easy.setBackground(Color.GREEN);
			med.setBackground(Color.CYAN);			
			hard.setBackground(Color.GRAY);
			extreme.setBackground(Color.RED);
			custom.setBackground(Color.WHITE);
			difficulty = 2;
		} else if (buttonPressed.equals("Extreme")) {
			//System.out.println("Extreme");
			easy.setBackground(Color.GREEN);
			med.setBackground(Color.CYAN);			
			hard.setBackground(Color.YELLOW);
			extreme.setBackground(Color.GRAY);
			custom.setBackground(Color.WHITE);
			difficulty = 3;
		} else if (buttonPressed.equals("Custom")) {
			//System.out.println("Custom");
			easy.setBackground(Color.GREEN);
			med.setBackground(Color.CYAN);			
			hard.setBackground(Color.YELLOW);
			extreme.setBackground(Color.RED);
			custom.setBackground(Color.GRAY);
			difficulty = 10;
			
		} else if (buttonPressed.equals("Start Game")) {
			//System.out.println("Start!");
			if(difficulty < 0) {
				System.out.println("No Game Selected");
			} else if (difficulty==10 & customTxt.getText().equals("")) {
				System.out.println("Error: Empty File Name");
			} else {
				sudokuGameStart(difficulty);
			}			
		}
	}

	private void sudokuGameStart(int difficulty) {
		
		puzzle = readPuzzle(difficulty);
		
		LoadingScreen ls = new LoadingScreen();
		ls.setVisible(true);
		
		//Solve Puzzle
		Solver s = new Solver(answer);
		
		if(s.possible) {
			SudokuGame sg = new SudokuGame(puzzle, answer);
			sg.setVisible(true);
			ls.close();
			dispose();
		} else {
			ErrorWindow ew = new ErrorWindow();
    		ew.setVisible(true);
		}	
	}

	private int[][] readPuzzle(int difficulty) {
		
		int[][] puzzle = new int[9][9];
		
		String file = null;
		switch(difficulty) {
		case 0 :
			file = "easy.txt";
			break;
		case 1 :
			file = "medium.txt";
			break;
		case 2 :
			file = "hard.txt";
			break;
		case 3 : 
			file = "extreme.txt";
			break;
		case 10:
			file = customTxt.getText();
			System.out.println(file);
			break;
		default :
			break;
		}
		
		//Scan game in
		Scanner inputStream = null;
		try {
			
			inputStream = new Scanner(new FileInputStream(file));
			
		} catch (FileNotFoundException e) {
			System.out.println("File not found, ending program");
			System.exit(0);
		}
		
		for(int i = 0; i < 9; ++i) {
			for(int j = 0; j < 9; ++j) {
				int val = inputStream.nextInt();
				answer[i][j] = val;
				puzzle[i][j] = val;
			}
		}
		
		return puzzle;
	}

	public int getDifficulty() {return difficulty;}
	
}
