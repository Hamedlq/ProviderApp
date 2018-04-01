package com.alireza.providerapp.Models;

/**
 * Created by alireza on 3/27/18.
 */

public class LoginResponseModel {

    String Message;
    private String Error;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getError() {
        return Error;
    }

    public void setError(String error) {
        Error = error;
    }
}
