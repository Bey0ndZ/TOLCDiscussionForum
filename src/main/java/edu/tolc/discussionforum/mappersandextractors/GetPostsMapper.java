package edu.tolc.discussionforum.mappersandextractors;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import edu.tolc.discussionforum.dto.GetPostsDTO;

public class GetPostsMapper implements RowMapper<GetPostsDTO> {
		public GetPostsDTO mapRow(ResultSet resultSet, int line) throws SQLException {
			GetPostsExtractor extractor = new GetPostsExtractor();
		  return extractor.extractData(resultSet);
	}
}
