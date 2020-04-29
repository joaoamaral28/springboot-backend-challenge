package com.challenge.demo.dao;

import com.challenge.demo.model.Call;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/* class used to ease the mapping of table fields to Call class type*/
public class CallRowMapper implements RowMapper<Call> {
    @Override
    public Call mapRow(ResultSet rs, int row_number) throws SQLException
    {
        return new Call(
            rs.getInt("id"),
            rs.getString("num_caller"),
            rs.getString("num_callee"),
            rs.getString("type"),
            rs.getTimestamp("time_start"),
            rs.getTimestamp("time_end"),
            rs.getString("call_status"));
    }
}
