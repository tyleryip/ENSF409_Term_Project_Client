package com.KerrYip.ClientView;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.LineBorder;

import com.KerrYip.Model.Course;

/**
 * The Panel for viewing all of the course catalog which will display all
 * courses in the catalog
 * 
 * @author Tyler Yip
 * @author Will Kerr
 */
public class BrowseCatalogPanel extends JPanel {
	// dimensions of JFrame
	private int width, height;

	// Buttons and Labels
	private JButton backButton;
	private JLabel browseCatalogLabel;

	// Scroll Text Field Area on the Panel
	private JTextArea dataText;

	// Color of background of GUI
	private static Color redColor = Color.decode("#800020");

	public BrowseCatalogPanel(int width, int height) {
		this.width = width;
		this.height = height;

		setBorder(new LineBorder(redColor, 10));

		// Buttons for the main window
		backButton = new JButton("Back");
		backButton.setIcon(new ImageIcon("Back.png"));

		// browse catalog title
		browseCatalogLabel = new JLabel();
		browseCatalogLabel.setText("Course Catalog");
		browseCatalogLabel.setFont(new Font("Dialog", Font.BOLD, 30));

		// browse catalog panels for formatting
		JPanel titlePanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));

		// Set up the layout of the main window
		setLayout(new BorderLayout(10, 10));

		// Add all the buttons into the panel
		titlePanel.add(browseCatalogLabel);

		// formats back button
		JPanel backPanel = new JPanel(new BorderLayout(0, 10));
		backPanel.add("East", backButton);
		buttonPanel.add(backPanel);

		// This is the data field that displays
		dataText = new JTextArea(20, 20);
		dataText.setLineWrap(true); // Allows text to wrap if it reaches the end of the line
		dataText.setWrapStyleWord(true); // text should wrap at word boundaries rather than character boundaries
		dataText.setEditable(false); // This ensure that the user cannot edit the data field
		dataText.setText(""); // This displays empty text in the field

		// Make the data field scroll-able if enough data fills the panel
		JScrollPane dataTextScrollPane = new JScrollPane(dataText);
		dataTextScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		// adding panels to appropriate quadrants
		add("North", titlePanel);
		add("South", buttonPanel);
		add("Center", dataTextScrollPane);

	}

	/**
	 * Updates the catalog on the Browse Catalog Menu
	 * 
	 * @param catalog The new catalog that will be displayed
	 */
	public void updateCatalog(ArrayList<Course> catalog) {
		String temp = "";
		for (int i = 0; i < catalog.size(); i++) {
			temp += catalog.get(i);
		}
		dataText.setText(temp);
	}

	/**
	 * Adds listener for the backButton
	 * 
	 * @param listenForBackMenuButton listener for the button
	 */
	public void addBackMenuListener(ActionListener listenForBackMenuButton) {
		backButton.addActionListener(listenForBackMenuButton);
	}

}
