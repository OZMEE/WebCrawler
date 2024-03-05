package com.example.ozmee.ru.WebCrawler.crawler;

import com.example.ozmee.ru.WebCrawler.entities.Page;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ParserTest {

    Parser parser;

    @Autowired
    ParserTest(Parser parser){
        this.parser = parser;
    }

    @Test
    void convertUrlToPage() {
    }

    @Test
    void parse() {
        String value = "https://javarush.com/sitemap.xml";

        Queue<String> res = new LinkedList<>();
        res.add("https://javarush.com/sitemap-main.xml");
        res.add("https://javarush.com/sitemap-groups.xml");
        res.add("https://javarush.com/sitemap-quests.xml");
        res.add("https://javarush.com/sitemap-lectures-1.xml");
        res.add("https://javarush.com/sitemap-forum.xml");
        res.add("https://javarush.com/sitemap-help-1.xml");
        res.add("https://javarush.com/sitemap-help-2.xml");
        res.add("https://javarush.com/sitemap-help-3.xml");

        assertEquals(res, parser.parse(value));

        value = "https://javarush.com/sitemap-main.xml";
        res = new LinkedList<>();
        res.add("https://javarush.com/");
        res.add("https://javarush.com/ua/");
        res.add("https://javarush.com/about/mission");
        res.add("https://javarush.com/about/tutorial");
        res.add("https://javarush.com/about/reviews");
        res.add("https://javarush.com/about/faq");
        res.add("https://javarush.com/groups/online-internship");
        res.add("https://javarush.com/prices");

        assertEquals(res, parser.parse(value));
    }
    @Test
    void convertListUrlToListPage() {
        List<String> value = new ArrayList<>();
        value.add("https://animego.org/anime/bakuman-128?ysclid=lsr58k6qh2215897461");
        value.add("https://jut.su/bakuman/");

        List<Page> res = new ArrayList<>();
        res.add(Page.builder()
                .name("Бакуман смотреть онлайн — Аниме")
                .url("https://animego.org/anime/bakuman-128?ysclid=lsr58k6qh2215897461")
                .isFile(false)
                .build());
        res.add(Page.builder()
                .name("Смотреть Бакуман все серии и сезоны на Jut.su")
                .url("https://jut.su/bakuman/")
                .isFile(false)
                .build());

        assertEquals(res, parser.convertListUrlToListPage(value));

        value = new ArrayList<>();
        value.add("https://qna.habr.com/q/713947?ysclid=lst03r4afz467029370");
        value.add("https://www.youtube.com/product/sitemap.xml");

        res = new ArrayList<>();
        res.add(Page.builder()
                .name("Как и где взять список сайтов работающих на HTTP? — Хабр Q&A")
                .url("https://qna.habr.com/q/713947?ysclid=lst03r4afz467029370")
                .isFile(false)
                .build());
        res.add(Page.builder()
                .name("https://www.youtube.com/product/sitemap.xml")
                .url("https://www.youtube.com/product/sitemap.xml")
                .isFile(true)
                .build());
        assertEquals(res, parser.convertListUrlToListPage(value));
    }

    @Test
    void getRootUrl() {
        String value = "https://javarush.com/groups/posts/2542-otvetih-na-samihe-populjarnihe-voprosih-ob-interfeyse-map";
        String res = "https://javarush.com/robots.txt";

        assertEquals(res, parser.getRootUrl(value));

        value = "https://www.youtube.com/";
        res = "https://www.youtube.com/robots.txt";

        assertEquals(res, parser.getRootUrl(value));
    }

    @Test
    void parseRobotsFile() {
        String value = "https://javarush.com/robots.txt";
        List<String> list = new ArrayList<>();
        list.add("https://javarush.com/sitemap.xml");
        assertLinesMatch(list, parser.parseRobotsFile(value));

        value = "https://animego.org/robots.txt";
        list = new ArrayList<>();
        list.add("https://animego.org/sitemap.xml");
        assertLinesMatch(list, parser.parseRobotsFile(value));
    }
}