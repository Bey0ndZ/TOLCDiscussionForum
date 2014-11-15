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

	public String enrollStudentInAllCourses(String studentName, String instructorsName);
}
