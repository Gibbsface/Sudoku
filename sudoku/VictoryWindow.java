package sudoku;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class VictoryWindow extends JFrame {

	VictoryWindow(int seconds) {
		
		super("Nice!");
		setSize(300,120);
		getContentPane().setBackground(Color.WHITE);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel msg1 = new JLabel("Congrats! You solved it in " + seconds + " seconds", SwingConstants.CENTER);
		add(msg1, BorderLayout.CENTER);
		
	}
}
