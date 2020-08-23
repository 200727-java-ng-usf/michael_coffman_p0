package com.revature.exceptions;

public class InvalidRequestException extends RuntimeException{

    /**
     * This method will throw an InvalidRequestException with a custom
     * message depending on the context.
     * @param msg
     */
    public InvalidRequestException(String msg) {
        super(msg);
    }

}
