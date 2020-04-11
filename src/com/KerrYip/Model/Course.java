package com.KerrYip.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represents a course a student can enroll in or an administrator
 * can modify
 * 
 * @author tyleryip
 * @version 2.0
 * @since 04/07/20
 *
 */
public class Course implements Serializable {

	/**
	 * This long is used for serialization
	 */
	private static final long serialVersionUID = 1L;

	private String courseName;
	private int courseNum;
	private ArrayList<Course> preReq;
	private ArrayList<CourseOffering> offeringList;

	/**
	 * Constructor for the class Course
	 * 
	 * @param courseName the name of the course
	 * @param courseNum  the number of the course
	 */
	public Course(String courseName, int courseNum) {
		this.setCourseName(courseName);
		this.setCourseNum(courseNum);
		// Both of the following are only association
		preReq = new ArrayList<Course>();
		offeringList = new ArrayList<CourseOffering>();
	}

	public Course(String courseName, int courseNum, ArrayList<CourseOffering> courseOfferings) {
		this.setCourseName(courseName);
		this.setCourseNum(courseNum);
		// Both of the following are only association
		preReq = new ArrayList<Course>();
		offeringList = courseOfferings;
	}

	@Override
	public String toString() {
		String st = "";
		st += getCourseName() + " " + getCourseNum();
		st += "\n---------------------------------------";
		st += "\nAll course sections:\n";
		for (CourseOffering c : offeringList)
			st += c;
		st += "\nAll course prerequisites:\n";
		for (Course c : preReq)
			st += c.getCourseName() + " " + c.getCourseNum();
		st += "\n---------------------------------------\n";
		return st;
	}

	// GETTERS and SETTERS
	public ArrayList<CourseOffering> getOfferingList() {
		return offeringList;
	}

	public void setOfferingList(ArrayList<CourseOffering> offeringList) {
		this.offeringList = offeringList;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public int getCourseNum() {
		return courseNum;
	}

	public void setCourseNum(int courseNum) {
		this.courseNum = courseNum;
	}

	public ArrayList<Course> getPreReq() {
		return preReq;
	}

	public void setPreReq(ArrayList<Course> preReq) {
		this.preReq = preReq;
	}

	public String getNameNum() {
		return this.courseName + " " + this.getCourseNum();
	}

}
