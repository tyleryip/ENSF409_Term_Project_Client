package com.KerrYip.ClientView;

import java.awt.*;
import javax.swing.*;

/**
 * The Frame of the GUI which has all the panels and is able to switch between
 * them on the frame
 * 
 * @author Tyler Yip
 * @author Will Kerr
 */
public class MainView extends JFrame {

	// dimensions of the frame
	private final int width, height;

	// panels of the frame
	private LoginSelectPanel loginSelect;
	private StudentMenuPanel studentMenu;
	private AdminMenuPanel adminMenu;
	private BrowseCatalogPanel browseCatalog;

	// card layout which allows for switching between the panels above
	private JPanel cardList;
	private CardLayout cardControl;

	// Color of background of GUI and Buttons
	private static Color redColor = Color.decode("#800020");
	private static Color goldColor = Color.decode("#CAB15E");

	/**
	 * Constructor for the MainView which generates the frame that the GUI will be
	 * on
	 * 
	 * @param height height of the frame
	 * @param width  width of the frame
	 */
	public MainView(int height, int width) {

		// sets dimensions
		this.height = height;
		this.width = width;
		this.setSize(height, width);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// creates format for the GUI
		formatManager();

		// JFrame Icon
		ImageIcon img = new ImageIcon("LogoPic.png");
		setIconImage(img.getImage());

		// creates card layout to traverse all the panels
		cardList = new JPanel();
		cardList.setLayout(new CardLayout());
		cardControl = (CardLayout) (cardList.getLayout());
	}

	/**
	 * Shows the specified panel on the frame
	 * 
	 * @param label The name of the desired panel to be shown
	 */
	public void show(String label) {
		getCardControl().show(getCardList(), label);
		setTitle(label);
	}

	/**
	 * Adds panel to the cardList
	 * 
	 * @param pane  panel to be added
	 * @param label name the panel will be referred to as
	 */
	public void addCard(JPanel pane, String label) {
		getCardList().add(pane, label);
	}

	/**
	 * Starts the GUI by making the frame visible and starting on the login
	 * selection
	 */
	public void start() {
		add(cardList);
		show("Login Select");
		setVisible(true);
	}

	/**
	 * Formats everything for the entire GUI
	 */
	@SuppressWarnings("static-access")
	public void formatManager() {
		UIManager UI = new UIManager();
		// Set look and feel to windows
		try {
			UI.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Formats JPanel
		UI.put("Panel.background", redColor);

		// Formats JLabel
		UI.put("Label.font", new Font("Dialog", Font.BOLD, 20));
		UI.put("Label.foreground", Color.white);

		// Formats JTextArea
		UI.put("TextArea.background", Color.decode("#F0F0F0"));
		UI.put("TextArea.font", new Font("Dialog", Font.BOLD, 20));

		// Formats JButton
		UI.put("Button.background", goldColor);
		UI.put("Button.font", new Font("Dialog", Font.BOLD, 20));

		// Formats JOptionPane
		UI.put("OptionPane.background", redColor);
		UI.put("OptionPane.messageFont", new Font("Dialog", Font.BOLD, 17));
		UI.put("OptionPane.messageForeground", Color.white);
		UI.put("OptionPane.buttonFont", new Font("Dialog", Font.BOLD, 17));
		UI.put("OptionPane.font", new Font("Dialog", Font.BOLD, 17));

		// Formats JTextField
		UI.put("TextField.font", new Font("Dialog", Font.BOLD, 15));
		UI.put("TextField.background", Color.decode("#F0F0F0"));

	}

	// getters and setters
	public JPanel getCardList() {
		return cardList;
	}

	public CardLayout getCardControl() {
		return cardControl;
	}

	public LoginSelectPanel getLoginSelect() {
		return loginSelect;
	}

	public StudentMenuPanel getStudentMenu() {
		return studentMenu;
	}

	public void setLoginSelect(LoginSelectPanel loginSelect) {
		this.loginSelect = loginSelect;
	}

	public void setStudentMenu(StudentMenuPanel studentMenu) {
		this.studentMenu = studentMenu;
	}

	public AdminMenuPanel getAdminMenu() {
		return adminMenu;
	}

	public void setAdminMenu(AdminMenuPanel adminMenu) {
		this.adminMenu = adminMenu;
	}

	public BrowseCatalogPanel getBrowseCatalog() {
		return browseCatalog;
	}

	public void setBrowseCatalog(BrowseCatalogPanel browseCatalog) {
		this.browseCatalog = browseCatalog;
	}

}
