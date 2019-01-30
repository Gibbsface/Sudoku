package sudoku;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WindowTools extends WindowAdapter {

	@Override
	public void windowClosing(WindowEvent arg0) {
		ConfirmWindow check = new ConfirmWindow();
		check.setVisible(true);
	}
	
}
