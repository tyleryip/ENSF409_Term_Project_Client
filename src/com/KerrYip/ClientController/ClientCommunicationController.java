package com.KerrYip.ClientController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

//import com.KerrYip.ClientModel.Course;
//import com.KerrYip.ClientModel.Student;
import com.KerrYip.Model.Course;
import com.KerrYip.Model.CourseOffering;
import com.KerrYip.Model.Registration;
import com.KerrYip.Model.Student;

import java.io.*;

import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * This class is primarily used to communicate with the server via sockets
 * 
 * @author Tyler Yip
 * @author Will Kerr
 * @version 2.0
 * @since 04/07/20
 *
 */
public class ClientCommunicationController {

	private Socket aSocket;
	private ObjectInputStream fromServer;
	private ObjectOutputStream toServer;

	/**
	 * Constructor for the class ClientCommunicationController opens a connection on
	 * 
	 * @param serverName the name of the server
	 * @param port       the port of the server
	 */
	public ClientCommunicationController(String serverName, int port) {
		try {
			// create socket
			aSocket = new Socket(serverName, port);

			// Socket object streams
			// For some reason, the streams must be initialized in this order or they will
			// fail
			toServer = new ObjectOutputStream(aSocket.getOutputStream());
			fromServer = new ObjectInputStream(aSocket.getInputStream());

		} catch (UnknownHostException e) {
			System.err.println("Error: could not find a host with the name: " + serverName);
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Error: I/O socket error");
			e.printStackTrace();
		}
	}

	// The following helper methods are for sending or receiving something from the
	// server

