package com.KerrYip.ClientView;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.LineBorder;

import com.KerrYip.Model.Course;

/**
 * The Panel for the Admin Menu which allows the admin to select what operations
 * they would like to do for the courses
 * 
 * @author Tyler Yip
 * @author Will Kerr
 */
public class AdminMenuPanel extends JPanel {
	private int width, height;
	private JButton searchCatalogButton, addCourseButton, addPreReqButton, removeCourseButton, viewStudentCoursesButton,
			addStudentButton, assignGradeButton, runButton, logoutButton;
	private JLabel adminMenuLabel;
	private JTextArea dataText;

	private static Color redColor = Color.decode("#800020");
	private static Color goldColor = Color.decode("#CAB15E");

	/**
	 * Constructs the Admin Menu Panel
	 *
	 * @param width  width of the Frame the panel will be in
	 * @param height width of the Frame the panel will be in
	 */
	public AdminMenuPanel(int width, int height) {
		this.width = width;
		this.height = height;

		setBorder(new LineBorder(redColor, 10));

		// admin menu title
		adminMenuLabel = new JLabel();
		adminMenuLabel.setText("Admin Menu");
		adminMenuLabel.setFont(new Font("Dialog", Font.BOLD, 30));

		// admin menu panels for formatting
		JPanel titlePanel = new JPanel();
		JPanel buttonPanel = formatButtons();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
		titlePanel.add(adminMenuLabel);

		// Set up the layout of the main window
		setLayout(new BorderLayout(10, 10));

		// Add all the buttons into the panel

		// the data field that displays
		dataText = new JTextArea((int) (width * 0.75), (int) (height * 0.75));
		dataText.setLineWrap(true); // Allows text to wrap if it reaches the end of the line
		dataText.setWrapStyleWord(true); // text should wrap at word boundaries rather than character boundaries
		dataText.setEditable(false); // This ensure that the user cannot edit the data field
		dataText.setText(""); // This displays empty text in the field

		JPanel coursePanel = new JPanel();
		coursePanel.setLayout(new BorderLayout());
		// Make the data field scroll-able if enough data fills the panel
		JScrollPane dataTextScrollPane = new JScrollPane(dataText);
		dataTextScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		coursePanel.add("Center", dataTextScrollPane);
		JPanel courseTitlePanel = new JPanel();
		courseTitlePanel.setBackground(Color.decode("#67001a"));
		JLabel courseTitle = new JLabel("Course Catalog View");
		courseTitlePanel.add(courseTitle);
		coursePanel.add("North", courseTitlePanel);

		JPanel logoutPanel = new JPanel(new BorderLayout(0, 10));
		logoutPanel.add("East", logoutButton);

		// adding panels to appropriate quadrants
		add("North", titlePanel);
		add("East", buttonPanel);
		add("Center", coursePanel);
		add("South", logoutPanel);

	}

	public JPanel formatButtons() {
		// Buttons for the main window
		JPanel buttonPanel = new JPanel(new BorderLayout(0, 10));
		JPanel buttons1 = new JPanel(new BorderLayout(0, 10));
		JPanel buttons2 = new JPanel(new BorderLayout(0, 10));
		JPanel buttons3 = new JPanel(new BorderLayout(0, 10));
		JPanel buttons4 = new JPanel(new BorderLayout(0, 10));
		JPanel buttons5 = new JPanel(new BorderLayout(0, 10));

		// add button
		searchCatalogButton = new JButton("Search for Course in Catalog");
		addCourseButton = new JButton("Add new course to the Catalog");
		addPreReqButton = new JButton("Add Pre-Requisite to Course");
		removeCourseButton = new JButton("Remove course from the Catalog");
		viewStudentCoursesButton = new JButton("View a Student's Courses");
		addStudentButton = new JButton("Add new student");
		assignGradeButton = new JButton("Assign Grade");
		runButton = new JButton("Run Courses");
		logoutButton = new JButton("Logout");

		// add logos to buttons
		searchCatalogButton.setIcon(new ImageIcon("Search.png"));
		addCourseButton.setIcon(new ImageIcon("Add.png"));
		addPreReqButton.setIcon(new ImageIcon("AddPreReq.png"));
		removeCourseButton.setIcon(new ImageIcon("Remove.png"));
		viewStudentCoursesButton.setIcon(new ImageIcon("ViewStudentCourses.png"));
		addStudentButton.setIcon(new ImageIcon("AddStudent.png"));
		assignGradeButton.setIcon(new ImageIcon("Grade.png"));
		runButton.setIcon(new ImageIcon("Run.png"));
		logoutButton.setIcon(new ImageIcon("Logout.png"));

		// add school logo
		// ImageIcon logo = new ImageIcon("resources\\LogoPic.png");
		// JLabel logoLabel = new JLabel(logo);

		// empty space
		JPanel emptySpace = new JPanel();
		emptySpace.setBorder(new LineBorder(redColor, 8));

		buttons1.add("North", emptySpace);
		buttons1.add("Center", addCourseButton);
		buttons1.add("South", addPreReqButton);
		buttons2.add("North", buttons1);
		buttons2.add("Center", removeCourseButton);
		buttons2.add("South", searchCatalogButton);
		buttons3.add("North", buttons2);
		buttons3.add("Center", viewStudentCoursesButton);
		buttons3.add("South", addStudentButton);
		buttons4.add("North", buttons3);
		buttons4.add("Center", assignGradeButton);
		buttons4.add("South", runButton);
		buttons5.add("North", buttons4);

		buttonPanel.add("North", buttons5);
		return buttonPanel;
	}

