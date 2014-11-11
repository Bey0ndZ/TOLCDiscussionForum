package edu.tolc.discussionforum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import edu.tolc.discussionforum.dao.UsersDAO;
import edu.tolc.discussionforum.model.UserInformation;
import edu.tolc.discussionforum.service.UsersService;

public class UsersServiceImpl implements UsersService{
	@Autowired
	UsersDAO userDAO;
	
	@Override
	public String userRegistration(UserInformation userInfo) {
		return userDAO.userRegistration(userInfo);
	}

	@Override
	public String addCourse(String courseName, String courseDescription,
			String instructorsName) {
		return userDAO.addCourse(courseName, courseDescription, instructorsName);
	}
	
}	
