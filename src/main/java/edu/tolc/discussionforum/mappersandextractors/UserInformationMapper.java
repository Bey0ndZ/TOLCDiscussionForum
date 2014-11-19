package edu.tolc.discussionforum.mappersandextractors;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import edu.tolc.discussionforum.dto.UserInformationDTO;

public class UserInformationMapper implements RowMapper<UserInformationDTO> {
		public UserInformationDTO mapRow(ResultSet resultSet, int line) throws SQLException {
			UserInformationExtractor extractor = new UserInformationExtractor();
		  return extractor.extractData(resultSet);
	}
}
