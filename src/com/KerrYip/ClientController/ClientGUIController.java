package com.KerrYip.ClientController;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.KerrYip.ClientView.AdminMenuPanel;
import com.KerrYip.ClientView.BrowseCatalogPanel;
import com.KerrYip.ClientView.LoginSelectPanel;
import com.KerrYip.ClientView.MainView;
import com.KerrYip.ClientView.StudentMenuPanel;
import com.KerrYip.Model.Course;
import com.KerrYip.Model.CourseOffering;
import com.KerrYip.Model.Registration;
import com.KerrYip.Model.Student;

/**
 * This class is used to communicate between the GUI and the
 * ClientCommunicationController
 */
public class ClientGUIController {

	// the other controllers on the client side
	private ClientCommunicationController communicate;

	// the frame that is used for the GUI
	private MainView frame;

	// width and height of the frame
	private int width;
	private int height;

	/**
	 * Constructor for the ClientGUIController
	 * 
	 * @param width  the width of the frame
	 * @param height the height of the frame
	 * @param port   the port number that will connect with server
	 */
	public ClientGUIController(int width, int height, int port) {
		// generating controller for socket connections
		communicate = new ClientCommunicationController("localhost", port);

		// generating the frame
		frame = new MainView(width, height);

		// generating all panels that will be used by the frame
		loginSelectSetup();
		studentMenuSetup();
		adminMenuSetup();
		browseCatalogSetup();

		// adds listener for when the 'X' is pressed
		frame.addWindowListener(new MyWindowListener());

		// Starts the GUI, makes the frame visible and start on the login selection
		// screen
		frame.start();
	}

	/**
	 * Creates the JPanel for the login selection and adds all the buttons and adds
	 * it to the frame
	 */
	public void loginSelectSetup() {
		// adding panel to frame
		frame.setLoginSelect(new LoginSelectPanel(getWidth(), getHeight()));

		// adding button listeners to panel
		frame.getLoginSelect().addStudentLoginListener(new StudentLoginListener());
		frame.getLoginSelect().addAdminLoginListener(new AdminLoginListener());
		frame.getLoginSelect().addQuitLoginListener(new QuitLoginListener());

		// adding panel to card Layout for switching between panels
		frame.addCard(frame.getLoginSelect(), "Login Select");
	}

	/**
	 * Creates the JPanel for the student menu and adds all the buttons and adds it
	 * to the frame
	 */
	public void studentMenuSetup() {
		// adding panel to frame
		frame.setStudentMenu(new StudentMenuPanel(getWidth(), getHeight()));

		// adding button listeners to panel
		frame.getStudentMenu().addEnrollCourseListener(new StudentEnrollCourseListener());
		frame.getStudentMenu().addDropCourseListener(new StudentDropCourseListener());
		frame.getStudentMenu().addBrowseCatalogListener(new StudentBrowseCatalogListener());
		frame.getStudentMenu().addSearchCatalogListener(new SearchCatalogListener());
		frame.getStudentMenu().addLogoutListener(new LogoutListener());

		// adding panel to card Layout for switching between panels
		frame.addCard(frame.getStudentMenu(), "Student Menu");
	}

	/**
	 * Creates the JPanel for the admin menu and adds all the buttons and adds it to
	 * the frame
	 */
	public void adminMenuSetup() {
		// adding panel to frame
		frame.setAdminMenu(new AdminMenuPanel(getWidth(), getHeight()));

		// adding button listeners to panel
		frame.getAdminMenu().addAddCourseToCatalogListener(new AddCourseToCatalogListener());
		frame.getAdminMenu().addRemoveCourseFromCatalogListener(new RemoveCourseFromCatalogListener());
		frame.getAdminMenu().addSearchCatalogListener(new SearchCatalogListener());
		frame.getAdminMenu().addViewStudentEnrolledCoursesListener(new AdminViewEnrolledCoursesListener());
		frame.getAdminMenu().addAddStudentListener(new AddStudentListener());
		frame.getAdminMenu().addRunCoursesListener(new RunCoursesListener());
		frame.getAdminMenu().addLogoutListener(new LogoutListener());

		// adding panel to card Layout for switching between panels
		frame.addCard(frame.getAdminMenu(), "Admin Menu");
	}

	/**
	 * Creates the JPanel for viewing the course catalog and adds all the buttons
	 * and adds it to the frame
	 */
	public void browseCatalogSetup() {
		frame.setBrowseCatalog(new BrowseCatalogPanel(getWidth(), getHeight()));
		frame.getBrowseCatalog().addBackMenuListener(new BackStudentMenu());
		frame.addCard(frame.getBrowseCatalog(), "Browse Catalog");
	}

