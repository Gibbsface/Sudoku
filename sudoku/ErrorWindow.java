package sudoku;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class ErrorWindow extends JFrame {

	ErrorWindow() {
		
		setSize(300,120);
		getContentPane().setBackground(Color.WHITE);
		setLayout(new GridLayout(4,0));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel msg1 = new JLabel("Error: no solution for given puzzle", SwingConstants.CENTER);
		add(msg1, 0);
		
	}
}
