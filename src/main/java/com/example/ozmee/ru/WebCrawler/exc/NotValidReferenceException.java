package com.example.ozmee.ru.WebCrawler.exc;

public class NotValidReferenceException extends RuntimeException {
    public NotValidReferenceException(){
        super("Reference is not valid!");
    }
}
