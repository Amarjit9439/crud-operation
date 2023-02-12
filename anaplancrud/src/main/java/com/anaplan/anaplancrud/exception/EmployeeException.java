package com.anaplan.anaplancrud.exception;

public class EmployeeException extends RuntimeException{

    String message;
    public EmployeeException(String message){
        super(message);
        this.message=message;

    }

    @Override
    public String getMessage() {
        return message;
    }
}
