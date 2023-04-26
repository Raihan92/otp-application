package com.progcreek.otpapplication.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Reply<T> implements Serializable {

    private boolean success;

    private Integer code;

    private String message;

    private String error;

    private T payload;

    public Reply() {
    }

    public Reply(boolean success, Integer code, String message, T payload) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.payload = payload;
    }

    public Reply(boolean success, Integer code, String message, String error, T payload) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.error = error;
        this.payload = payload;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public String getTimestamp() {
        return LocalDateTime.now().toString();
    }

}
