package com.example.ozmee.ru.WebCrawler.util.exc;

public class CantCrawlUrlException extends RuntimeException{
    public CantCrawlUrlException(String msg){
        super(msg);
    }
}
