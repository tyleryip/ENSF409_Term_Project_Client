package com.KerrYip.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represents a section or offering of a specific course
 * 
 * @author Tyler Yip
 * @author Will Kerr
 * @version 2.0
 * @since 04/07/20
 */
public class CourseOffering implements Serializable {

	// This long is used for serialization
	private static final long serialVersionUID = 1L;

	// Properties of the course offering
	private int secNum;
	private int secCap;

	// The course that this offering belongs to
	private Course theCourse;

	// A list of all students who are registered in this offering
	private ArrayList<Registration> offeringRegList;

	// Used to identify the instance of this class in the SQL database
	private int id;

	/**
	 * The constructor for class CourseOffering
	 * 
	 * @param secNum the section number
	 * @param secCap the capacity of the course offering
	 */
	public CourseOffering(int secNum, int secCap) {
		this.setSecNum(secNum);
		this.setSecCap(secCap);
		this.theCourse = new Course("NULL", 000);
		offeringRegList = new ArrayList<Registration>();
	}

	/**
	 * The constructor for class CourseOffering
	 * 
	 * @param courseOfferingID ID number used to store in the SQL database
	 * @param secNum           the section number
	 * @param secCap           the capacity of the course offering
	 */
	public CourseOffering(int secNum, int secCap, int courseOfferingID) {
		this.id = courseOfferingID;
		this.setSecNum(secNum);
		this.setSecCap(secCap);
		offeringRegList = new ArrayList<Registration>();
	}

	/**
	 * The constructor for class CourseOffering
	 * 
	 * @param courseOfferingID ID number used to store in the SQL database
	 * @param course           The course
	 * @param secNum           the section number
	 * @param secCap           the capacity of the course offering
	 */
	public CourseOffering(int courseOfferingID, Course course, int secNum, int secCap) {
		this.id = courseOfferingID;
		this.theCourse = course;
		this.setSecNum(secNum);
		this.setSecCap(secCap);
		offeringRegList = new ArrayList<Registration>();
	}

	/**
	 * Adds a registration to the course offering's registration list
	 * 
	 * @param registration the registration to add
	 */
	public void addRegistration(Registration registration) {
		// TODO Auto-generated method stub
		offeringRegList.add(registration);

	}

	@Override
	public String toString() {
		String st = "\nCourse: ";
		st += getTheCourse().getCourseName() + " " + getTheCourse().getCourseNum() + "\n";
		st += "Section Num: " + getSecNum() + ", section cap: " + getSecCap() + "\n";
		// We also want to print the names of all students in the section
		return st;
	}

	/**
	 * Method for the GUI to display the offering without the Course and all
	 * students being listed for the enrolled courses list in browse catalog and
	 * admin menu
	 * 
	 * @return Returns the String of the offering
	 */
	public String toOfferingString() {
		String st = "Section Num: " + getSecNum() + ", section cap: " + getSecCap() + "\n";
		return st;
	}

	// GETTERS and SETTERS
	public int getSecNum() {
		return secNum;
	}

	public void setSecNum(int secNum) {
		this.secNum = secNum;
	}

	public int getSecCap() {
		return secCap;
	}

	public void setSecCap(int secCap) {
		this.secCap = secCap;
	}

	public Course getTheCourse() {
		return theCourse;
	}

	public void setTheCourse(Course theCourse) {
		this.theCourse = theCourse;
	}

	public ArrayList<Registration> getOfferingRegList() {
		return offeringRegList;
	}

	public void setOfferingRegList(ArrayList<Registration> offeringRegList) {
		this.offeringRegList = offeringRegList;
	}

	public int getID() {
		return id;
	}

	public void setID(int courseOfferingID) {
		this.id = courseOfferingID;
	}

}
