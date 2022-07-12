package staffInformationService;

import java.sql.SQLException;

import javax.swing.JFrame;

public class Main {

	// simply creates the object of the frame and calls the set visible function to
	// make the frame visible
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		JFrame staffFrame = new StaffFormBuilder();
		staffFrame.setVisible(true);
	}
}
