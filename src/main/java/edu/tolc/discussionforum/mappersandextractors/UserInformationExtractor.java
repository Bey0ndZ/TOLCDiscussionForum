package edu.tolc.discussionforum.mappersandextractors;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import edu.tolc.discussionforum.dto.UserInformationDTO;

public class UserInformationExtractor implements ResultSetExtractor<UserInformationDTO> {
		public UserInformationDTO extractData(ResultSet resultSet)
			throws SQLException, DataAccessException {

			UserInformationDTO getUserInfoDTO = new UserInformationDTO();

			getUserInfoDTO.setFirstname(resultSet.getString(1));
			getUserInfoDTO.setLastname(resultSet.getString(2));
			getUserInfoDTO.setUsername(resultSet.getString(3));
			getUserInfoDTO.setEmail(resultSet.getString(4));

		return getUserInfoDTO;
	}
}
