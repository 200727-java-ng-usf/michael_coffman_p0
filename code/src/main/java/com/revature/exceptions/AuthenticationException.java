package com.revature.exceptions;

public class AuthenticationException extends RuntimeException{


    // This will be constructed when no authentication message is
    // specified.
    public AuthenticationException() {
        super("Authentication failed.");
    }

    // This will be constructed when an authentication message is
    // specified.
    public AuthenticationException(String msg) {
        super(msg);
    }



}
