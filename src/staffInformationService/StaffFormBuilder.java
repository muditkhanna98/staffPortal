package staffInformationService;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/*************************************************************************************************
 * Database programming using java – Assignment 4 *
 * 
 * I declare that this assignment is my own work in accordance with Humber
 * Academic Policy. *
 * 
 * No part of this assignment has been copied manually or electronically from
 * any other source *
 * 
 * (including web sites) or distributed to other students/social media. *
 * 
 * Name: Mudit Khanna Student ID:N01487943 Date: 7TH JULY 2022
 */
public class StaffFormBuilder extends JFrame {
	ActionListener listener;

	Connection connection;

	Statement statement;
	PreparedStatement prepStatement;
	ResultSet set;

	JPanel mainPanel;
	JPanel staffInfoPanel;
	JPanel idPanel;
	JPanel namePanel;
	JPanel addressPanel;
	JPanel cityPanel;
	JPanel telePanel;
	JPanel buttonsPanel;

	JLabel idLabel;
	JLabel lastNameLabel;
	JLabel firstNameLabel;
	JLabel sexLabel;
	JLabel addressLabel;
	JLabel cityLabel;
	JLabel stateLabel;
	JLabel teleLabel;
	JLabel infoLabel;

	JTextField idField;
	JTextField lastNameField;
	JTextField firstNameField;
	JTextField sexField;
	JTextField addressField;
	JTextField cityField;
	JTextField stateField;
	JTextField teleField;

	JButton viewBtn;
	JButton insertBtn;
	JButton updateBtn;
	JButton clearBtn;

	StaffFormBuilder() throws ClassNotFoundException, SQLException {
		setTitle("Staff Details Portal");
		setSize(500, 300);
		setResizable(false);

		// class that implements the action listener
		class ButtonListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == insertBtn) {
					if (isValidData()) {
						insertData();
					} else {
						JOptionPane.showMessageDialog(null, "Wrong input data", "ERROR", JOptionPane.ERROR_MESSAGE);
					}
				} else if (e.getSource() == viewBtn) {
					try {
						viewData();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				} else if (e.getSource() == clearBtn) {
					clearForm();
				} else if (e.getSource() == updateBtn) {
					try {
						updateDetails();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}

		}
		listener = new ButtonListener();
		infoLabel = new JLabel();
		createPanels();
		establishConnection();
	}

	// following function creates the various panel for all the fields and adds them
	// to the main frame
	public void createPanels() {
		createIdPanel();
		createNamePanel();
		createAddressPanel();
		createCityPanel();
		createTelePanel();
		createButtonPanel();

		staffInfoPanel = new JPanel();
		mainPanel = new JPanel();

		staffInfoPanel.setBorder(new TitledBorder(new EtchedBorder(), "Staff Information"));
		staffInfoPanel.setLayout(new GridLayout(5, 5));

		staffInfoPanel.add(idPanel);
		staffInfoPanel.add(namePanel);
		staffInfoPanel.add(addressPanel);
		staffInfoPanel.add(cityPanel);
		staffInfoPanel.add(telePanel);

		mainPanel.add(staffInfoPanel, BorderLayout.NORTH);
		mainPanel.add(buttonsPanel, BorderLayout.SOUTH);
		mainPanel.add(infoLabel);

		add(mainPanel);
	}

	// function to create if panel
	public void createIdPanel() {
		idPanel = new JPanel();
		idLabel = new JLabel("ID");
		idField = new JTextField(10);

		idPanel.add(idLabel);
		idPanel.add(idField);
	}

	// function to create name panel
	public void createNamePanel() {
		namePanel = new JPanel();
		lastNameLabel = new JLabel("Last Name");
		firstNameLabel = new JLabel("First Name");
		sexLabel = new JLabel("mi");

		lastNameField = new JTextField(10);
		firstNameField = new JTextField(10);
		sexField = new JTextField(5);

		namePanel.add(lastNameLabel);
		namePanel.add(lastNameField);
		namePanel.add(firstNameLabel);
		namePanel.add(firstNameField);
		namePanel.add(sexLabel);
		namePanel.add(sexField);
	}

	// function to create address panel
	public void createAddressPanel() {
		addressPanel = new JPanel();
		addressLabel = new JLabel("Address");
		addressField = new JTextField(10);

		addressPanel.add(addressLabel);
		addressPanel.add(addressField);
	}

	// function to create city panel
	public void createCityPanel() {
		cityPanel = new JPanel();
		cityLabel = new JLabel("City");
		stateLabel = new JLabel("State");

		cityField = new JTextField(10);
		stateField = new JTextField(10);

		cityPanel.add(cityLabel);
		cityPanel.add(cityField);
		cityPanel.add(stateLabel);
		cityPanel.add(stateField);
	}

	// function to create telephone panel
	public void createTelePanel() {
		telePanel = new JPanel();
		teleLabel = new JLabel("Telephone");
		teleField = new JTextField(10);

		telePanel.add(teleLabel);
		telePanel.add(teleField);
	}

