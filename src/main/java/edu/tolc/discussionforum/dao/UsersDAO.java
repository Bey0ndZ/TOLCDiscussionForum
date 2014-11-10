package edu.tolc.discussionforum.dao;

import edu.tolc.discussionforum.model.UserInformation;

public interface UsersDAO {
	// Registration
	public String userRegistration(UserInformation userInfo);
}
