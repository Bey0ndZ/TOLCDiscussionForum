package edu.tolc.discussionforum.service;

import java.util.List;

import edu.tolc.discussionforum.dto.GetCoursesDTO;
import edu.tolc.discussionforum.model.UserInformation;

public interface UsersService {
	// Registration
	public String userRegistration(UserInformation userInfo);

	public String addCourse(String courseName, String courseDescription,
			String instructorsName);

	public List<GetCoursesDTO> getCourseList(String instructorsName);
}