	// function to create button panel
	public void createButtonPanel() {
		buttonsPanel = new JPanel();

		viewBtn = new JButton("View");
		insertBtn = new JButton("Insert");
		updateBtn = new JButton("Update");
		clearBtn = new JButton("Clear");

		viewBtn.addActionListener(listener);
		insertBtn.addActionListener(listener);
		updateBtn.addActionListener(listener);
		clearBtn.addActionListener(listener);

		buttonsPanel.add(viewBtn);
		buttonsPanel.add(insertBtn);
		buttonsPanel.add(updateBtn);
		buttonsPanel.add(clearBtn);
	}

	// function established connection with the database using the jdbc jar file
	// driver class
	// uses the hidden connection url and username password from the other class
	public void establishConnection() throws ClassNotFoundException, SQLException {
		Class.forName(DataBaseInformation.DRIVER_URL);

		connection = DriverManager.getConnection(DataBaseInformation.CONNECTION_URL, DataBaseInformation.USERNAME,
				DataBaseInformation.PASSWORD);

		infoLabel.setText("Database connected");
	}

	// check if all the fields have data before data is inserted into the database.
	// Also verifies that items are according the size in the database
	public boolean isValidData() {
		if (idField.getText().length() == 0 || lastNameField.getText().length() == 0
				|| firstNameField.getText().length() == 0 || sexField.getText().length() == 0
				|| addressField.getText().length() == 0 || cityField.getText().length() == 0
				|| stateField.getText().length() == 0 || teleField.getText().length() == 0) {
			return false;
		} else {
			if (idField.getText().length() > 9 || lastNameField.getText().length() > 15
					|| firstNameField.getText().length() > 15 || sexField.getText().length() > 1
					|| addressField.getText().length() > 20 || cityField.getText().length() > 20
					|| stateField.getText().length() > 2 || teleField.getText().length() > 10) {
				return false;
			}
			return true;
		}

	}

	// created the preparede statement to insert the record into the database
	public void insertData() {
		try {
			prepStatement = connection.prepareStatement(
					"INSERT INTO STAFF(\"ID\",\"LASTNAME\",\"FIRSTNAME\",\"MI\",\"ADDRESS\",\"CITY\",\"STATE\",\"TELEPHONE\",\"EMAIL\")"
							+ "VALUES(?,?,?,?,?,?,?,?,?)");
			prepStatement.setString(1, idField.getText());
			prepStatement.setString(2, lastNameField.getText());
			prepStatement.setString(3, firstNameField.getText());
			prepStatement.setString(4, sexField.getText());
			prepStatement.setString(5, addressField.getText());
			prepStatement.setString(6, cityField.getText());
			prepStatement.setString(7, stateField.getText());
			prepStatement.setString(8, teleField.getText());
			prepStatement.setString(9, null);

			prepStatement.executeUpdate();
			infoLabel.setText("Record has been inserted.");
			clearForm();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// function that calls the statement to get the result set with the rows of all
	// records in the database
	// further created object of second frame and sends the data to the same
	public void viewData() throws SQLException {
		statement = connection.createStatement();
		set = statement.executeQuery("SELECT ID,LASTNAME,FIRSTNAME,MI,ADDRESS,CITY,STATE,TELEPHONE,EMAIL FROM STAFF");
		String data = "";
		int count = 1;
		while (set.next()) {
			data += count + ") " + "Id:" + set.getString(1) + "\tLast Name: " + set.getString(2) + "\tFirst Name: "
					+ set.getString(3) + "\tGender: " + set.getString(4) + "\tAddress: " + set.getString(5) + " "
					+ set.getString(6) + " " + set.getString(7) + "\tMobile: " + set.getString(8) + "\n";
			count++;
		}

		JFrame dispFrame = new DisplayInfoFrame(data);
	}

	// clears all the fields of the form
	public void clearForm() {
		idField.setText("");
		lastNameField.setText("");
		firstNameField.setText("");
		sexField.setText("");
		addressField.setText("");
		cityField.setText("");
		stateField.setText("");
		teleField.setText("");
	}

	// function created prepared statement to first check if the record with the id
	// exists
	// if doesnt exist then dialog box with error is shown
	// if exists then the record row is updated with the new values
	public void updateDetails() throws SQLException {
		if (!idField.getText().isEmpty()) {
			prepStatement = connection.prepareStatement("SELECT FIRSTNAME FROM STAFF WHERE ID=?");
			prepStatement.setString(1, idField.getText());

			set = prepStatement.executeQuery();
			if (set.next()) {
				prepStatement = connection.prepareStatement(
						"update staff set firstname=?,lastname=?,mi=?,address=?,city=?,state=?,telephone=? where id=?");
				prepStatement.setString(1, firstNameField.getText());
				prepStatement.setString(2, lastNameField.getText());
				prepStatement.setString(3, sexField.getText());
				prepStatement.setString(4, addressField.getText());
				prepStatement.setString(5, cityField.getText());
				prepStatement.setString(6, stateField.getText());
				prepStatement.setString(7, teleField.getText());
				prepStatement.setString(8, idField.getText());

				prepStatement.executeUpdate();
				infoLabel.setText("Record has been updated");
			} else {
				JOptionPane.showMessageDialog(this, "No record with this id found", "ERROR", JOptionPane.ERROR_MESSAGE);

			}
		} else {
			JOptionPane.showMessageDialog(this, "Please input id to update record", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}

}
