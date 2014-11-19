package edu.tolc.discussionforum.dto;

public class CourseEnrollmentDTO {
	private String coursename;
	private String studentregistered;
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
	 * @return the studentregistered
	 */
	public String getStudentregistered() {
		return studentregistered;
	}
	/**
	 * @param studentregistered the studentregistered to set
	 */
	public void setStudentregistered(String studentregistered) {
		this.studentregistered = studentregistered;
	}
}
