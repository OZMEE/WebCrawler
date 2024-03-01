package com.example.ozmee.ru.WebCrawler.controllers;

import com.example.ozmee.ru.WebCrawler.crawler.Crawler;
import com.example.ozmee.ru.WebCrawler.crawler.Crawler2;
import com.example.ozmee.ru.WebCrawler.entities.Page;
import com.example.ozmee.ru.WebCrawler.entities.SearchRequest;
import com.example.ozmee.ru.WebCrawler.services.PageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/crawler")
@RequiredArgsConstructor
public class ReferenceController {
    private final Crawler crawler;
    private final PageService pageService;

    //List<Crawler2> crawlers = new ArrayList<>();

    @GetMapping("/scan")
    public ResponseEntity<Boolean> search(){

        crawler.scan("https://javarush.com/groups/posts/2542-otvetih-na-samihe-populjarnihe-voprosih-ob-interfeyse-map");

        return ResponseEntity.ok(true);
    }

    @GetMapping
    public ResponseEntity<List<Page>> getAll(){
        List<Page> pages = pageService.findAll();

        return ResponseEntity.ok(pages);
    }
}
