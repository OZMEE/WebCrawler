package com.example.ozmee.ru.WebCrawler.crawler;

import com.example.ozmee.ru.WebCrawler.entities.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CrawlerTest {

    private final Crawler crawler;

    @Autowired
    CrawlerTest(Crawler crawler) {
        this.crawler = crawler;
    }

    @Test
    void robotsFile() {

    }

    @Test
    void getRoot() {

    }

    @Test
    void search() {
    }

    @Test
    void savePages() {
    }



    @Test
    void crawl() {
    }

    @Test
    void distributeToSaveAndCrawl() {
    }
}