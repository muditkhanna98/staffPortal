package staffInformationService;

import java.awt.TextArea;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class DisplayInfoFrame extends JFrame {

	// created object of second frame and appends data to the text area to view the
	// records in the database
	public DisplayInfoFrame(String data) {
		TextArea area = new TextArea(35, 60);
		JPanel panel = new JPanel();

		area.append(data);

		setSize(700, 700);
		panel.add(area);
		add(panel);
		setVisible(true);
	}
}
