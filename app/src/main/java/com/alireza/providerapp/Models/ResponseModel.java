package com.alireza.providerapp.Models;

/**
 * Created by alireza on 3/27/18.
 */

public class ResponseModel<T> {

    T Message;
    private String Error;

    public T getMessage() {
        return Message;
    }

    public void setMessage(T message) {
        Message = message;
    }

    public String getError() {
        return Error;
    }

    public void setError(String error) {
        Error = error;
    }
}
