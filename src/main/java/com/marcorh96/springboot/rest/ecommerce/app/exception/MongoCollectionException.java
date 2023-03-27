package com.marcorh96.springboot.rest.ecommerce.app.exception;

public class MongoCollectionException extends Exception{
    
    public MongoCollectionException(String message){
        super(message);
    }
}
