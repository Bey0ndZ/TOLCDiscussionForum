package edu.tolc.discussionforum.dao;

import java.util.List;

import edu.tolc.discussionforum.dto.GetCoursesDTO;
import edu.tolc.discussionforum.model.UserInformation;

public interface UsersDAO {
	// Registration
	public String userRegistration(UserInformation userInfo);

	public String addCourse(String courseName, String courseDescription,
			String instructorsName);

	public List<GetCoursesDTO> getCourseList(String instructorsName);

	public String instructorRegistration(UserInformation userInfo);

	public List<String> getInstructorsList();
	
	// For the students when they click on View All Courses tab
	public List<GetCoursesDTO> getCourseList();
	
	// Enroll student in course
	public String enrollStudentInCourse(String courseID, String studentName);

	// Get student courses
	public List<GetCoursesDTO> getStudentCourses(String studentName);
}
