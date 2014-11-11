package edu.tolc.discussionforum.dao.impl;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import edu.tolc.discussionforum.dao.UsersDAO;
import edu.tolc.discussionforum.model.UserInformation;

// Perform CRUD Operations
public class UsersDAOImpl implements UsersDAO {
	@Autowired
	DataSource dataSource;
	
	@Override
	public String userRegistration(UserInformation userInfo) {
		// Setting all the accounts to enabled by default
		userInfo.setEnabled(1);
		
		String userRegistrationQuery = "INSERT INTO users VALUES (?,?,?,?,?,?,?)";
		JdbcTemplate userRegistrationTemplate = new JdbcTemplate(dataSource);
		
		userRegistrationTemplate.update(userRegistrationQuery,
				new Object[] {userInfo.getUsername(), userInfo.getPassword(),
				userInfo.getFirstname(), userInfo.getLastname(),
				userInfo.getIsstudent(), userInfo.getPhonenumber(),
				userInfo.getEmail(), userInfo.getEnabled()});
		return "User registration successful";
	}

}
