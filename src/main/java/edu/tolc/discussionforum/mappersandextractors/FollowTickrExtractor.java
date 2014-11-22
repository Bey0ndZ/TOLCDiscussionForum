package edu.tolc.discussionforum.mappersandextractors;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import edu.tolc.discussionforum.dto.FollowTickrDTO;

public class FollowTickrExtractor implements ResultSetExtractor<FollowTickrDTO> {
		public FollowTickrDTO extractData(ResultSet resultSet)
			throws SQLException, DataAccessException {

			FollowTickrDTO followTickrDTO = new FollowTickrDTO();

			followTickrDTO.setThreadname(resultSet.getString(1));
			followTickrDTO.setPostedby(resultSet.getString(2));
			followTickrDTO.setPostedat(resultSet.getTimestamp(3));
			followTickrDTO.setPostanonymously(resultSet.getBoolean(4));

		return followTickrDTO;
	}
}
