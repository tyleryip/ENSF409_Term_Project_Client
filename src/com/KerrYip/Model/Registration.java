package com.KerrYip.Model;

import java.io.Serializable;

/**
 * This class represents a registration for a course between a course offering
 * and a student; it also tracks a student's grade in the course
 * 
 * @author Tyler Yip
 * @author Will Kerr
 * @version 2.0
 * @since 04/07/20
 */
public class Registration implements Serializable {

	// This long is used for serialization
	private static final long serialVersionUID = 1L;

	private Student theStudent;
	private CourseOffering theOffering;
	private char grade;

	// Used to identify the instance of this class in the SQL database
	private int id;

	/**
	 * Constructor for Registration
	 * 
	 * @param id the id
	 */
	public Registration(int id) {
		this.theStudent = null;
		this.theOffering = null;
		this.id = id;
	}

	/**
	 * Constructor for Registration
	 * 
	 * @param id    the registration ID for the database
	 * @param grade the grade
	 */
	public Registration(int id, char grade) {
		this.theStudent = null;
		this.theOffering = null;
		this.id = id;
		this.grade = grade;
	}

	/**
	 * Constructor for Registration
	 *
	 * @param id          the registration ID for the database
	 * @param theStudent  the student
	 * @param theOffering the course offering
	 * @param grade       the grade
	 */
	public Registration(int id, Student theStudent, CourseOffering theOffering, char grade) {
		this.id = id;
		this.theStudent = theStudent;
		this.theOffering = theOffering;
		this.grade = grade;
	}

	/**
	 * Links a student and course offering together via a registration
	 * 
	 * @param st the student
	 * @param of the course offering
	 */
	public void completeRegistration(Student st, CourseOffering of) {
		theStudent = st;
		theOffering = of;
		addRegistration();
	}

	/**
	 * Adds a registration to the student's registration list and the course
	 * offering's registration list
	 */
	private void addRegistration() {
		if (theStudent.getStudentRegList().size() > 6) {
			System.out.println(
					"Student has already enrolled in the maximum number of 6 courses, cannot register this student into an additional course.");
			return;
		}
		theStudent.addRegistration(this);
		theOffering.addRegistration(this);
	}

	@Override
	public String toString() {
		String st = "";
		st += "Student Name: " + getTheStudent() + "\n";
		st += "The Offering: " + getTheOffering() + "\n";
		st += "Grade: " + getGrade();
		st += "\n-----------\n";
		return st;
	}

	/**
	 * Method for the GUI to display the Registration without students name being
	 * displayed again as it is already displayed as the title of the student menu
	 * 
	 * @return Returns the String of the Registration
	 */
	public String toCourseString() {
		String st = "";
		st += getTheOffering() + "\n";
		st += "Grade: " + getGrade();
		st += "\n-----------\n";
		return st;
	}

	// GETTERS and SETTERS
	public Student getTheStudent() {
		return theStudent;
	}

	public void setTheStudent(Student theStudent) {
		this.theStudent = theStudent;
	}

	public CourseOffering getTheOffering() {
		return theOffering;
	}

	public void setTheOffering(CourseOffering theOffering) {
		this.theOffering = theOffering;
	}

	public char getGrade() {
		return grade;
	}

	public void setGrade(char grade) {
		this.grade = grade;
	}

	public int getID() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
	}

}
