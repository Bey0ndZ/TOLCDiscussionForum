package edu.tolc.discussionforum.mappersandextractors;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import edu.tolc.discussionforum.dto.GetTickrDTO;

public class GetTickrMapper implements RowMapper<GetTickrDTO> {
		public GetTickrDTO mapRow(ResultSet resultSet, int line) throws SQLException {
			GetTickrExtractor extractor = new GetTickrExtractor();
		  return extractor.extractData(resultSet);
	}
}
