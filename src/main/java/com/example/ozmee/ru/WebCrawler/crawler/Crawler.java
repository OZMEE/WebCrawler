package com.example.ozmee.ru.WebCrawler.crawler;

import com.example.ozmee.ru.WebCrawler.entities.Page;
import com.example.ozmee.ru.WebCrawler.services.PageService;
import com.example.ozmee.ru.WebCrawler.util.PageValidator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Crawler {

    final PageService pageService;
    final PageValidator pageValidator;
    final Parser parser;

    int deep = 2;

    Set<String> wasVisited = new HashSet<>();

    @Async("taskExecutor")
    public void multiCrawl(int level, String url){
        crawl(++level, url);
    }

    public boolean scan(String url, int deep){
        this.deep = deep;

        String root = parser.getRootUrl(url);
        List<String> xmls = parser.parseRobotsFile(root);

        for(String xml : xmls){
            multiCrawl(1, xml);
        }

        return true;
    }

    void crawl(int level, String url){
        if(wasVisited.contains(url)){
            return;
        }
        wasVisited.add(url);
        if(pageValidator.isValidWebsite(url)){
            Page page = parser.convertUrlToPage(url);
            if(page != null) {
                System.out.println("Сохранение");
                pageService.save(page);
            }
        }

        Queue<String> parsedLinks = parser.parse(url);
        System.out.println("Parsed links: " + parsedLinks.toString());

        if(level == deep){
            pageService.saveAllPages(parser.convertListUrlToListPage(parsedLinks));
            return;
        }

        List<String> linksToCrawl = distributeToSaveAndCrawl(parsedLinks);
        System.out.println("Links to crawl: " + linksToCrawl);

        if(linksToCrawl.size() == 1 && linksToCrawl.get(0).equals(url)){
            Page page = parser.convertUrlToPage(url);
            if(page != null) {
                pageService.save(page);
            }
            return;
        }
        for(String link : linksToCrawl){
            crawl(++level, link);
        }
    }

    List<String> distributeToSaveAndCrawl(Queue<String> links){
        List<String> listToSave = new ArrayList<>();
        List<String> listToParse = new ArrayList<>();

        for (String link : links) {
            if (pageValidator.isValidWebsite(link)) {
                listToSave.add(link);
            } else {
                listToParse.add(link);
            }
        }

        List<Page> pagesToSave = parser.convertListUrlToListPage(listToSave);
        pageService.saveAllPages(pagesToSave);

        return listToParse;
    }
}
