package sudoku;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class LoadingScreen extends JFrame {

	LoadingScreen() {
		
		setSize(300,120);
		getContentPane().setBackground(Color.WHITE);
		setLayout(new GridLayout(4,0));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		JLabel msg1 = new JLabel("Loading Sudoku Game...", SwingConstants.CENTER);
		add(msg1, 0);
		
		JLabel msg2 = new JLabel("", SwingConstants.CENTER);
		add(msg2, 1);
		
		JLabel msg3 = new JLabel("This may take a while,", SwingConstants.CENTER);
		add(msg3, 2);
		
		JLabel msg4 = new JLabel("I am generating a solution", SwingConstants.CENTER);
		add(msg4, 3);
		
	}
	

	public void close() {
		dispose();
	}

}
