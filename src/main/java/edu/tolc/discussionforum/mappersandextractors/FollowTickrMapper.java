package edu.tolc.discussionforum.mappersandextractors;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import edu.tolc.discussionforum.dto.FollowTickrDTO;

public class FollowTickrMapper implements RowMapper<FollowTickrDTO> {
	public FollowTickrDTO mapRow(ResultSet resultSet, int line) throws SQLException {
		FollowTickrExtractor extractor = new FollowTickrExtractor();
	  return extractor.extractData(resultSet);
	}
}
