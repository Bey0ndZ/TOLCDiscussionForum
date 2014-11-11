package edu.tolc.discussionforum.dto;

public class GetCoursesDTO {
	private int courseid;
	private String coursename;
	private String coursedescription;
	private String instructor;
	private int numberofstudents;
	/**
	 * @return the courseid
	 */
	public int getCourseid() {
		return courseid;
	}
	/**
	 * @param courseid the courseid to set
	 */
	public void setCourseid(int courseid) {
		this.courseid = courseid;
	}
	/**
	 * @return the coursename
	 */
	public String getCoursename() {
		return coursename;
	}
	/**
	 * @param coursename the coursename to set
	 */
	public void setCoursename(String coursename) {
		this.coursename = coursename;
	}
	/**
	 * @return the coursedescription
	 */
	public String getCoursedescription() {
		return coursedescription;
	}
	/**
	 * @param coursedescription the coursedescription to set
	 */
	public void setCoursedescription(String coursedescription) {
		this.coursedescription = coursedescription;
	}
	/**
	 * @return the instructor
	 */
	public String getInstructor() {
		return instructor;
	}
	/**
	 * @param instructor the instructor to set
	 */
	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}
	/**
	 * @return the numberofstudents
	 */
	public int getNumberofstudents() {
		return numberofstudents;
	}
	/**
	 * @param numberofstudents the numberofstudents to set
	 */
	public void setNumberofstudents(int numberofstudents) {
		this.numberofstudents = numberofstudents;
	}
}