	/**
	 * Listens for when the button to login as a student as been pressed. Prompts
	 * the user for their ID, if login in successful takes them to the student menu
	 * other login fails and they remain at the login selection
	 */
	class StudentLoginListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// prompts user for student id
			String studentID = JOptionPane.showInputDialog("Please enter the student's id");
			if (studentID == null) {
				// cancel is pressed, return to login selection
				frame.show("Login Select");
			} else {
				// ok is pressed, check if login is valid
				System.out.println("student login");
				String message = communicate.communicateLogin("student login", studentID);
				ArrayList<Registration> registrationList = communicate.receiveRegistrationList();
				if (message.equals("login failed")) {
					// login in unsuccessful, take back to login selection
					JOptionPane.showMessageDialog(null, "Login Unsuccessful: Could not locate ID");
					frame.show("Login Select");

				} else {
					// login is successful, take to student menu
					frame.getStudentMenu().updateTitle(message);
					frame.getStudentMenu().updateEnrolledCourse(registrationList);
					frame.show("Student Menu");
				}
			}
		}
	}

	/**
	 * Listens for when the button to login as a admin as been pressed. Prompts the
	 * user for their ID, if login in successful takes them to the admin menu other
	 * login fails and they remain at the login selection
	 */
	class AdminLoginListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// prompts user for admin id
			String adminID = JOptionPane.showInputDialog("Please enter the admin's id (lets make \"admin\" the id");
			if (adminID == null) {
				// cancel is pressed, return to login selection
				frame.show("Login Select");
			} else {
				// ok is pressed, check if login is valid
				System.out.println("admin login");
				String message = communicate.communicateLogin("admin login", adminID);
				if (message.equals("login successful")) {
					// login is successful, take to admin menu
					ArrayList<Course> catalog = communicate.communicateGetCatalog();
					frame.getAdminMenu().updateCourse(catalog);
					frame.show("Admin Menu");
				} else {
					// login in unsuccessful, take back to login selection
					JOptionPane.showMessageDialog(null, "Login Unsuccessful: Could not locate ID");
					frame.show("Login Select");
				}
			}
		}
	}

	/**
	 * Listens for when the button the quit the program is pressed. Whens pressed
	 * closes all socket connections then shuts the program down
	 */
	class QuitLoginListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("QUIT");
			communicate.communicateQuit(); // closes socket connections
			System.exit(0);
		}
	}

	/**
	 * Listens for when the button for a course to enroll is a course has been
	 * pressed. When pressed prompts the user to enter their course and sends to
	 * server to be added Displays a message in enrollment was successful or not
	 */
	class StudentEnrollCourseListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			//The following creates the prompt that the user will input the desired input into
			//creating panels and titles
			JPanel enrollPanel = new JPanel();
			JLabel enrollTitle = new JLabel("Please enter the course you would like to enroll in");
			JPanel input = new JPanel();

			//The first row of inputs with course name and number desired
			input.add(new JLabel("Course Name:"));
			JTextField courseName = new JTextField(10);
			input.add(courseName);
			input.add(new JLabel("Course Number:"));
			JTextField courseNumber = new JTextField(5);
			input.add(courseNumber);

			//second row of inputs with the lecture number desired
			JPanel input2 = new JPanel();
			input2.add(new JLabel("Lecture Number:"));
			JTextField lectureNumber = new JTextField(5);
			input2.add(lectureNumber);

			//places them all into final panel for the user prompt
			enrollPanel.setLayout(new BorderLayout());
			enrollPanel.add("North", enrollTitle);
			enrollPanel.add("Center", input);
			enrollPanel.add("South", input2);

			try {
				//prompts the user for their input and records what button has been pressed
				int result = JOptionPane.showOptionDialog(null, enrollPanel, "Enroll In New Course",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);

				if (result == JOptionPane.OK_OPTION) {
					//If the OK button is selected then it will send the data to enroll into a course and receive
					// the necessary data
					Course tempCourse = new Course(courseName.getText(), Integer.parseInt(courseNumber.getText()));
					String message = communicate.communicateEnrollCourse(tempCourse,
							lectureNumber.getText());
					ArrayList<Registration> registrationList = communicate.receiveRegistrationList();

					if (message.equals("enroll successful")) {
						//if enrollment was successful update the studentMenu and display success message
						frame.getStudentMenu().updateEnrolledCourse(registrationList);
						frame.show("Student Menu");
						JOptionPane.showMessageDialog(null, "Enrollment was successful");
					} else {
						//if enrollment failed display fail message
						JOptionPane.showMessageDialog(null, "Enrollment was unsuccessful");
					}

				}
			} catch (NumberFormatException nfe) {
				//if an invalid input was made (words placed into number sections)
				JOptionPane.showMessageDialog(null, "Invalid input");
			}
		}
	}

	/**
	 * Listens for when the button for a course to drop a course has been pressed.
	 * When pressed prompts the user to enter their course and sends to server to be
	 * added Displays a message if dropping the course was successful or not
	 */
	class StudentDropCourseListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			//The following creates the prompt that the user will input the desired input into
			//creating panels and titles
			JPanel enrollPanel = new JPanel();
			JLabel enrollTitle = new JLabel("Please enter the course you would like to drop");
			JPanel input = new JPanel();

			//creates inputs with course name and number desired
			input.add(new JLabel("Course Name:"));
			JTextField courseName = new JTextField(10);
			input.add(courseName);
			input.add(new JLabel("Course Number:"));
			JTextField courseNumber = new JTextField(15);
			input.add(courseNumber);

			//places them all into final panel for the user prompt
			enrollPanel.setLayout(new BorderLayout());
			enrollPanel.add("North", enrollTitle);
			enrollPanel.add("Center", input);

			try {
				//prompts the user for their input and records what button has been pressed
				int result = JOptionPane.showOptionDialog(null, enrollPanel, "Drop a Course",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);

				if (result == JOptionPane.OK_OPTION) {
					//If the OK button is selected then it will send the data to drop a course and receive
					// the necessary data
					Course tempCourse = new Course(courseName.getText(), Integer.parseInt(courseNumber.getText()));
					String message = communicate.communicateDropCourse(tempCourse);
					ArrayList<Registration> registrationList = communicate.receiveRegistrationList();

					if (message.equals("drop successful")) {
						frame.getStudentMenu().updateEnrolledCourse(registrationList);
						frame.show("Student Menu");
						JOptionPane.showMessageDialog(null, "Drop was successful");
					} else {
						//if enrollment failed display fail message
						JOptionPane.showMessageDialog(null, "Drop was unsuccessful");
					}
				}
			} catch (NumberFormatException nfe) {
				//if an invalid input was made (words placed into number sections)
				JOptionPane.showMessageDialog(null, "Invalid input");
			}
		}
	}

	/**
	 * Listens for when the view all course catalog button has been pressed Prompts
	 * server for catalog list and displays on a new panel
	 */
	class StudentBrowseCatalogListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			//Gets the catalog
			ArrayList<Course> catalog = communicate.communicateGetCatalog();
			//update browse catalog screen and switch to ti
			frame.getBrowseCatalog().updateCatalog(catalog);
			frame.show("Browse Catalog");
		}
	}

	/**
	 * Listens for when the search catalog button has been pressed Prompts user for
	 * the course they are searching for and sends it to server if the result is
	 * found display, if not display that course isn't found
	 */
	class SearchCatalogListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			//The following creates the prompt that the user will input the desired input into
			//creating panels and titles
			JPanel dropPanel = new JPanel();
			JLabel dropTitle = new JLabel("Please enter the course you would like to search for");
			JPanel input = new JPanel();

			//creates inputs with course name and number desired
			input.add(new JLabel("Course Name:"));
			JTextField courseName = new JTextField(10);
			input.add(courseName);
			input.add(new JLabel("Course Number:"));
			JTextField courseNumber = new JTextField(5);
			input.add(courseNumber);

			//places them all into final panel for the user prompt
			dropPanel.setLayout(new BorderLayout());
			dropPanel.add("North", dropTitle);
			dropPanel.add("Center", input);

			try {
				//prompts the user for their input and records what button has been pressed
				int result = JOptionPane.showOptionDialog(null, dropPanel, "Search for a Course",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);

				if (result == JOptionPane.OK_OPTION) {
					//If the OK button is selected then it will send the data to search for the course and receive
					//the necessary data
					Course tempCourse = new Course(courseName.getText(), Integer.parseInt(courseNumber.getText()));
					String message = communicate.communicateSearchCourse(tempCourse);
					System.out.println(message);
					if (message.equals("Search completed")) {
						//displays the course is not found
						JOptionPane.showMessageDialog(null, "Course was not found");
					} else {
						//displays the course converted to string
						JOptionPane.showMessageDialog(null, message);
					}
				}
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null, "Invalid input");
			}
		}
	}

	/**
	 * Listens for when the logout button is pressed and takes the user back to the
	 * login selection
	 */
	class LogoutListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			//switches to login select and sends to server that we are logging out
			frame.show("Login Select");
			communicate.communicateLogout();
		}
	}

	/**
	 * listens for when the back button is pressed while logged in as a student
	 * takes the user back to the student menu
	 */
	class BackStudentMenu implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			//switches to student menu
			frame.show("Student Menu");
		}
	}

	/**
	 * Listens for when the add course to catalog button is pressed Prompts user for
	 * information of the new course and sends it to server message is displayed if
	 * the course was successfully added or not
	 */
	class AddCourseToCatalogListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			//The following creates the prompt that the user will input the desired input into
			//creating panels and titles
			JPanel addPanel = new JPanel();
			JLabel addTitle = new JLabel("Please enter the course name you would like to add");
			JPanel input = new JPanel();

			//creates inputs with course name and number desired
			input.add(new JLabel("Course Name:"));
			JTextField courseName = new JTextField(10);
			input.add(courseName);
			input.add(new JLabel("Course Number:"));
			JTextField courseNumber = new JTextField(5);
			input.add(courseNumber);

			//places them all into final panel for the user prompt
			addPanel.setLayout(new BorderLayout());
			addPanel.add("North", addTitle);
			addPanel.add("Center", input);

			// creates for adding course offering
			JPanel offeringPanel = new JPanel();
			JLabel offeringTitle = new JLabel("Please enter the course offering you would like to add");
			JPanel offeringInput = new JPanel();

			//creates inputs with course name and number desired
			offeringInput.add(new JLabel("New Section Number:"));
			JTextField secNum = new JTextField(3);
			secNum.setEditable(false);
			offeringInput.add(secNum);
			offeringInput.add(new JLabel("Section Cap:"));
			JTextField secCap = new JTextField(5);
			offeringInput.add(secCap);

			//places them all into final panel for the user prompt
			offeringPanel.setLayout(new BorderLayout());
			offeringPanel.add("North", offeringTitle);
			offeringPanel.add("Center", offeringInput);

			//options for buttons that are adding course offering
			Object[] options = { "Add another Course Offering", "Complete", "Cancel" };

			// prompts user for the course
			try {
				//prompts user for the course
				int result = JOptionPane.showOptionDialog(null, addPanel, "Add a Course", JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.PLAIN_MESSAGE, null, null, null);

				if (result == JOptionPane.OK_OPTION) {
					//if the ok button is pressed prompt the user for course offering
					ArrayList<CourseOffering> courseOfferings = new ArrayList<CourseOffering>();
					int offeringResult = -1;
					int secNumberCounter = 1;

					//if add another offering is pressed, loop until complete it pressed
					while (offeringResult != JOptionPane.NO_OPTION) {
						secNum.setText(secNumberCounter + "");
						//prompt user for course offering
						offeringResult = JOptionPane.showOptionDialog(null, offeringPanel, "Add a Course Offering",
								JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
						courseOfferings.add(new CourseOffering(secNumberCounter, Integer.parseInt(secCap.getText())));
						secNumberCounter++;
					}
					if (offeringResult == JOptionPane.CANCEL_OPTION) {
						return; //if cancel is pressed stop the process
					}
					//send course and course offering to server
					Course tempCourse = new Course(courseName.getText(), Integer.parseInt(courseNumber.getText()));
					String message = communicate.communicateAddCourse(tempCourse, courseOfferings);
					System.out.println(message);

					if (message.equals("Course added")) {
						//if course is added then update menu and display message
						ArrayList<Course> catalog = communicate.communicateGetCatalog();
						frame.getAdminMenu().updateCourse(catalog);
						JOptionPane.showMessageDialog(null, "Course was successfully added");
					} else {
						//if course is not added then display message that it wasn't successful
						JOptionPane.showMessageDialog(null, "Course could not be added");
					}
				}
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null, "Invalid input");
			}
		}
	}

	/**
	 * Listens for when the remove course from catalog button is pressed Prompts the
	 * user for the course name and sends it to server message is displayed if the
	 * course was successfully removed or not
	 */
	class RemoveCourseFromCatalogListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			//The following creates the prompt that the user will input the desired input into
			//creating panels and titles
			JPanel dropPanel = new JPanel();
			JLabel dropTitle = new JLabel("Please enter the course you would like to remove");
			JPanel input = new JPanel();

			//creates inputs with course name and number desired
			input.add(new JLabel("Course Name:"));
			JTextField courseName = new JTextField(10);
			input.add(courseName);
			input.add(new JLabel("Course Number:"));
			JTextField courseNumber = new JTextField(5);
			input.add(courseNumber);

			//places them all into final panel for the user prompt
			dropPanel.setLayout(new BorderLayout());
			dropPanel.add("North", dropTitle);
			dropPanel.add("Center", input);

			// prompts user for the course
			try {
				//prompts the user for their input and records what button has been pressed
				int result = JOptionPane.showOptionDialog(null, dropPanel, "Remove a Course",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);

				if (result == JOptionPane.OK_OPTION) {
					//If the OK button is selected then it will send the data to remove a course and receive
					// the necessary data
					Course tempCourse = new Course(courseName.getText(), Integer.parseInt(courseNumber.getText()));
					String message = communicate.communicateRemoveCourse(tempCourse);
					System.out.println(message);
					if (message.equals("course removed")) {
						//course was removed and catalog is updated on menu and message is displayed
						ArrayList<Course> catalog = communicate.communicateGetCatalog();
						frame.getAdminMenu().updateCourse(catalog);
						JOptionPane.showMessageDialog(null, "Course was successfully removed");
					} else {
						//course not removed and message is displayed
						JOptionPane.showMessageDialog(null, "Course could not be removed");
					}
				}
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null, "Invalid input");
			}
		}
	}

	/**
	 * Listens for when the view Student's enrolled courses button is pressed
	 * Prompts the user for the student ID and sends it to server enrolled courses
	 * by that student is displays courses if the ID is found if not displays that
	 * it could not find that student
	 */
	class AdminViewEnrolledCoursesListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			//If the OK button is selected then it will send the data to remove a course and receive
			// the necessary data
			String studentID = JOptionPane.showInputDialog("Please enter the student's id");
			if(studentID != null) {
				String message = communicate.communicateGetStudentsRegistrationList(studentID);
				ArrayList<Registration> registration = communicate.receiveRegistrationList();
				if (message.equals("could not find student")) {
					//couldn't find the student
					JOptionPane.showMessageDialog(null, "Unable to find the Student");
				} else {
					//displays all student registration
					String temp = "Student's Enrolled Courses:\n";
					for (int i = 0; i < registration.size(); i++) {
						temp += registration.get(i);
					}
					JOptionPane.showMessageDialog(null, temp);
				}
			}
		}
	}

	/**
	 * Listens for when the Add New Student button is pressed Prompts the user for
	 * the student info and sends it to server to be added Message is returns if
	 * successful or not
	 */
	class AddStudentListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			//prompt to enter the student's ID
			String studentName = JOptionPane.showInputDialog("Please enter the student's name");
			if(studentName != null) {
				//sends name and instruction
				String message = communicate.communicateAddStudent(studentName);
				if (message == "failed to add") {
					//unable to make student, display message
					JOptionPane.showMessageDialog(null, "Unable to create new Student");
				} else {
					//displays that student was added and their new ID
					JOptionPane.showMessageDialog(null, "Added new Student. " + studentName + "'s new ID is: " + message);
				}
			}
		}
	}

	/**
	 * Listens for when the Run Courses button is pressed Attempts to run all
	 * courses and message is displayed on what courses ran successfully and what
	 * did not
	 */
	class RunCoursesListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			//tells server to run courses and displays all the courses running or not in one message string
			String message = communicate.communicateRunCourses();
			JOptionPane.showMessageDialog(null, message);
		}
	}

	/**
	 * Listens for when the 'X' button at the top of the frame is pressed Whens
	 * pressed closes all socket connections then shuts the program down
	 */
	class MyWindowListener implements WindowListener {

		public void windowClosing(WindowEvent arg0) {
			System.out.println("QUIT");
			communicate.communicateQuit();
			System.exit(0);

		}

		public void windowOpened(WindowEvent arg0) {
		}

		public void windowClosed(WindowEvent arg0) {
		}

		public void windowIconified(WindowEvent arg0) {
		}

		public void windowDeiconified(WindowEvent arg0) {
		}

		public void windowActivated(WindowEvent arg0) {
		}

		public void windowDeactivated(WindowEvent arg0) {
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
