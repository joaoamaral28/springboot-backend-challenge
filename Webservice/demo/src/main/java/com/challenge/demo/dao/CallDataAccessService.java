package com.challenge.demo.dao;

import com.challenge.demo.model.Call;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("mySQL")
public class CallDataAccessService implements CallDao {

    private final JdbcTemplate jdbcTemplate;

    public CallDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insertCall(Call call) {
        return jdbcTemplate.update("INSERT INTO calls (num_caller, num_callee,type,time_start,call_status) VALUES (?,?,?,?,?)",
                call.getCallerNumber(), call.getCalleeNumber(), call.getCallType(), call.getCallStartTime(), call.getCallStatus());
    }

    @Override
    public int updateCall(Call call) {
        return jdbcTemplate.update("UPDATE calls SET num_caller=?, num_callee=?,type=?,time_start=?, time_end=?, call_status=? WHERE id=?",
                call.getCallerNumber(), call.getCalleeNumber(), call.getCallType(), call.getCallStartTime(), call.getCallEndTime(), call.getCallStatus(), call.getId());
    }

    @Override
    public Call retrieveCall(Integer call_id){
        Call call = (Call) jdbcTemplate.queryForObject("SELECT * FROM calls WHERE id=?", new Object[]{call_id}, new CallRowMapper());
        return call;
    }

    @Override
    public int deleteCall(Integer call_id) {
        return jdbcTemplate.update("DELETE FROM calls WHERE id=?", call_id);
    }

    @Override
    public int endCall(Call call){
        call.endCall();
        return jdbcTemplate.update("UPDATE calls SET time_end=?, call_status=? WHERE id=?", call.getCallEndTime(), call.getCallStatus(), call.getId());
    }

    @Override
    public List<Call> getAllCalls(String type) {
        return jdbcTemplate.query("SELECT * FROM calls WHERE type=?",new Object[]{type}, new CallRowMapper());
    }

    @Override
    public List<Call> getAllCalls() {
        return jdbcTemplate.query("SELECT * FROM calls",new Object[]{}, new CallRowMapper());
    }

    @Override
    public List<Call> getAllCallsPaginate(Integer limit, Integer offset) {
        return jdbcTemplate.query("SELECT * FROM calls LIMIT ? OFFSET ?",new Object[]{limit,offset}, new CallRowMapper());
    }

    @Override
    public List<Call> getAllCallsPaginate(String type, Integer limit, Integer offset) {
        return jdbcTemplate.query("SELECT * FROM calls WHERE type=? LIMIT ? OFFSET ?",new Object[]{type,limit,offset}, new CallRowMapper());
    }

    @Override
    public int getStatsTotalNumberOfCalls() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM calls", new Object[] {}, Integer.class);
    }

    @Override
    public List<Call> getCallsCaller(String caller_number){
        return jdbcTemplate.query("SELECT * FROM calls WHERE num_caller=?",new Object[]{caller_number}, new CallRowMapper());
    }

    @Override
    public List<Call> getCallsCallee(String callee_number){
        return jdbcTemplate.query("SELECT * FROM calls WHERE num_callee=?",new Object[]{callee_number}, new CallRowMapper());
    }

}
