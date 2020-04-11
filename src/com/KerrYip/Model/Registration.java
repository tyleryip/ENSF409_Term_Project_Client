package com.KerrYip.Model;

import java.io.Serializable;

/**
 * This class represents a registration for a course between a course offering
 * and a student; it also tracks a student's grade in the course
 * 
 * @author tyleryip
 * @version 2.0
 * @since 04/07/20
 */
public class Registration implements Serializable {

	/**
	 * This long is used for serialization
	 */
	private static final long serialVersionUID = 1L;

	private Student theStudent;
	private CourseOffering theOffering;
	private char grade;

	@Override
	public String toString() {
		String st = "";
		st += "Student Name: " + getTheStudent() + "\n";
		st += "The Offering: " + getTheOffering() + "\n";
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

}
