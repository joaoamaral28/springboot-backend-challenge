package com.challenge.demo.dao;

import com.challenge.demo.model.Call;

import java.util.List;

/* Data access object class to handle persistence of system calls */
public interface CallDao {

    // insert call object representation into local database
    int insertCall(Call call);

    // update an existing call on the database (used mostly for filling missing fields upon call closure)
    int updateCall(Call call);

    // fetches a single call from database given the input id
    Call retrieveCall(Integer id);

    // delete call from database
    int deleteCall(Integer call_id);

    int endCall(Call call);

    // get all database calls from given type (filter parameter)
    List<Call> getAllCalls(String type);

    // get all database calls no filter
    List<Call> getAllCalls();

    // get all database calls with pagination using the specified limit and offset
    List<Call> getAllCallsPaginate(Integer limit, Integer offset);

    // get all database calls with filter and with pagination using the specified limit and offset
    List<Call> getAllCallsPaginate(String type, Integer limit, Integer offset);

    // Statistics operations

    List<Call> getCallsCaller(String caller_number);

    List<Call> getCallsCallee(String callee_number);

    int getStatsTotalNumberOfCalls();

}
