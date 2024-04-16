package com.example.ozmee.ru.WebCrawler.crawler;

import com.example.ozmee.ru.WebCrawler.entities.HistoryOfOperation;
import com.example.ozmee.ru.WebCrawler.exc.NotValidReferenceException;
import com.example.ozmee.ru.WebCrawler.services.HistoryService;
import com.example.ozmee.ru.WebCrawler.util.PageValidator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Planner {
    HistoryService historyService;
    PageValidator pageValidator;

    Crawler crawler;

    public HttpStatus plan(String url, int deep){
        if(!pageValidator.isValidWebsite(url)){
            throw new NotValidReferenceException();
        }

        HistoryOfOperation history = HistoryOfOperation.builder()
                .url(url)
                .date(new Date())
                .isCompleted(false)
                .build();

        historyService.save(history);

        synchronized (crawler) {
            boolean isCompleted = false;

            isCompleted = crawler.scan(url, deep);

            if (isCompleted) {
                history.setCompleted(true);
            }
        }

        historyService.save(history);

        return HttpStatus.OK;
    }
}
