package sudoku;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class ConfirmWindow extends JFrame implements ActionListener {

	ConfirmWindow() {
		
		setSize(300,120);
		getContentPane().setBackground(Color.WHITE);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		
		JLabel msg = new JLabel("Are you sure you want to Exit???", SwingConstants.CENTER);
		add(msg, BorderLayout.CENTER);
		
		JPanel btns = new JPanel();
		btns.setLayout(new FlowLayout());
		
		JButton yes = new JButton("Yes");
		yes.addActionListener(this);
		btns.add(yes);
		
		JButton no = new JButton("No");
		no.addActionListener(this);
		btns.add(no);
		
		add(btns, BorderLayout.SOUTH);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		String button = e.getActionCommand();
		
		if(button.equals("Yes")) {
			System.exit(0);
		} else if (button.equals("No")) {
			dispose();
		} else {
			System.out.println("error in ConfirmWindow");
		}
		
	}

}
