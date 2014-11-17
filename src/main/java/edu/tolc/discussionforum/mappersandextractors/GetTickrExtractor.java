package edu.tolc.discussionforum.mappersandextractors;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import edu.tolc.discussionforum.dto.GetTickrDTO;

public class GetTickrExtractor implements ResultSetExtractor<GetTickrDTO> { 
	public GetTickrDTO extractData(ResultSet resultSet)
		throws SQLException, DataAccessException {

		GetTickrDTO getTickrDTO = new GetTickrDTO();

		getTickrDTO.setPostedby(resultSet.getString(1));
		getTickrDTO.setPostedat(resultSet.getTimestamp(2));
		getTickrDTO.setPostanonymously(resultSet.getBoolean(3));

	return getTickrDTO;
}
}
