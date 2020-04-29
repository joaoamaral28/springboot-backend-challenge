package com.challenge.demo.service;

import com.challenge.demo.dao.CallDao;
import com.challenge.demo.model.Call;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.TimeUnit;

/* Service class implements all the business logic of the application */
@Service
public class CallService {

    private CallDao call_dao;

    @Autowired
    public CallService(@Qualifier("mySQL") CallDao call_dao){
        this.call_dao = call_dao;
    }

    public String createCall(Call call){
        int ret = call_dao.insertCall(call);
        if(ret==1){
            return "Call created successfully!";
        }else{
            return "Error";
        }
    }

    public String updateCall(Call call) {
        int ret = call_dao.updateCall(call);
        if(ret==1){
            return "Call created successfully!";
        }else{
            return "Error";
        }
    }

    public String updateCall(Integer id) {
        Call call = call_dao.retrieveCall(id);
        int ret = call_dao.endCall(call);
        if(ret==1){
            return "Call created successfully!";
        }else{
            return "Error";
        }
    }

    public int deleteCall(Integer call_id) {
        return call_dao.deleteCall(call_id);
    }

    public List<Call> getAllCalls(){
        return call_dao.getAllCalls();
    }

    public List<Call> getAllCalls(String type) {
        return call_dao.getAllCalls(type);
    }

    public List<Call> getAllCallsPaginate(Integer limit, Integer offset){
        return call_dao.getAllCallsPaginate(limit, offset);
    }

    public List<Call> getAllCallsPaginate(String call_type, Integer limit, Integer offset){
        return call_dao.getAllCallsPaginate(call_type,limit, offset);
    }

    public Map<Integer, String> getStatsCallDuration(String type) {
        List<Call> calls = getAllCalls(type);
        Map<Integer, Long> duration_map = new HashMap<Integer, Long>();

        // calculate total duration by day
        for(Call c : calls){
            Timestamp start = c.getCallStartTime();
            Timestamp end = c.getCallEndTime();
            long call_duration = end.getTime() - start.getTime();
            // get day from timestamp start date
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(start.getTime());
            Integer day = calendar.get(Calendar.DAY_OF_MONTH);

            if(duration_map.containsKey(day))
                duration_map.put(day, duration_map.get(day) + call_duration);
            else
                duration_map.put(day, call_duration);
        }

        Map<Integer, String> result = new HashMap<Integer, String>();
        // format duration into date format for better Client readability
        for(Map.Entry<Integer,Long> entry : duration_map.entrySet()){
            result.put(entry.getKey(),milisToHMS(entry.getValue()));
        }

        return result;
    }

    public Map<Integer, Integer> getStatsTotalNumberOfCalls() {
        List<Call> calls = getAllCalls();
        return callsToCountMap(calls);
    }

    public Map<Integer, Integer> getStatsNumberOfCallsCaller(String caller_number) {
        List<Call> calls = call_dao.getCallsCaller(caller_number);
        return callsToCountMap(calls);
    }

    public Map<Integer, Integer> getStatsNumberOfCallsCallee(String callee_number) {
        List<Call> calls = call_dao.getCallsCallee(callee_number);
        return callsToCountMap(calls);
    }

    public Map<Integer, Double> getStatsTotalCallCost() {
        List<Call> calls = getAllCalls();
        Map<Integer, Double> cost_map = new HashMap<Integer, Double>();

        // calculate total duration by day
        for(Call c : calls){
            Timestamp start = c.getCallStartTime();
            Timestamp end = c.getCallEndTime();
            long call_duration = end.getTime() - start.getTime();
            // get day from timestamp start date
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(start.getTime());
            Integer day = calendar.get(Calendar.DAY_OF_MONTH);

            double cost = 0;
            if(c.getCallType().equals("OUTBOUND")){
                long mins = TimeUnit.MILLISECONDS.toMinutes(call_duration) % TimeUnit.HOURS.toMinutes(1);
                if(mins > 5){
                    cost = 0.10 +  (mins-5) * 0.05;
                }else{
                    cost = 0.10;
                }
            }
            if(cost_map.containsKey(day))
                cost_map.put(day, cost_map.get(day) + cost);
            else
                cost_map.put(day, cost);
        }
        return cost_map;
    }

    /* aux functions */
    public Map<Integer, Integer> callsToCountMap(List<Call> calls){
        Map<Integer, Integer> count_map = new HashMap<Integer, Integer>();
        for(Call c : calls){
            // get day from timestamp start date
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(c.getCallStartTime().getTime());
            Integer day = calendar.get(Calendar.DAY_OF_MONTH);
            if(count_map.containsKey(day))
                count_map.put(day, count_map.get(day) + 1);
            else
                count_map.put(day, 1);
        }
        return count_map;
    }

    public String milisToHMS(long milis){
        return String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(milis),
                TimeUnit.MILLISECONDS.toMinutes(milis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(milis) % TimeUnit.MINUTES.toSeconds(1));
    }

}