	public void updateCourse(ArrayList<Course> catalog) {
		String temp = "";
		for (int i = 0; i < catalog.size(); i++) {
			temp += catalog.get(i);
		}
		dataText.setText(temp);
	}

	/**
	 * Adds Listeners to the addCourseButton
	 * 
	 * @param listenForAddCourseToCatalogButton the listener for the button
	 */
	public void addAddCourseToCatalogListener(ActionListener listenForAddCourseToCatalogButton) {
		addCourseButton.addActionListener(listenForAddCourseToCatalogButton);
	}

	/**
	 * Adds Listeners to the addPreReqButton
	 *
	 * @param listenForAddPreReqToCourseButton the listener for the button
	 */
	public void addAddPreReqToCourseListener(ActionListener listenForAddPreReqToCourseButton) {
		addPreReqButton.addActionListener(listenForAddPreReqToCourseButton);
	}

	/**
	 * Adds Listeners to the removeCourseButton
	 * 
	 * @param listenForRemoveCourseFromCatalogButton the listener for the button
	 */
	public void addRemoveCourseFromCatalogListener(ActionListener listenForRemoveCourseFromCatalogButton) {
		removeCourseButton.addActionListener(listenForRemoveCourseFromCatalogButton);
	}

	/**
	 * Adds listeners to the searchCatalogButton
	 * 
	 * @param listenForSearchCatalogButton the listener for the button
	 */
	public void addSearchCatalogListener(ActionListener listenForSearchCatalogButton) {
		searchCatalogButton.addActionListener(listenForSearchCatalogButton);
	}

	/**
	 * Adds listeners to the viewStudentCoursesButton
	 * 
	 * @param listenForStudentEnrolledCoursesButton the listener for the button
	 */
	public void addViewStudentEnrolledCoursesListener(ActionListener listenForStudentEnrolledCoursesButton) {
		viewStudentCoursesButton.addActionListener(listenForStudentEnrolledCoursesButton);
	}

	/**
	 * Adds listeners to the addStudentButton
	 * 
	 * @param listenForAddStudentButton the listener for the button
	 */
	public void addAddStudentListener(ActionListener listenForAddStudentButton) {
		addStudentButton.addActionListener(listenForAddStudentButton);
	}

	/**
	 * Adds listeners to the assignGradeButton
	 *
	 * @param listenForAssignGradeButton the listener for the button
	 */
	public void addAssignGradeListener(ActionListener listenForAssignGradeButton) {
		assignGradeButton.addActionListener(listenForAssignGradeButton);
	}

	/**
	 * Adds listeners to the runButton
	 * 
	 * @param listenForRunCoursesButton the listener for the button
	 */
	public void addRunCoursesListener(ActionListener listenForRunCoursesButton) {
		runButton.addActionListener(listenForRunCoursesButton);
	}

	/**
	 * Adds listeners to the logoutButton
	 * 
	 * @param listenForLogoutButton the listener for the button
	 */
	public void addLogoutListener(ActionListener listenForLogoutButton) {
		logoutButton.addActionListener(listenForLogoutButton);
	}
}
