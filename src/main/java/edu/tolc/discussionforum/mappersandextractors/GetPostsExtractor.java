package edu.tolc.discussionforum.mappersandextractors;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import edu.tolc.discussionforum.dto.GetPostsDTO;

public class GetPostsExtractor implements ResultSetExtractor<GetPostsDTO> {
	public GetPostsDTO extractData(ResultSet resultSet)
		throws SQLException, DataAccessException {

		GetPostsDTO getPostsDTO = new GetPostsDTO();

		getPostsDTO.setPostcontent(resultSet.getString(1));
		getPostsDTO.setPostedby(resultSet.getString(2));
		getPostsDTO.setPostanonymously(resultSet.getBoolean(3));
		getPostsDTO.setPostedat(resultSet.getTimestamp(4));

	return getPostsDTO;
}
}
