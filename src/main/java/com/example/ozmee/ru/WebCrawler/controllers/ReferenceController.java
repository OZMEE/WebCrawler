package com.example.ozmee.ru.WebCrawler.controllers;

import com.example.ozmee.ru.WebCrawler.crawler.Crawler;
import com.example.ozmee.ru.WebCrawler.crawler.Planner;
import com.example.ozmee.ru.WebCrawler.entities.Page;
import com.example.ozmee.ru.WebCrawler.dto.SearchRequest;
import com.example.ozmee.ru.WebCrawler.services.PageService;
import com.example.ozmee.ru.WebCrawler.util.SearchRequestValidator;
import com.example.ozmee.ru.WebCrawler.util.exc.CantCrawlUrlException;
import com.example.ozmee.ru.WebCrawler.util.exc.SearchRequestError;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationContextInitializedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.XmlWebApplicationContext;

import java.util.List;

@RestController
@RequestMapping("/crawler")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReferenceController {
    PageService pageService;

    SearchRequestValidator searchRequestValidator;
    Planner planner;

    @PostMapping("/scan")
    public ResponseEntity<Boolean> search(@RequestBody @Valid SearchRequest request,
                                          BindingResult bindingResult){
        searchRequestValidator.validate(request, bindingResult);
        System.out.println(request);

        if(bindingResult.hasErrors()){
            List<FieldError> errors = bindingResult.getFieldErrors();
            StringBuilder str = new StringBuilder();
            for(FieldError error : errors){
                str.append(error.getField()).append(" - ").append(error.getDefaultMessage());
            }

            throw new CantCrawlUrlException(str.toString());
        }

        planner.plan(request.getReference(), request.getDeep());

        return ResponseEntity.ok(true);
    }

    @ExceptionHandler
    private ResponseEntity<SearchRequestError> handler(CantCrawlUrlException e){
        SearchRequestError error = new SearchRequestError(e.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<List<Page>> getAll(){
        List<Page> pages = pageService.findAll();

        return ResponseEntity.ok(pages);
    }
}