	/**
	 * This method is a helper method to make allow strings to be read from the
	 * output stream of the server more easily, used for receiving messages
	 * 
	 * @return the string sent by the server
	 */
	private String readString() {
		String input = "";
		try {
			input = (String) fromServer.readObject();
		} catch (ClassNotFoundException e) {
			System.err.println("Error: could not convert the object to a string");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return input;
	}

	/**
	 * This method is a helper method to allow sending strings to the input stream
	 * of the server to be made more easily, used for sending commands to the server
	 *
	 * @param toSend the string to send to server
	 */
	private void writeString(String toSend) {
		try {
			toServer.writeObject(toSend);
		} catch (IOException e) {
			System.err.println("Error: unable to write string to an object");
			e.printStackTrace();
		}
	}

	/**
	 * Receives a course from the server
	 * 
	 * @return the course from the server
	 */
	private Course receiveCourse() {
		Course course = null;
		try {
			course = (Course) fromServer.readObject();
		} catch (ClassNotFoundException e) {
			System.err.println("Error: could not convert the object to a string");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return course;
	}

	/**
	 * Sends a course to the server
	 * 
	 * @param course The course getting sent
	 */
	private void sendCourse(Course course) {
		try {
			toServer.writeObject(course);
			toServer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sends a course offering array one course offering at a time to the server,
	 * with an null sent at the end
	 * 
	 * @param courseOfferings The course offering array getting sent
	 */
	private void sendCourseOfferingListAsString(ArrayList<CourseOffering> courseOfferings) {
		String st = courseOfferings.get(0).getSecCap() + "";
		for (int i = 1; i < courseOfferings.size(); i++) {
			st += ";" + courseOfferings.get(i).getSecCap();
		}
		writeString(st);
	}

	/**
	 * Receives a Course array one course at a time from the server, stopping when a
	 * null is sent
	 * 
	 * @return The courses all in one array from the server
	 */
	private ArrayList<Course> receiveCourseList() {
		ArrayList<Course> courseList = new ArrayList<Course>();
		try {
			Course course = (Course) fromServer.readObject();
			while (course != null) {
				courseList.add(course);
				course = (Course) fromServer.readObject();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return courseList;
	}

	// The following method is called by GUI to retrieve the registration list
	/**
	 * Receives a Registration array one registration at a time to the server,
	 * stopping when a null sent at the end
	 * 
	 * @return The registration all in one array from the server
	 */
	public ArrayList<Registration> receiveRegistrationList() {
		ArrayList<Registration> registrationList = new ArrayList<Registration>();
		try {
			Registration registration = (Registration) fromServer.readObject();
			while (registration != null) {
				registrationList.add(registration);
				registration = (Registration) fromServer.readObject();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return registrationList;
	}

	// The following methods are methods called by GUIController that call
	// the helper methods above to communicate with the server
	/**
	 * Sends the type of login and id of the person logging in and returns if the
	 * login was successful or not
	 * 
	 * @param instruction The type of login
	 * @param id          The id of the person logging in
	 * @return message if login was successful or not
	 */
	public String communicateLogin(String instruction, String id, String password) {
		writeString(instruction);
		writeString(id);
		writeString(password);
		return readString();
	}

	public String receiveStudentName() {
		return readString();
	}

	/**
	 * Sends an instruction to the Server to quit and closes sockets
	 */
	public void communicateQuit() {
		writeString("QUIT");
		closeConnection();
	}

	/**
	 * Sends the instruction, course with the name and number, and lecture number to
	 * the server to enroll student into the course. Sends back message if
	 * successful or not
	 * 
	 * @param course        Sends course with name and num desired to be enrolled in
	 * @param lectureNumber Sends desired lecture number
	 * @return message if enrollment was successful or not
	 */
	public String communicateEnrollCourse(Course course, String lectureNumber) {
		String message = "";
		writeString("enroll course");
		sendCourse(course);
		writeString(lectureNumber);
		message = readString();
		return message;
	}

	/**
	 * Sends the instruction and course with desired name and number to server and
	 * it sends back message if dropping the course was successful or not
	 * 
	 * @param course Sends course with name and num desired to be enrolled in
	 * @return message if dropping was successful or not
	 */
	public String communicateDropCourse(Course course) {
		String temp = "";
		writeString("drop course");
		sendCourse(course);
		temp = readString();

		return temp;
	}

	/**
	 * Sends an instruction to server and receives the course catalog
	 * 
	 * @return the course catalog in a Course ArrayList
	 */
	public ArrayList<Course> communicateGetCatalog() {
		writeString("browse courses");
		return receiveCourseList();
	}

	/**
	 * Sends an instruction and course with name and number and sends back the
	 * course if it was found and a message that it wasn't found otherwise
	 * 
	 * @param course The desired course to search for
	 * @return Returns the course in a string if found, otherwise returns a message
	 *         that the course wasn't found
	 */
	public String communicateSearchCourse(Course course) {
		Course courseResult = null;
		String message = "";
		writeString("search for course");
		sendCourse(course);
		message = readString();
		if (message.equals("course found")) {
			// if course found place read Course and turn into a string to return;
			courseResult = receiveCourse();
			message = courseResult.toString();
		} else {
			// if course not found read the fail statement
			message = readString();
		}
		return message;
	}

	/**
	 * Sends an instruction and course and all course offerings desired for the new
	 * course to the server and the server returns a message if course was added
	 * successfully or not
	 * 
	 * @param course          The desired course name and number to add
	 * @param courseOfferings The desired course offering to add
	 * @return message if the course was added successfully or not
	 */
	public String communicateAddCourse(Course course, ArrayList<CourseOffering> courseOfferings) {
		writeString("add course");
		sendCourse(course);
		sendCourseOfferingListAsString(courseOfferings);
		return readString();
	}

	/**
	 * Sends an instruction and Parent's course name and number and the desired
	 * Prereq's course name and number to the server and the server returns a
	 * message if course prereq was added successfully or not
	 * 
	 * @param parentNameNum The desired course name and number to add
	 * @param preReqNameNum The desired course offering to add
	 * @return message if the course was added successfully or not
	 */
	public String communicateAddPreReq(String parentNameNum, String preReqNameNum) {
		writeString("add prereq");
		writeString(parentNameNum);
		writeString(preReqNameNum);
		return readString();
	}

	/**
	 * Sends an instruction and desired course to the server and receives a message
	 * if the course was removed or not
	 * 
	 * @param course The desired course to remove
	 * @return message if course was removed or not
	 */
	public String communicateRemoveCourse(Course course) {
		writeString("remove course");
		sendCourse(course);
		return readString();
	}

	/**
	 * Sends an instruction and desired student's ID to server and receives message
	 * if student was found or not
	 * 
	 * @param studentID desired student's ID
	 * @return message if student was found or not
	 */
	public String communicateGetStudentsRegistrationList(String studentID) {
		writeString("admin view student courses");
		writeString(studentID);
		return readString();
	}

	/**
	 * Sends an instruction and the new student's name to server and returns message
	 * if the student was added or not
	 * 
	 * @param studentName The name of the new student
	 * @return Sends back message with new student ID if successfully added and
	 *         failure message if student was not added
	 */
	public String communicateAddStudent(String studentName, String password) {
		writeString("add student");
		writeString(studentName);
		writeString(password);
		return readString();
	}

	/**
	 * Sends an instruction to inform the server that user is logging out
	 */
	public void communicateLogout() {
		writeString("logout");
	}

	/**
	 * Sends an instruction and student's ID, the course and assigned grade to the
	 * server and the server sends message is assignment was successful or not
	 * 
	 * @param studentID     the student's ID
	 * @param courseNameNum the course name and number that the grade will be
	 *                      assigned for
	 * @param grade         Student's grade
	 * @return message if the course was added successfully or not
	 */
	public String communicateAssignGrade(String studentID, String courseNameNum, String grade) {
		writeString("assign grade");
		writeString(studentID);
		writeString(courseNameNum);
		writeString(grade);
		return readString();
	}

	/**
	 * Sends an instruction to server to run courses and sends back all the courses
	 * if they ran or not
	 * 
	 * @return String with all the courses if they will run or not
	 */
	public String communicateRunCourses() {
		writeString("run courses");
		return readString();
	}

	/**
	 * closes all the socket connections
	 */
	private void closeConnection() {
		try {
			fromServer.close();
			toServer.close();
		} catch (IOException e) {
			e.getStackTrace();
		}
	}
}
