package com.KerrYip.ClientView;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * The Login Selection Panel which allows the user to selection which type of
 * login they would like to do
 * 
 * @author Tyler Yip
 * @author Will Kerr
 */
public class LoginSelectPanel extends JPanel {
	private int width, height;
	private ImageIcon logo;
	private JButton studentButton, adminButton, quitButton;
	private JLabel loginLabel, logoLabel;

	/**
	 * Constructs the Login Selection Panel
	 *
	 * @param width  width of the Frame the panel will be in
	 * @param height width of the Frame the panel will be in
	 */
	public LoginSelectPanel(int width, int height) {
		this.width = width;
		this.height = height;

		//sets color of the panel
		Color redColor = Color.decode("#800020");
		formatButtons();
		setBorder(new LineBorder(redColor,200));


		// This is the title Panel
		JPanel titlePanel = new JPanel(new BorderLayout());
		titlePanel.setBackground(redColor);
		loginLabel = new JLabel("Please select login type:",SwingConstants.CENTER);
		loginLabel.setFont(new Font("Dialog",Font.BOLD,25));
		loginLabel.setForeground(Color.white);
		titlePanel.add("Center",loginLabel);

		// This is the logo Panel
		JPanel logoPanel = new JPanel(new BorderLayout());
		logoPanel.setBackground(redColor);
		logo = new ImageIcon("LogoText.png");
		logoLabel = new JLabel(logo);
		logoPanel.setLayout(new BorderLayout());
		logoPanel.add("Center",logoLabel);

		// This is the button panel
		JPanel buttonPanel = new JPanel(new BorderLayout());
		JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER,30,10));
		buttonPanel.setBackground(redColor);
		buttons.setBackground(redColor);

		// Add all the buttons into the panel
		buttons.add(studentButton);
		buttons.add(adminButton);
		buttons.add(quitButton);
		buttonPanel.add("Center",buttons);

		setLayout(new BorderLayout());
		add("North",logoPanel);
		add("Center",titlePanel);
		add("South",buttonPanel);
	}

	/**
	 * Formats the buttons
	 */
	public void formatButtons(){
		Color goldColor = Color.decode("#CAB15E");
		studentButton = new JButton("Student Login");
		adminButton = new JButton("Admin Login");
		quitButton = new JButton("Quit Login");
		studentButton.setIcon(new ImageIcon("StudentLogin.png"));
		adminButton.setIcon(new ImageIcon("AdminLogin.png"));
		quitButton.setIcon(new ImageIcon("Logout.png"));
		studentButton.setSize(new Dimension(500,100));
		adminButton.setSize(new Dimension(100,100));
		quitButton.setSize(new Dimension(100,100));
		studentButton.setFont(new Font("Dialog",Font.BOLD,20));
		adminButton.setFont(new Font("Dialog",Font.BOLD,20));
		quitButton.setFont(new Font("Dialog",Font.BOLD,20));
		studentButton.setBackground(goldColor);
		adminButton.setBackground(goldColor);
		quitButton.setBackground(goldColor);
	}

	/**
	 * Adds listener for the studentButton
	 *
	 * @param listenForStudentButton listener for the button
	 */
	public void addStudentLoginListener(ActionListener listenForStudentButton) {
		studentButton.addActionListener(listenForStudentButton);
	}

	/**
	 * Adds listener for the adminButton
	 *
	 * @param listenForAdminButton listener for the button
	 */
	public void addAdminLoginListener(ActionListener listenForAdminButton) {
		adminButton.addActionListener(listenForAdminButton);
	}

	/**
	 * Adds listener for the quitButton
	 *
	 * @param listenForQuitButton listener for the button
	 */
	public void addQuitLoginListener(ActionListener listenForQuitButton) {
		quitButton.addActionListener(listenForQuitButton);
	}

}
