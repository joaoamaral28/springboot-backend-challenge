package com.challenge.demo.api;

import com.challenge.demo.service.CallService;
import com.challenge.demo.model.Call;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path="api/call")
public class CallController {

    private CallService call_service;

    @Autowired
    public CallController(CallService call_service) {
        this.call_service = call_service;
    }

    @PostMapping(path="/createCall")
    @ResponseBody
    public String createCall(@Valid @NonNull @RequestBody Call call){
        call.startCall();
        return call_service.createCall(call);
    }

    @DeleteMapping(path="/deleteCall")
    @ResponseBody
    public String deleteCall(@RequestParam Integer call_id){
        call_service.deleteCall(call_id);
        return "Call deleted!";
    }

    @PutMapping(path="/endCall")
    @ResponseBody
    public String endCall(@RequestParam Integer call_id){ /* probably not good idea for Clients to know call ids but will do for now*/
        call_service.updateCall(call_id);
        return "Call ended!";
    }

    @GetMapping(path="/getAllCalls")
    @ResponseBody
    public List<Call> getAllCalls(@RequestParam(required = false) String call_type, @RequestParam(required = false)  Integer limit, @RequestParam(required = false)  Integer offset){
        if(call_type==null)
            if(limit==null && offset==null)
                return call_service.getAllCalls(); // fetch all calls from db
            else
                return call_service.getAllCallsPaginate(limit,offset); // fetch all calls from db with pagination
        else {
            if (limit == null && offset == null)
                return call_service.getAllCalls(call_type); // fetch all calls with type from db
            else
                return call_service.getAllCallsPaginate(call_type,limit,offset); // fetch all calls with type from db with pagination
        }
    }

    @GetMapping(path="/stats/totalCallDuration")
    @ResponseBody
    public Map<Integer, String> getStatsCallDuration(@RequestParam String call_type){
        return call_service.getStatsCallDuration(call_type);
    }

    @GetMapping(path="/stats/totalNumberOfCalls")
    @ResponseBody
    public Map<Integer, Integer> getStatsTotalNumberOfCalls(){
        return call_service.getStatsTotalNumberOfCalls();
    }

    @GetMapping(path="/stats/numberOfCallsCaller")
    @ResponseBody
    public Map<Integer, Integer> getStatsNumberOfCallsCaller(@RequestParam String caller_number) {
        return call_service.getStatsNumberOfCallsCaller(caller_number);
    }

    @GetMapping(path="/stats/numberOfCallsCallee")
    @ResponseBody
    public Map<Integer, Integer> getStatsNumberOfCallsCallee(@RequestParam String callee_number) {
        return call_service.getStatsNumberOfCallsCallee(callee_number);
    }

    @GetMapping(path="/stats/getTotalCallCost")
    @ResponseBody
    public Map<Integer, Double>  getTotalCallCost() {
        return call_service.getStatsTotalCallCost();
    }
}
