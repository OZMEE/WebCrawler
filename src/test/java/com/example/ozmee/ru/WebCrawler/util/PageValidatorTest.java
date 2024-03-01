package com.example.ozmee.ru.WebCrawler.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PageValidatorTest {
    PageValidator pageValidator;

    @Autowired
    PageValidatorTest(PageValidator pageValidator){
        this.pageValidator = pageValidator;
    }

    @Test
    void isValidWebsite() {
        String value = "https://www.youtube.com/";
        assertTrue(pageValidator.isValidWebsite(value));

        value = "https://www.youtube.com/robots.txt";
        assertFalse(pageValidator.isValidWebsite(value));

        value = "https://www.youtube.com/product/sitemap.xml";
        assertFalse(pageValidator.isValidWebsite(value));

        value = "https://translate.google.com/;%29";
        assertTrue(pageValidator.isValidWebsite(value));
    }

    @Test
    void isHttp() {
        String value = "https://github.com/PrettyBoyCosmo/HTTP-List?ysclid=lssymrrnwt722039539";
        assertTrue(pageValidator.isHttp(value));

        value = "htps://github.com/PrettyBoyCosmo/HTTP-List?ysclid=lssymrrnwt722039539";
        assertFalse(pageValidator.isHttp(value));
    }

    @Test
    void isFile() {
        String value = "https://www.youtube.com/product/sitemap.xml";
        assertTrue(pageValidator.isFile(value));

        value = "https://www.youtube.com/robots.txt";
        assertTrue(pageValidator.isFile(value));

        value = "https://github.com/PrettyBoyCosmo/HTTP-List?ysclid=lssymrrnwt722039539";
        assertFalse(pageValidator.isFile(value));
    }
}