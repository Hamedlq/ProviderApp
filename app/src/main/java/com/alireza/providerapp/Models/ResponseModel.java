package com.alireza.providerapp.Models;

/**
 * Created by alireza on 3/27/18.
 */

public class ResponseModel<T> {

    T Message;

    public T getMessage() {
        return Message;
    }

    public void setMessage(T message) {
        Message = message;
    }
}
