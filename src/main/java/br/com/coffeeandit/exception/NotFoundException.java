package br.com.coffeeandit.exception;

public class NotFoundException extends RuntimeException{

    public NotFoundException (String message){
        super(message);
    }
}
