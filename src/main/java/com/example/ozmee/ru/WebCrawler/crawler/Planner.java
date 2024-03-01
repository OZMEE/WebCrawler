package com.example.ozmee.ru.WebCrawler.crawler;

import com.example.ozmee.ru.WebCrawler.entities.History;
import com.example.ozmee.ru.WebCrawler.exc.NotValidReferenceException;
import com.example.ozmee.ru.WebCrawler.services.HistoryService;
import com.example.ozmee.ru.WebCrawler.util.PageValidator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Planner {
    ApplicationContext applicationContext;
    HistoryService historyService;

    PageValidator pageValidator;

    List<Crawler> crawlers = new ArrayList<>();

    @Async("taskExecutor")
    public HttpStatus plan(String url){
        if(!pageValidator.isValidWebsite(url)){
            throw new NotValidReferenceException();
        }
        Crawler crawlerForScan = getCrawlerForScan();

        History history = History.builder()
                .url(url)
                .date(new Date())
                .isCompleted(false)
                .build();

        boolean isCompleted = false;

        isCompleted = crawlerForScan.scan(url);

        if(isCompleted){
            history.setCompleted(true);
        }

        return HttpStatus.OK;
    }

    public Crawler getCrawlerForScan(){
        Crawler crawlerForScan = null;
        for(Crawler crawler : crawlers){
            if(crawlerForScan == null && !crawler.isScan()){
                crawlerForScan = crawler;
            }
        }
        if(crawlerForScan == null){
            crawlerForScan = applicationContext.getBean(Crawler.class);
            crawlers.add(crawlerForScan);
        }
        return crawlerForScan;
    }
}
