package com.challenge.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name="calls")
public class Call {

    /*
    enum call_states {
        ONGOING,
        ENDED
    }*/

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    /* call data */
    private String num_caller;
    private String num_callee;

    private String type;
    private Timestamp time_start; // when caller starts the call or when callee picks up the call ?
    private Timestamp time_end;

    private String call_status;

    public Call(@JsonProperty("num_caller") String num_caller, @JsonProperty("num_callee") String num_callee, @JsonProperty("type") String type) {
        this.num_caller = num_caller;
        this.num_callee = num_callee;
        this.type = type;
    }

    public Call(Integer id, String num_caller, String num_callee, String type, Timestamp time_start, Timestamp time_end, String call_status){
        this.id = id;
        this.num_caller = num_caller;
        this.num_callee = num_callee;
        this.type = type;
        this.time_start = time_start;
        this.time_end = time_end;
        this.call_status = call_status;
    }

    public void endCall(){
        call_status = "ENDED"; // internal state update
        this.time_end = new Timestamp(System.currentTimeMillis());
    }

    public void startCall() { /* probably not needed anymore since run() exists */
        call_status = "ONGOING"; // internal state update
        this.time_start = new Timestamp(System.currentTimeMillis());
    }

    public boolean updateCall(Call call){
        System.out.println("Call was updated");
        return true;
    }

    public Integer getId(){
        return id;
    }

    public String getCallerNumber() {
        return num_caller;
    }

    public String getCalleeNumber() {
        return num_callee;
    }

    public Timestamp getCallStartTime() {
        return time_start;
    }

    public Timestamp getCallEndTime() {
        return time_end;
    }

    public String getCallType(){
        return type;
    }

    public String getCallStatus(){
        return call_status;
    }



}
