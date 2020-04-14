package com.KerrYip.ClientView;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.LineBorder;

import com.KerrYip.Model.Registration;
import com.KerrYip.Model.Student;

/**
 * The Panel for the Student Menu which allows the student to select what
 * operations they would like to do for their courses
 * 
 * @author Tyler Yip
 * @author Will Kerr
 */
public class StudentMenuPanel extends JPanel {
	private int width, height;
	private JButton searchCatalogButton, enrollCourseButton, dropCourseButton, browseCatalogButton, logoutButton;
	private JLabel studentMenuLabel;
	private JTextArea dataText;

	private static Color redColor = Color.decode("#800020");
	private static Color goldColor = Color.decode("#CAB15E");

	/**
	 * Constructs the Student Menu Panel
	 *
	 * @param width  width of the Frame the panel will be in
	 * @param height width of the Frame the panel will be in
	 */
	public StudentMenuPanel(int width, int height) {
		this.width = width;
		this.height = height;

		setBorder(new LineBorder(redColor,10));


		// student menu title
		studentMenuLabel = new JLabel();
		studentMenuLabel.setText("Student Menu");
		studentMenuLabel.setFont(new Font("Dialog",Font.BOLD,30));

		// student menu panels for formatting
		JPanel titlePanel = new JPanel();
		JPanel buttonPanel = formatButtons();
		JPanel coursePanel = new JPanel();
		coursePanel.setLayout(new BorderLayout());

		// Set up the layout of the main window
		setLayout(new BorderLayout(10,10));

		// Add all the buttons into the panel
		titlePanel.add(studentMenuLabel);
		// coursePanel.add("South",viewEnrolledCoursesButton);

		// the data field that displays
		dataText = new JTextArea((int) (width * 0.75), (int) (height * 0.75));
		dataText.setLineWrap(true); // Allows text to wrap if it reaches the end of the line
		dataText.setWrapStyleWord(true); // text should wrap at word boundaries rather than character boundaries
		dataText.setEditable(false); // This ensure that the user cannot edit the data field
		dataText.setText(""); // This displays empty text in the field

		// Make the data field scroll-able if enough data fills the panel
		JScrollPane dataTextScrollPane = new JScrollPane(dataText);
		dataTextScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		coursePanel.add("Center", dataTextScrollPane);

		JPanel courseTitlePanel = new JPanel();
		JLabel courseTitle = new JLabel("Your courses");
		courseTitlePanel.setBackground(Color.decode("#67001a"));
		courseTitlePanel.add(courseTitle);
		coursePanel.add("North", courseTitlePanel);

		JPanel logoutPanel = new JPanel(new BorderLayout(0,10));
		logoutPanel.add("East",logoutButton);

		// adding panels to appropriate quadrants
		add("North", titlePanel);
		add("East", buttonPanel);
		add("Center", coursePanel);
		add("South", logoutPanel);

	}

	public JPanel formatButtons(){
		// Buttons for the main window
		JPanel buttonPanel = new JPanel(new BorderLayout(0,10));
		JPanel buttons1 = new JPanel(new BorderLayout(0,10));
		JPanel buttons2 = new JPanel(new BorderLayout(0,10));
		JPanel buttons3 = new JPanel(new BorderLayout(0,10));

		//add button
		searchCatalogButton = new JButton("Search for Course in Catalog");
		enrollCourseButton = new JButton("Enroll in a Course");
		dropCourseButton = new JButton("Drop a Course");
		browseCatalogButton = new JButton("View all Courses in Catalog");
		logoutButton = new JButton("Logout");

		//add logos to buttons
		searchCatalogButton.setIcon(new ImageIcon("Search.png"));
		enrollCourseButton.setIcon(new ImageIcon("Add.png"));
		dropCourseButton.setIcon(new ImageIcon("Remove.png"));
		browseCatalogButton.setIcon(new ImageIcon("Catalog.png"));
		logoutButton.setIcon(new ImageIcon("Logout.png"));

		//add school logo
		ImageIcon logo = new ImageIcon("LogoPic.png");
		JLabel logoLabel = new JLabel(logo);

		//empty space
		JPanel emptySpace = new JPanel();
		emptySpace.setBorder(new LineBorder(redColor,8));

		buttons1.add("North",emptySpace);
		buttons1.add("Center",enrollCourseButton);
		buttons1.add("South",dropCourseButton);
		buttons2.add("North",browseCatalogButton);
		buttons3.add("North",searchCatalogButton);
		buttons2.add("Center",buttons3);
		buttons2.add("South",logoLabel);

		buttonPanel.add("North",buttons1);
		buttonPanel.add("Center",buttons2);
		return buttonPanel;
	}

	public void updateEnrolledCourse(ArrayList<Registration> registrationsList) {
		String temp = "";
		for (int i = 0; i < registrationsList.size(); i++) {
			temp += registrationsList.get(i).toCourseString();
		}
		dataText.setText(temp);
	}

	public void updateTitle(String name) {
		studentMenuLabel.setText(name + "'s Course Registration");
	}

	/**
	 * Adds listener to the enrollCourseButton
	 *
	 * @param listenForSearchCatalogButton listener for the button
	 */
	public void addEnrollCourseListener(ActionListener listenForSearchCatalogButton) {
		enrollCourseButton.addActionListener(listenForSearchCatalogButton);
	}

	/**
	 * Adds listener to the dropCourseButton
	 *
	 * @param listenForSearchCatalogButton listener for the button
	 */
	public void addDropCourseListener(ActionListener listenForSearchCatalogButton) {
		dropCourseButton.addActionListener(listenForSearchCatalogButton);
	}

	/**
	 * Adds listener to the browseCatalogButton
	 *
	 * @param listenForSearchCatalogButton listener for the button
	 */
	public void addBrowseCatalogListener(ActionListener listenForSearchCatalogButton) {
		browseCatalogButton.addActionListener(listenForSearchCatalogButton);
	}

	/**
	 * Adds listener to the searchCatalogButton
	 *
	 * @param listenForSearchCatalogButton listener for the button
	 */
	public void addSearchCatalogListener(ActionListener listenForSearchCatalogButton) {
		searchCatalogButton.addActionListener(listenForSearchCatalogButton);
	}

	/**
	 * Adds listener to the logoutButton
	 *
	 * @param listenForSearchCatalogButton listener for the button
	 */
	public void addLogoutListener(ActionListener listenForSearchCatalogButton) {
		logoutButton.addActionListener(listenForSearchCatalogButton);
	}
}
