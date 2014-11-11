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
		String userRolesQuery = "INSERT INTO user_roles(username, role) VALUES (?,?)";
		
		JdbcTemplate userRegistrationTemplate = new JdbcTemplate(dataSource);
		JdbcTemplate userRolesTemplate = new JdbcTemplate(dataSource);
		
		// Insert into DB
		// Query 1
		userRegistrationTemplate.update(userRegistrationQuery,
				new Object[] {userInfo.getUsername(), userInfo.getPassword(),
				userInfo.getFirstname(), userInfo.getLastname(),
				userInfo.getPhonenumber(), userInfo.getEmail(), 
				userInfo.getEnabled()});
		
		// Query 2
		userRolesTemplate.update(userRolesQuery, new Object[] {userInfo.getUsername(),
				"ROLE_STUDENT"});
		
		return "User registration successful";
	}

}
