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
 * @author tyleryip
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

	public Course receiveCourse(){
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

	public void sendCourse(Course course){
		try {
			toServer.writeObject(course);
			toServer.flush();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Sends an instruction to the Server and receives the message back
	 *
	 * @param instruction The instruction the server will execute
	 * @return The message the server sends back
	 */
	public void communicateLogout(String instruction) {
		writeString(instruction);
	}

	/**
	 * Sends an instruction to the Server and receives the message back
	 *
	 * @param instruction The instruction the server will execute
	 * @return The message the server sends back
	 */
	public String communicateSendAndReceiveString(String instruction) {
		String message = null;
		writeString(instruction);
		message = readString();
		return message;
	}

	public void sendCourseOfferingList(ArrayList<CourseOffering> courseOfferings){
		try {
			for (CourseOffering co : courseOfferings) {
				toServer.writeObject(co);
				toServer.flush();
			}
			toServer.writeObject(null);
			toServer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Registration> receiveRegistrationList(){
		ArrayList<Registration> registrationList = new ArrayList<Registration>();
		try {
			Registration registration = (Registration)fromServer.readObject();
			while(registration != null){
				registrationList.add(registration);
				registration = (Registration)fromServer.readObject();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return registrationList;
	}

	public ArrayList<Course> receiveCourseList(){
		ArrayList<Course> courseList = new ArrayList<Course>();
		try {
			Course course = (Course)fromServer.readObject();
			while(course != null){
				courseList.add(course);
				course = (Course)fromServer.readObject();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return courseList;
	}

	public String communicateLogin(String instruction, String id) {
		String message = null;
		writeString(instruction);
		writeString(id);
		message = readString();
		return message;
	}

	/**
	 * Sends an instruction to the Server and receives the message back
	 *
	 * @param instruction The instruction the server will execute
	 * @param course      The course the server needs for the instruction
	 * @return The message the server sends back
	 */
	public String communicateSearchCourse(String instruction, Course course) {
		Course courseResult = null;
		String message = "";
		writeString(instruction);
		sendCourse(course);
		message = readString();
		if (message.equals("course found")) {
			courseResult = receiveCourse();
		} else {
			message = readString();
		}
		return message;
	}

	/**
	 * Sends an instruction to the Server and receives the message back
	 *
	 * @param instruction The instruction the server will execute
	 * @param course      The course the server needs for the instruction
	 * @return The message the server sends back
	 */
	public String communicateRemoveCourse(String instruction, Course course) {
		String message = null;
		writeString(instruction);
		sendCourse(course);
		message = readString();
		return message;
	}

	/**
	 * Sends an instruction to the Server and receives the message back
	 *
	 * @param instruction The instruction the server will execute
	 * @param course      The course the server needs for the instruction
	 * @return The message the server sends back
	 */
	public String communicateEnrollCourse(String instruction, Course course, String lectureNumber,
			ArrayList<Registration> registrationList) {
		String message = "";
		writeString(instruction);
		sendCourse(course);
		writeString(lectureNumber);
		message = readString();
		registrationList = receiveRegistrationList();
		return message;
	}

	/**
	 * Sends an instruction to the Server and receives the message back
	 *
	 * @param instruction The instruction the server will execute
	 * @param course      The course the server needs for the instruction
	 * @return The message the server sends back
	 */
	public String communicateDropCourse(String instruction, Course course, ArrayList<Registration> registrationList) {
		String temp = "";
			writeString(instruction);
			sendCourse(course);
			temp = readString();
			registrationList = receiveRegistrationList();

		return temp;
	}

	/**
	 * Sends an instruction to the Server and receives the message back
	 *
	 * @param instruction The instruction the server will execute
	 * @param course      The course the server needs for the instruction
	 * @return The message the server sends back
	 */
	public String communicateAddCourse(String instruction, Course course, ArrayList<CourseOffering> courseOfferings) {
		String message = null;
		writeString(instruction);
		sendCourse(course);
		sendCourseOfferingList(courseOfferings);
		return readString();
	}

	/**
	 * Sends an instruction to the Server and receives back an Course Array
	 * 
	 * @param instruction The instruction the server will execute
	 * @return The Course Array requested
	 */
	public ArrayList<Course> communicateGetCourseList(String instruction) {
		writeString(instruction);
		return receiveCourseList();
	}

	/**
	 * Sends an instruction to the Server and receives back an Course Array
	 *
	 * @param instruction The instruction the server will execute
	 * @return The Course Array requested
	 */
	public String communicateGetStudentsRegistrationList(String instruction, String studentID) {
			writeString(instruction);
			writeString(studentID);
			return readString();
	}

	public String communicateAddStudent(String instruction, String studentID) {
		writeString(instruction);
		writeString(studentID);
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
