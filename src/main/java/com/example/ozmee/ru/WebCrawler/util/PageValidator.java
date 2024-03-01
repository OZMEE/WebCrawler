package com.example.ozmee.ru.WebCrawler.util;

import com.example.ozmee.ru.WebCrawler.entities.Page;
import com.example.ozmee.ru.WebCrawler.services.PageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PageValidator {
    PageService pageService;

    public boolean isValidWebsite(String url){
        return isHttp(url) && !isFile(url);
    }
    /*
    private boolean isDuplicate(String url){
        Optional<Page> page = pageService.searchByUrl(url);
        return page.isPresent();
    }
     */

    boolean isHttp(String url){
        return url.startsWith("https://") || url.startsWith("http://");
    }

    public boolean isFile(String url){
        return url.endsWith(".xml") || url.endsWith(".txt") || url.endsWith(".gz");
    }
}
