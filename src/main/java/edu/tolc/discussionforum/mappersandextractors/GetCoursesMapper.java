package edu.tolc.discussionforum.mappersandextractors;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import edu.tolc.discussionforum.dto.GetCoursesDTO;

public class GetCoursesMapper implements RowMapper<GetCoursesDTO>{
		public GetCoursesDTO mapRow(ResultSet resultSet, int line) throws SQLException {
			GetCoursesExtractor extractor = new GetCoursesExtractor();
		  return extractor.extractData(resultSet);
	}
}
