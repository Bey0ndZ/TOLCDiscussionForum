package edu.tolc.discussionforum.mappersandextractors;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import edu.tolc.discussionforum.dto.GetThreadInfoDTO;

public class GetThreadInfoMapper implements RowMapper<GetThreadInfoDTO> {
		public GetThreadInfoDTO mapRow(ResultSet resultSet, int line) throws SQLException {
			GetThreadInfoExtractor extractor = new GetThreadInfoExtractor();
		  return extractor.extractData(resultSet);
	}
}
