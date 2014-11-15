package edu.tolc.discussionforum.service;

import java.util.List;

import edu.tolc.discussionforum.dto.GetCoursesDTO;
import edu.tolc.discussionforum.model.UserInformation;

public interface UsersService {
	// Registration
	public String userRegistration(UserInformation userInfo);
	// Register instructors
	public String instructorRegistration(UserInformation userInfo);
	// For instructors - adding a new course
	public String addCourse(String courseName, String courseDescription,
			String instructorsName);
	// For instructors
	public List<GetCoursesDTO> getCourseList(String instructorsName);
	// This method could be reused for admin
	public List<String> getInstructorsList();
	// Get all the courses for student
	public List<GetCoursesDTO> getCourseList();
	// Enroll student in course
	public String enrollStudentInCourse(String courseID, String studentName);
	// Get student courses
	public List<GetCoursesDTO> getStudentCourses(String studentName);
	
}
